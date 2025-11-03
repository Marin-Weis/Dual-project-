package fr.iutvannes.dual.Persistence

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "Eleve")
data class Eleve(
    @PrimaryKey(autoGenerate = true)
    var id_eleve: Int = 0,
    var nom: String = "",
    var prenom: String = "",
    var data_naissance : String = "",
    var password : String = "",
    )