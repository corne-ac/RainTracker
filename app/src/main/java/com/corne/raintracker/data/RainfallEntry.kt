import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*

@Entity(tableName = "rainfall_entries")
data class RainfallEntry(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "time") var time: Time,
    @ColumnInfo(name = "amount") var amount: Double,
    @ColumnInfo(name = "note") var note: String
)

