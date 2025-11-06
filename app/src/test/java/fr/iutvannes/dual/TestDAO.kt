package fr.iutvannes.dual

import androidx.room.Room
import fr.iutvannes.dual.model.database.AppDatabase
import fr.iutvannes.dual.model.persistence.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class TestDAO {

    private lateinit var db: AppDatabase

    @Before
    fun initDb() {
        val context = RuntimeEnvironment.getApplication()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }


    @After
    fun closeDb() {
        db.close()
    }

    // Test eleve
    @Test
    fun testInsertAndGetEleve() = runBlocking {
        val eleve = Eleve(nom = "Dupont", prenom = "Jean", data_naissance = "2004-06-14", password = "Password1.")
        val id = db.EleveDao().insert(eleve).toInt()

        val fetched = db.EleveDao().getEleveById(id)
        assertNotNull(fetched)
        assertEquals("Dupont", fetched?.nom)
    }

    @Test
    fun testUpdateEleve() = runBlocking {
        val eleve = Eleve(nom = "Durand", prenom = "Paul", data_naissance = "2003-04-10", password = "Password1.")
        val id = db.EleveDao().insert(eleve).toInt()

        val updated = eleve.copy(id_eleve = id, nom = "Durand-Modifié")
        val rows = db.EleveDao().update(updated)

        val fetched = db.EleveDao().getEleveById(id)
        assertEquals(1, rows)
        assertEquals("Durand-Modifié", fetched?.nom)
    }

    @Test
    fun testDeleteEleve() = runBlocking {
        val eleve = Eleve(nom = "Martin", prenom = "Léa", data_naissance = "2005-02-18", password = "Password1.")
        val id = db.EleveDao().insert(eleve).toInt()

        val rowsDeleted = db.EleveDao().delete(id)
        assertEquals(1, rowsDeleted)

        val fetched = db.EleveDao().getEleveById(id)
        assertNull(fetched)
    }

    // Test prof
    @Test
    fun testInsertAndGetProfByEmail() = runBlocking {
        val prof = Prof(nom = "Tibermacine", prenom = "Chouki", email = "chouki.tibermacine@iutvannes.fr", password = "Password1.")
        val id = db.profDAO().insert(prof).toInt()

        val fetched = db.profDAO().getProfByEmail("chouki.tibermacine@iutvannes.fr")
        assertNotNull(fetched)
        assertEquals("Chouki", fetched?.prenom)
    }

    // Test seance
    @Test
    fun testInsertSeance() = runBlocking {
        val profId = db.profDAO().insert(
            Prof(nom = "Kamp", prenom = "Jean-François", email = "jf.kamp@iutvannes.fr", password = "Password1.")
        ).toInt()

        val seance = Seance(date = "2025-11-06", nb_tours = 3, nb_cibles = 5, id_prof = profId.toString())
        val id = db.seanceDao().insert(seance).toInt()

        val fetched = db.seanceDao().getSeanceById(id)
        assertNotNull(fetched)
        assertEquals(3, fetched?.nb_tours)
    }

    // Test resultat
    @Test
    fun testInsertResultat() = runBlocking {
        val eleveId = db.EleveDao().insert(Eleve(nom = "Bernard", prenom = "Luc", data_naissance = "2004-01-01", password = "Password1.")).toInt()
        val profId = db.profDAO().insert(Prof(nom = "Lemoine", prenom = "Anne", email = "anne@iutvannes.fr", password = "Password1.")).toInt()
        val seanceId = db.seanceDao().insert(Seance(date = "2025-05-10", nb_tours = 4, nb_cibles = 5, id_prof = profId.toString())).toInt()

        val resultat = Resultat(id_eleve = eleveId, id_seance = seanceId, temp_course = 25.3F, cibles_touchees = 4, note_finale = 15.5F)
        val id = db.resultatDao().insert(resultat).toInt()

        val fetched = db.resultatDao().getResultatById(id)
        assertNotNull(fetched)
        assertEquals(4, fetched?.cibles_touchees)
    }
}
