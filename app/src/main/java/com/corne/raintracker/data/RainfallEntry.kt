
import androidx.room.PrimaryKey

public data class RainfallEntry(
    var date: Long,
    var time: Long,
    var amount: Double,
    var note: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
