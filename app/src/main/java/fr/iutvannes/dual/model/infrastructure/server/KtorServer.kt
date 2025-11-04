package fr.iutvannes.dual.infrastructure.server

import android.content.Context
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class EventDTO(
    val type: String,
    val studentId: String? = null,
    val payload: JsonObject? = null
)

/**
 * Démarre/arrête le serveur Ktor (HTTP) et installe les plugins json, logs, cors...
 * Injecte les dépendances dans les routes
 */
object KtorServer {
    private var engine: ApplicationEngine? = null
    private lateinit var appContext: Context

    fun start(context: Context, port: Int = 8080, wait: Boolean = false) {
        if (engine != null) return
        appContext = context.applicationContext
        engine = embeddedServer(Netty, host = "0.0.0.0", port = port) {
            module(appContext)
        }.also { it.start(wait = wait) }
    }

    fun stop() {
        engine?.stop(500, 1000)
        engine = null
    }
}

private fun contentTypeFor(path: String): ContentType =
    when {
        path.endsWith(".html", true) -> ContentType.Text.Html
        path.endsWith(".css", true)  -> ContentType.Text.CSS
        path.endsWith(".js", true)   -> ContentType.Application.JavaScript
        path.endsWith(".svg", true)  -> ContentType.Image.SVG
        path.endsWith(".png", true)  -> ContentType.Image.PNG
        path.endsWith(".jpg", true) || path.endsWith(".jpeg", true) -> ContentType.Image.JPEG
        else -> ContentType.Application.OctetStream
    }

/**
 * Modules Ktor
 */
fun Application.module(appContext: Context) {

    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) { json() }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
    }
    install(Compression) { gzip() }

    val liveBus = MutableSharedFlow<EventDTO>(extraBufferCapacity = 64)

    routing {

        get("/ping") { call.respond(mapOf("status" to "ok")) }

        get("/qr-url") {
            val host = call.request.host()
            val port = call.request.port()
            val base = "http://$host${if (port in listOf(80, 443)) "" else ":$port"}"
            call.respond(mapOf("join" to "$base/student"))
        }

        // UI élève depuis assets/student
        get("/student") {
            val bytes = appContext.assets.open("student/index.html").use { it.readBytes() }
            call.respondBytes(bytes, contentType = ContentType.Text.Html)
        }
        get("/student/{...}") {
            val rest = call.parameters.getAll("...")?.joinToString("/") ?: "index.html"
            val assetPath = "student/$rest"
            runCatching { appContext.assets.open(assetPath).use { it.readBytes() } }
                .onSuccess { bytes -> call.respondBytes(bytes, contentType = contentTypeFor(assetPath)) }
                .onFailure { call.respond(HttpStatusCode.NotFound, "Fichier introuvable: $assetPath") }
        }

        post("/event") {
            val evt = runCatching { call.receive<EventDTO>() }.getOrElse {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "invalid_body"))
                return@post
            }
            liveBus.tryEmit(evt)
            call.respond(HttpStatusCode.Accepted, mapOf("status" to "received"))
        }

        // SSE manuel (sans plugin)
        get("/live") {
            call.response.cacheControl(CacheControl.NoCache(null))
            call.respondTextWriter(contentType = ContentType.Text.EventStream) {
                fun sendSse(data: String) {
                    write("data: $data\n\n")
                    flush()
                }
                sendSse("""{"type":"connected","payload":{"msg":"ready"}}""")
                liveBus.collect { evt ->
                    val payload = evt.payload ?: JsonObject(mapOf("ts" to JsonPrimitive(System.currentTimeMillis())))
                    val studentId = evt.studentId ?: ""
                    val json = """{"type":"${evt.type}","studentId":"$studentId","payload":$payload}"""
                    sendSse(json)
                }
            }
        }

        get("/") {
            call.respondText(
                "Serveur BIATHLON actif • Élève: /student • Prof (SSE): /live • QR: /qr-url",
                ContentType.Text.Plain
            )
        }
    }
}
