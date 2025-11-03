import androidx.room.*
import fr.iutvannes.dual.Persistence.Resultat

@Dao
interface ResultatDAO {

    @Insert
    suspend fun insert(resultat: Resultat): Long

    @Query("DELETE FROM Resultat WHERE id_resultat = :idResultat")
    suspend fun delete(idResultat: Int): Int

    @Query("SELECT * FROM Resultat")
    suspend fun getAll(): List<Resultat>

    @Query("SELECT * FROM Resultat WHERE id_resultat = :idResultat")
    suspend fun getResultatById(idResultat: Int): Prof?

    @Update
    suspend fun update(resultat: Resultat): Int
}