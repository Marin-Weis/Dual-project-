package fr.iutvannes.dual.controller

import android.os.Bundle
import android.view.View // Import pour gérer la visibilité (View.VISIBLE, View.GONE)
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import fr.iutvannes.dual.R
import fr.iutvannes.dual.controller.fragments.ConnexionFragment
import fr.iutvannes.dual.controller.fragments.ClassesFragment
import fr.iutvannes.dual.controller.fragments.InscriptionFragment
import fr.iutvannes.dual.controller.fragments.ProfilFragment
import fr.iutvannes.dual.controller.fragments.TableauDeBordFragment

class MainActivity : AppCompatActivity() {

    // Vues pour la barre de navigation et son conteneur
    private lateinit var navBarContainer: LinearLayout
    private lateinit var navHomeButton: LinearLayout
    private lateinit var navClassesButton: LinearLayout
    private lateinit var topBarContainer: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Code pour le plein écran...
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.hide(WindowInsetsCompat.Type.systemBars())
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContentView(R.layout.activity_main)

        // Listener pour le bouton de profil
        val profileButton = findViewById<ImageButton>(R.id.profileImage)
        profileButton.setOnClickListener {
            showFragment(ProfilFragment())
        }

        // --- GESTION DE LA NAVIGATION ---

        // Récupérer les vues globales de la barre de navigation
        navBarContainer = findViewById(R.id.bottomNav)
        navHomeButton = findViewById(R.id.nav_home_button)
        navClassesButton = findViewById(R.id.nav_classes_button)

        // --- GESTION DU PROFIL ---
        topBarContainer = findViewById(R.id.topBar)


        // Définir les actions des clics
        navHomeButton.setOnClickListener {
            if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is TableauDeBordFragment) {
                showFragment(TableauDeBordFragment())
            }
        }
        navClassesButton.setOnClickListener {
            if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is ClassesFragment) {
                showFragment(ClassesFragment())
            }
        }

        // --- ÉTAT INITIAL ---
        if (savedInstanceState == null) {
            showFragment(InscriptionFragment()) // On commence sur le tableau de bord
        }

    }

    /**
     * Remplace le fragment actuel ET gère la visibilité de la barre de navigation.
     */
    fun showFragment(fragment: Fragment) {
        // --- C'EST LA LOGIQUE LA PLUS IMPORTANTE ---
        when (fragment) {
            is TableauDeBordFragment -> {
                // Si c'est le tableau de bord
                topBarContainer.visibility = View.VISIBLE
                navBarContainer.visibility = View.VISIBLE // On MONTRE la barre
                selectNavItem(navHomeButton) // On sélectionne l'icône "Home"
            }

            is ClassesFragment -> {
                topBarContainer.visibility = View.VISIBLE
                navBarContainer.visibility = View.VISIBLE // On MONTRE la barre
                selectNavItem(navClassesButton) // On sélectionne l'icône "Classe"
            }

            is ProfilFragment -> {
                topBarContainer.visibility = View.GONE
                navBarContainer.visibility = View.GONE
            }

            is ConnexionFragment -> {
                topBarContainer.visibility = View.GONE
                navBarContainer.visibility = View.GONE
            }

            is InscriptionFragment -> {
                topBarContainer.visibility = View.GONE
                navBarContainer.visibility = View.GONE
            }

            // Pour tout autre fragment (Connexion, Inscription...), la barre sera cachée par défaut
            else -> {
                navBarContainer.visibility = View.GONE
            }
        }

        // Affiche le fragment passé en paramètre
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right, // entrée
                R.anim.fade_out,       // sortie
                R.anim.fade_in,        // retour
                R.anim.slide_out_right // retour inverse
            )
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Gère la sélection visuelle des boutons (change la couleur).
     * Met les boutons à false et le bouton passé en paramètre à true afin d'éviter les problèmes de couleur.
     * Ensuite on affiche la configuration actuelle avec itemToSelect à true (le bouton selectionner change de couleur).
     */
    private fun selectNavItem(itemToSelect: LinearLayout) {
        navHomeButton.isSelected = false
        navClassesButton.isSelected = false
        itemToSelect.isSelected = true
    }
}


