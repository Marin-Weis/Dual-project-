package fr.iutvannes.dual.database

import ProfDAO
import ResultatDAO
import SeanceDAO
import androidx.room.Database;
import androidx.room.RoomDatabase;

import fr.iutvannes.dual.Persistence.*;
import fr.iutvannes.dual.DAO.EleveDAO


@Database(entities = [Eleve :: class, Prof :: class, Resultat :: class, Seance :: class], version = 1)
abstract class AppDatabase {
    abstract fun eleveDao() : EleveDAO
    abstract fun profDao() : ProfDAO
    abstract fun resultatDao() : ResultatDAO
}