package com.corne.raintracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

// Defines the database table for storing rainfall entries
@Entity(tableName = "rainfall_entries")
data class RainfallEntry(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0, // Unique ID for each entry
    var date: Date, // Date the rainfall occurred
    var amount: Double // Amount of rainfall in inches
)