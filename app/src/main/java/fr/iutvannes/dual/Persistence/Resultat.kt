package fr.iutvannes.dual.Persistence

import androidx.room.*


@Entity("Resultat")
data class Resultat (
    @PrimaryKey(autoGenerate = true)
    var id_resultat: Int=0,
    var id_eleve : Int,
    var id_seance: Int,
    var temp_course : Float,
    var cibles_touchees: Int,
    var penalites: Float,
    var vma: Float,
    var note_finale: Float,
    var classement: Int
)