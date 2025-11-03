package fr.iutvannes.dual

import android.content.Intent              // Pour lancer une autre activité
import android.os.Bundle                  // Pour la gestion du cycle de vie
import android.widget.Button               // Pour manipuler les boutons de la vue
import androidx.appcompat.app.AppCompatActivity  // Classe de base pour les activités modernes (compatibilité)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔗 Lien entre le contrôleur (MainActivity) et la vue XML (activity_main.xml)
        setContentView(R.layout.accueil_view)

//        // 🧩 On récupère le bouton déclaré dans activity_main.xml
//        val boutonConnexion = findViewById<Button>(R.id.boutonConnexion)
//
//        // 🚀 Action : quand on clique sur le bouton, on ouvre la page de connexion
//        boutonConnexion.setOnClickListener {
//            val intent = Intent(this, ConnexionActivity::class.java)
//            startActivity(intent)
//        }
    }
}
