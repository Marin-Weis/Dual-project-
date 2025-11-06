package fr.iutvannes.dual

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var topBar: View
    private lateinit var bottomNav: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Plein écran
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.hide(WindowInsetsCompat.Type.systemBars())
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Charge ton layout principal
        setContentView(R.layout.activity_main)

        // Récupère les barres
        topBar = findViewById(R.id.topBar)
        bottomNav = findViewById(R.id.bottomNav)

        // 🧭 Initialise la navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 👀 Gère automatiquement la visibilité des barres selon l’écran affiché
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.connexionFragment,
                R.id.inscriptionFragment -> { // Pages sans barre
                    topBar.visibility = View.GONE
                    bottomNav.visibility = View.GONE
                }
                else -> { // Toutes les autres pages
                    topBar.visibility = View.VISIBLE
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }

        // ✅ Si tu veux gérer le bouton "Retour" Android
        onBackPressedDispatcher.addCallback(this) {
            if (!navController.popBackStack()) finish()
        }
    }
}
