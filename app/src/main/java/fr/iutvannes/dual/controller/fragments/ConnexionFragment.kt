package fr.iutvannes.dual.controller

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.room.Room
import fr.iutvannes.dual.R
import fr.iutvannes.dual.model.database.AppDatabase
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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

        val sharedPref = requireContext().getSharedPreferences("loginPrefs", 0)
        val editor = sharedPref.edit()

        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "dual.db"
        ).build()
        val dao = db.profDAO()

        val savedEmail = sharedPref.getString("email", "")
        val savedPassword = sharedPref.getString("password", "")
        val isRemembered = sharedPref.getBoolean("rememberMe", false)

        if (isRemembered) {
            emailInput.setText(savedEmail)
            passwordInput.setText(savedPassword)
            rememberMe.isChecked = true
        }

        oeilIcon.setOnClickListener {
            passwordVisible = !passwordVisible
            passwordInput.inputType = if (passwordVisible)
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordInput.setSelection(passwordInput.text.length)
        }

        connexionButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Veuillez entrer votre email", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(requireContext(), "Veuillez entrer votre mot de passe", Toast.LENGTH_SHORT).show()
            } else {
                // On lance une coroutine pour accéder à la DB
                lifecycleScope.launch {
                    val prof = withContext(Dispatchers.IO) { // Dispatchers.IO pour le thread de la DB
                        dao.getProfByEmail(email)
                    }

                    if (prof == null) {
                        Toast.makeText(requireContext(), "Cet email n'est pas enregistré", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Connexion réussie !", Toast.LENGTH_SHORT).show()
                        if (rememberMe.isChecked) {
                            editor.putString("email", emailInput.text.toString())
                            editor.putString("password", passwordInput.text.toString()) // TODO hash password
                            editor.putBoolean("rememberMe", true)
                            editor.apply()
                        } else {
                            editor.clear()
                            editor.apply()
                        }
                        findNavController().navigate(R.id.action_connexionFragment_to_tableauDeBordFragment)
                    }
                }
            }
        }

        inscriptionLien.setOnClickListener {
            findNavController().navigate(R.id.action_connexionFragment_to_inscriptionFragment)
        }

        forgottenPassword.setOnClickListener {
            // TODO changement de fragment
        }

        return view
    }
}
