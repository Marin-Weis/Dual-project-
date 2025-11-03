import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Seance")
data class Seance(
    @PrimaryKey(autoGenerate = true)
    var id_seance: Int = 0,
    var date: String,
    var nb_tours: Int,
    var nb_cibles: Int,
    var id_prof: String
)