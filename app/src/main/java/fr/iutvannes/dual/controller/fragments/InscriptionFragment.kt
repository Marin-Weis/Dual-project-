package fr.iutvannes.dual.controller.fragments


import android.content.Context
import androidx.room.*
import fr.iutvannes.dual.model.database.AppDatabase
import fr.iutvannes.dual.model.dao.ProfDAO
import fr.iutvannes.dual.model.persistence.Prof

class InscriptionFragment(context: Context) {

    private val database = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "app-database"
    ).build()

    private val profDAO = database.profDAO()

    suspend fun inscrireProf(name : String, firstname : String, email : String, password : String) : String {

        if(name.isBlank() || firstname.isBlank() || email.isBlank() || password.isBlank()) {
            return "Tous les champs son obligatoires !"
        }

        val existingProf = profDAO.getProfByEmail(email)
        if (existingProf != null) {
            return "Un professeur avec cet email existe déjà !"
        }

        val prof = Prof(nom = name, prenom = firstname, email = email, password = password)
        profDAO.insert(prof)

        return "Inscription réussie !"
    }


}

