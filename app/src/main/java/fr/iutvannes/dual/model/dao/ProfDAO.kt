package fr.iutvannes.dual.model.dao

import androidx.room.*
import fr.iutvannes.dual.model.persistence.Prof

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

    @Query("SELECT * FROM Prof WHERE id_prof = :email")
    suspend fun getProfByEmail(email: String): Prof?

    @Update
    suspend fun update(prof: Prof): Int
}