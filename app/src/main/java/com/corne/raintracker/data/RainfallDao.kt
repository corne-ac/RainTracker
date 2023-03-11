package com.corne.raintracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Data Access Object (DAO) for accessing the RainfallEntry table in the database
@Dao
interface RainfallDao {
    // Inserts a new RainfallEntry into the database, or replaces an existing one if it has the same ID
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: RainfallEntry)

    // Retrieves all the entries from the RainfallEntry table
    @Query("SELECT * FROM rainfall_entries")
    suspend fun getAllEntries(): List<RainfallEntry>
}