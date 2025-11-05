package fr.iutvannes.dual

import android.content.Intent              // Pour lancer une autre activité
import android.graphics.Bitmap
import android.os.Bundle                  // Pour la gestion du cycle de vie
import android.widget.Button               // Pour manipuler les boutons de la vue
import androidx.appcompat.app.AppCompatActivity  // Classe de base pour les activités modernes (compatibilité)
import fr.iutvannes.dual.infrastructure.server.KtorServer // serveur Ktor
import fr.iutvannes.dual.infrastructure.Utils
import com.google.zxing.BarcodeFormat // QR code
import com.google.zxing.qrcode.QRCodeWriter // QR code

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lien entre le contrôleur (MainActivity) et la vue XML (activity_main.xml)
        setContentView(R.layout.tableau_de_bord_view)

//        // Lancer le fragment de connexion au démarrage
//        showConnexionFragment()
    }

//        // 🧩 On récupère le bouton déclaré dans activity_main.xml
//        val boutonConnexion = findViewById<Button>(R.id.boutonConnexion)
//
//        // 🚀 Action : quand on clique sur le bouton, on ouvre la page de connexion
//        boutonConnexion.setOnClickListener {
//            val intent = Intent(this, ConnexionActivity::class.java)
//            startActivity(intent)
//        }

        // BOUTON LANCER SEANCE
         //bouton.setOnClickListener {
         //   startSession()
         //}
    }

    private fun startSession() {
        KtorServer.start(this) // lancement du serveur

        val ip = Utils.getLocalIpAddress() ?: "localhost"
        val url = "http://$ip:8080/student"

        val qrBitmap = genererQRCode(url)

        // affichage QR code
        // qrCodeView.setImageBitmap(qrBitmap)
        // textUrl.text = url
    }

    private fun genererQRCode(text: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bmp
    }
    // Exemple d'utilisation pour la connexion à l'app
//    private fun showConnexionFragment() {
//        val fragment = ConnexionFragment()  // Fragment Vue
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, fragment)
//            .commit()
//    }
}
