package fr.iutvannes.dual.controller.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import fr.iutvannes.dual.R
import fr.iutvannes.dual.model.database.AppDatabase
import fr.iutvannes.dual.model.persistence.Prof
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConnexionFragment : Fragment() {

    private var passwordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_connexion, container, false)

        val emailInput = view.findViewById<EditText>(R.id.Email)
        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
        val oeilIcon = view.findViewById<ImageView>(R.id.oeilIcon)
        val connexionButton = view.findViewById<Button>(R.id.connectionButton)
        val inscriptionLien = view.findViewById<TextView>(R.id.inscriptionLien)
        val rememberMe = view.findViewById<CheckBox>(R.id.rememberMeCheckBox)
        val forgottenPassword = view.findViewById<TextView>(R.id.forgottenPassword)

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "dual.db"
        ).build()
        val dao = db.profDAO()

        // ⚠️ Ajout temporaire d’un professeur de test si la table est vide
        lifecycleScope.launch(Dispatchers.IO) {
            val existing = dao.getAll()
            if (existing.isEmpty()) {
                dao.insert(
                    Prof(
                        nom = "Dupont",
                        prenom = "Jean",
                        email = "test@test.com",
                        password = "1234"
                    )
                )
                println("👤 Prof de test ajouté : test@test.com / 1234")
            }
        }

        // 👁️ Gérer l'affichage du mot de passe
        oeilIcon.setOnClickListener {
            passwordVisible = !passwordVisible
            passwordInput.inputType = if (passwordVisible)
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordInput.setSelection(passwordInput.text.length)
        }

        // 🔐 Connexion
        connexionButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Veuillez entrer votre email", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(requireContext(), "Veuillez entrer votre mot de passe", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val prof = withContext(Dispatchers.IO) {
                        dao.getProfByEmail(email)
                    }

                    if (prof == null) {
                        Toast.makeText(requireContext(), "Cet email n'est pas enregistré", Toast.LENGTH_SHORT).show()
                    } else if (prof.password != password) {
                        Toast.makeText(requireContext(), "Mot de passe incorrect", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Connexion réussie ✅", Toast.LENGTH_SHORT).show()
                        // ✅ Navigation vers le tableau de bord
                        findNavController().navigate(R.id.action_connexionFragment_to_dashboardFragment)
                    }
                }
            }
        }

        // 🧭 Aller vers l'inscription
        inscriptionLien.setOnClickListener {
            findNavController().navigate(R.id.action_connexionFragment_to_inscriptionFragment)
        }

        // 💾 Se souvenir de moi
        rememberMe.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(requireContext(), "Connexion automatique activée", Toast.LENGTH_SHORT).show()
                // TODO: sauvegarder l'email et le mot de passe avec SharedPreferences
            } else {
                Toast.makeText(requireContext(), "Connexion automatique désactivée", Toast.LENGTH_SHORT).show()
                // TODO: effacer les infos enregistrées
            }
        }

//        // ❓ Mot de passe oublié
//        forgottenPassword.setOnClickListener {
//            findNavController().navigate(R.id.action_connexionFragment_to_forgottenPasswordFragment)
//        }

        return view
    }
}
