import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "rainfall_entries")
data class RainfallEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date,
    val amount: Double,
    val note: String
)

