package com.corne.raintracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Defines the Room database for storing RainTracker data
@Database(entities = [RainfallEntry::class], version = 1)
abstract class RainTrackerDatabase : RoomDatabase() {
    // Provides a DAO for accessing the RainfallEntry table
    abstract fun rainfallDao(): RainfallDao

    companion object {
        @Volatile
        private var INSTANCE: RainTrackerDatabase? = null

        // Gets the RainTrackerDatabase instance, creating it if it doesn't exist yet
        fun getDatabase(context: Context): RainTrackerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RainTrackerDatabase::class.java,
                    "rain_tracker_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
