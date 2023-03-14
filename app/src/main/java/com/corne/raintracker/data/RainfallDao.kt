package com.corne.raintracker.data

import RainfallEntry
import androidx.room.*
import java.sql.Date

// Define a DAO (data access object) for RainfallEntry objects
@Dao
interface RainfallDao {
    // Insert a new RainfallEntry into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEntry(entry: RainfallEntry)

    // Get all RainfallEntry objects from the database
    @Query("SELECT * FROM rainfall_entries ORDER BY date ASC")
    fun getAllEntries(): List<RainfallEntry>

    // Get a RainfallEntry by ID from the database
    @Query("SELECT * FROM rainfall_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): RainfallEntry?

    // Get all RainfallEntry objects for a given date from the database
    @Query("SELECT * FROM rainfall_entries WHERE date >= :dateStart AND date <= :dateEnd" )
    suspend fun getEntriesByDate(dateStart: Date, dateEnd: Date): List<RainfallEntry>

    // Delete a RainfallEntry from the database
    @Delete
    suspend fun deleteEntry(entry: RainfallEntry): Int
}

