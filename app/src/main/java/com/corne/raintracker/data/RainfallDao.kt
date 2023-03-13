package com.corne.raintracker.data

import RainfallEntry
import android.content.Context
import androidx.room.*
import java.sql.Date

// Define a DAO (data access object) for RainfallEntry objects
@Dao
interface RainfallEntryDao {
    // Insert a new RainfallEntry into the database
    @Insert
    suspend fun addEntry(entry: RainfallEntry)

    // Get all RainfallEntry objects from the database
    @Query("SELECT * FROM rainfall_entries")
    suspend fun getAllEntries(): List<RainfallEntry>

    // Get a RainfallEntry by ID from the database
    @Query("SELECT * FROM rainfall_entries WHERE id = :id")
    suspend fun getEntryById(id: Long): RainfallEntry?

    // Get all RainfallEntry objects for a given date from the database
    @Query("SELECT * FROM rainfall_entries WHERE date = :date")
    suspend fun getEntriesByDate(date: Date): List<RainfallEntry>

    // Delete a RainfallEntry from the database
    @Delete
    suspend fun deleteEntry(entry: RainfallEntry)
}

// Define the Room database for RainfallEntry objects
@Database(entities = [RainfallEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    // Provide a method to access the RainfallEntryDao
    abstract fun rainfallEntryDao(): RainfallEntryDao

    companion object {
        // Create a singleton instance of the AppDatabase
        @Volatile
        private var instance: AppDatabase? = null

        // Get the singleton instance of the AppDatabase
        fun getInstance(context: Context): AppDatabase {
            // If the instance hasn't been created yet, create it
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "rainfall_database"
                ).build().also { instance = it }
            }
        }
    }
}
