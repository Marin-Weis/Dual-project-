package fr.iutvannes.dual.DAO

import androidx.room.*
import fr.iutvannes.dual.Persistence.Eleve

@Dao
interface EleveDAO {

    @Insert
    suspend fun insert(eleve: Eleve): Long

    @Query("DELETE FROM Eleve WHERE id_eleve = :idEleve")
    suspend fun delete(idEleve: Int): Int

    @Query("SELECT * FROM Eleve")
    suspend fun getAll(): List<Eleve>

    @Query("SELECT * FROM Eleve WHERE id_eleve = :idEleve")
    suspend fun getEleveById(idEleve: Int): Eleve?

    @Update
    suspend fun update(eleve: Eleve): Int
}