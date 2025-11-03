package fr.iutvannes.dual.DAO

import androidx.room.*
import fr.iutvannes.dual.Persistence.Eleve

@Dao
interface EleveDAO {

    @Insert
    suspend fun insert(eleve: Eleve): Long

    @Query("SELECT * FROM Eleve WHERE id_eleve = :idELeve")
    suspend fun delete(idELeve: Int): Int

    @Query("SELECT * FROM ELeve")
    suspend fun getAll(): List<Eleve>

    @Query("SELECT * FROM Eleve WHERE id_eleve = :idEleve")
    suspend fun getEleveById(idEleve: Int) : Eleve

    suspend fun update(eleve: Eleve) : Int
}