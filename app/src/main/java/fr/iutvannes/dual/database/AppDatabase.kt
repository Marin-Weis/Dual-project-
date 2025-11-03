package fr.iutvannes.dual.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.iutvannes.dual.Persistence.Eleve
import fr.iutvannes.dual.Persistence.Prof
import fr.iutvannes.dual.Persistence.Resultat
import fr.iutvannes.dual.Persistence.Seance
import fr.iutvannes.dual.DAO.EleveDAO
import fr.iutvannes.dual.DAO.ProfDAO
import fr.iutvannes.dual.DAO.ResultatDAO
import fr.iutvannes.dual.DAO.SeanceDAO

@Database(
    entities = [Eleve::class, Prof::class, Resultat::class, Seance::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eleveDao(): EleveDAO
    abstract fun profDao(): ProfDAO
    abstract fun resultatDao(): ResultatDAO
    abstract fun seanceDao(): SeanceDAO
}
