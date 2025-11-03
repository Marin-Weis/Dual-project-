package fr.iutvannes.dual.Persistence

import androidx.room.*


@Entity("Resultat")
data class Resultat (
    @PrimaryKey(autoGenerate = true)
    var id_resultat: Int=0,
    var id_eleve : Int = 0,
    var id_seance: Int = 0,
    var temp_course : Float = 0F,
    var cibles_touchees: Int = 0,
    var penalites: Float = 0F,
    var vma: Float = 0F,
    var note_finale: Float = 0F,
    var classement: Int = 0
)