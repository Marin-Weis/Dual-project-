package fr.iutvannes.dual.model.dao

import androidx.room.*
import fr.iutvannes.dual.model.persistence.Seance

@Dao
interface SeanceDAO {

    @Insert
    suspend fun insert(seance: Seance): Long

    @Query("DELETE FROM Seance WHERE id_seance = :idSeance")
    suspend fun delete(idSeance: Int): Int

    @Query("SELECT * FROM Seance")
    suspend fun getAll(): List<Seance>

    @Query("SELECT * FROM Seance WHERE id_seance = :idSeance")
    suspend fun getSeanceById(idSeance: Int): Seance?

    @Update
    suspend fun update(seance: Seance): Int
}