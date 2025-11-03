package fr.iutvannes.dual.Persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prof")
data class Prof(
    @PrimaryKey(autoGenerate = true)
    var id_prof: Int = 0,
    var nom: String = "",
    var prenom: String = "",
    var email: String = "",
    var password: String = ""
)
