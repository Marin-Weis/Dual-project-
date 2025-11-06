package fr.iutvannes.dual.controller.fragments
//
//import android.os.Bundle
//import android.text.InputType
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
import androidx.fragment.app.Fragment
//import androidx.lifecycle.lifecycleScope
//import androidx.room.Room
//import androidx.navigation.fragment.findNavController
//import fr.iutvannes.dual.R
//import fr.iutvannes.dual.model.database.AppDatabase
//import fr.iutvannes.dual.model.persistence.Prof
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
class InscriptionFragment : Fragment() {
//
//    private var passwordVisible = false
//    private var passwordVerifVisible = false
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val view = inflater.inflate(R.layout.inscription_view, container, false)
//
//        //Récupération des vues
//        val nomInput = view.findViewById<EditText>(R.id.Nom)
//        val prenomInput = view.findViewById<EditText>(R.id.Prenom)
//        val emailInput = view.findViewById<EditText>(R.id.Email)
//        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
//        val passwordVerifInput = view.findViewById<EditText>(R.id.passwordVerifInput)
//        val oeilIcon = view.findViewById<ImageView>(R.id.oeilIcon)
//        val oeilVerifIcon = view.findViewById<ImageView>(R.id.oeilVerifIcon)
//        val inscriptionButton = view.findViewById<Button>(R.id.inscriptionButton)
//        val connexionLien = view.findViewById<TextView>(R.id.connexionLien)
//
//        //Création de la base Room
//        val db = Room.databaseBuilder(
//            requireContext(),
//            AppDatabase::class.java,
//            "dual.db"
//        ).build()
//        val dao = db.profDAO()
//
//        //Gestion de l’affichage du mot de passe
//        oeilIcon.setOnClickListener {
//            passwordVisible = !passwordVisible
//            passwordInput.inputType = if (passwordVisible)
//                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//            else
//                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//            passwordInput.setSelection(passwordInput.text.length)
//        }
//
//        oeilVerifIcon.setOnClickListener {
//            passwordVerifVisible = !passwordVerifVisible
//            passwordVerifInput.inputType = if (passwordVerifVisible)
//                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//            else
//                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//            passwordVerifInput.setSelection(passwordVerifInput.text.length)
//        }
//
//        //Action sur le bouton d’inscription
//        inscriptionButton.setOnClickListener {
//            val nom = nomInput.text.toString().trim()
//            val prenom = prenomInput.text.toString().trim()
//            val email = emailInput.text.toString().trim()
//            val password = passwordInput.text.toString().trim()
//            val passwordVerif = passwordVerifInput.text.toString().trim()
//
//            if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || passwordVerif.isEmpty()) {
//                Toast.makeText(requireContext(), "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            if (password != passwordVerif) {
//                Toast.makeText(requireContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            lifecycleScope.launch {
//                val existingProf = withContext(Dispatchers.IO) {
//                    dao.getProfByEmail(email)
//                }
//
//                if (existingProf != null) {
//                    Toast.makeText(requireContext(), "Un professeur avec cet email existe déjà", Toast.LENGTH_SHORT).show()
//                } else {
//                    withContext(Dispatchers.IO) {
//                        val prof = Prof(
//                            nom = nom,
//                            prenom = prenom,
//                            email = email,
//                            password = password
//                        )
//                        dao.insert(prof)
//                    }
//
//                    Toast.makeText(requireContext(), "Inscription réussie 🎉", Toast.LENGTH_SHORT).show()
//
//                    //Navigation automatique vers la page de connexion
//                    findNavController().navigate(R.id.action_inscriptionFragment_to_connexionFragment)
//                }
//            }
//        }
//
//        //Lien “Déjà un compte ? Se connecter
//        connexionLien.setOnClickListener {
//            findNavController().navigate(R.id.action_inscriptionFragment_to_connexionFragment)
//        }
//
//        return view
//    }
}
