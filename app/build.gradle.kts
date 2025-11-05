plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "fr.iutvannes.dual"
    compileSdk =36

    defaultConfig {
        applicationId = "fr.iutvannes.dual"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    // On peut retirer le bloc buildFeatures { compose = false }
    // car c'est la valeur par défaut. Je le laisse pour la clarté.
    buildFeatures {
        compose = false
    }

    packaging {
        resources {
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/io.netty.versions.properties"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}

dependencies {
    // --- Dépendances de base pour une application non-Compose ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat) // Essentiel pour les thèmes (comme AppCompatActivity)

    // --- AJOUT NÉCESSAIRE ---
    // C'est cette ligne qui ajoute Theme.MaterialComponents...
    implementation("com.google.android.material:material:1.11.0")
    implementation(libs.androidx.constraintlayout)

    // --- Dépendances pour les tests ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // --- VOS DÉPENDANCES CONSERVÉES ---

    // --- ROOM (Base de données SQLite) ---
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // --- KTOR (Serveur HTTP local) ---
    implementation("io.ktor:ktor-server-core:3.0.0")
    implementation("io.ktor:ktor-server-netty:3.0.0")
    implementation("io.ktor:ktor-server-content-negotiation:3.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")

    // --- COROUTINES (Tâches asynchrones Kotlin) ---
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // --- LOGGING (Pour le débogage serveur) ---
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // --- Dépendance KTX pour les fragments ---
    implementation("androidx.fragment:fragment-ktx:1.6.0")

    // --- RETIRÉ : Dépendances inutiles de Jetpack Compose ---
    // implementation(libs.androidx.lifecycle.runtime.ktx) // Souvent utilisé avec Compose, mais peut être gardé si besoin
    // implementation(libs.androidx.activity.compose)
    // implementation(platform(libs.androidx.compose.bom))
    // implementation(libs.androidx.compose.ui)
    // implementation(libs.androidx.compose.ui.graphics)
    // implementation(libs.androidx.compose.ui.tooling.preview)
    // implementation(libs.androidx.compose.material3)
    // androidTestImplementation(platform(libs.androidx.compose.bom))
    // androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    // debugImplementation(libs.androidx.compose.ui.tooling)
    // debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Ktor server
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)

    // plugins
    implementation(libs.ktor.call.logging)
    implementation(libs.ktor.default.headers)
    implementation(libs.ktor.cors)
    implementation(libs.ktor.compression)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation("com.google.zxing:core:3.5.3")


    // logging
    implementation(libs.logback)
}
