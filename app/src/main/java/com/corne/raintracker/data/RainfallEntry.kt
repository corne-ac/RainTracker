package com.corne.raintracker.data
import androidx.room.PrimaryKey

data class RainfallEntry(
    var date: Long,
    var time: Long,
    var amount: Double,
    var note: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
