import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*

@Entity(tableName = "rainfall_entries")
data class RainfallEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val time: Time,
    val amount: Double,
    val note: String
)

