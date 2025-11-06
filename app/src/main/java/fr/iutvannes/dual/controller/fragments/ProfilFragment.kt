package fr.iutvannes.dual.controller.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import fr.iutvannes.dual.R

/**
 * Fragment pour afficher l'écran de profil de l'utilisateur.
 * Le layout associé est R.layout.fragment_profil.
 */
class ProfilFragment : Fragment(R.layout.fragment_profil) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔙 Bouton retour vers le tableau de bord
        val backButton = view.findViewById<ImageButton>(R.id.arrow_back_button)
        backButton.setOnClickListener {
            // Utilise le contrôleur de navigation pour revenir à l’écran précédent
            findNavController().navigate(R.id.dashboardFragment)
        }
    }
}
