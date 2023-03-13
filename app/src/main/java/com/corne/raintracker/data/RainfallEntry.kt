package com.corne.raintracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

// Defines the database table for storing rainfall entries
@Entity(tableName = "rainfall_entries")
data class RainfallEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null, // Unique ID for each entry
    var date: LocalDate, // Date the rainfall occurred
    var amount: Double, // Amount of rainfall in inches
    var notes: String // Notes related to rainfall entry
)