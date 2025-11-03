package fr.iutvannes.dual.DAO

import androidx.room.*
import fr.iutvannes.dual.Persistence.Prof

@Dao
interface ProfDAO {

    @Insert
    suspend fun insert(prof: Prof): Long

    @Query("DELETE FROM Prof WHERE id_prof = :idProf")
    suspend fun delete(idProf: Int): Int

    @Query("SELECT * FROM Prof")
    suspend fun getAll(): List<Prof>

    @Query("SELECT * FROM Prof WHERE id_prof = :idProf")
    suspend fun getProfById(idProf: Int): Prof?

    @Update
    suspend fun update(prof: Prof): Int
}