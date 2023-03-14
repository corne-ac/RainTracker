package com.corne.raintracker.data


import RainfallEntry
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Define the Room database for RainfallEntry objects
@Database(entities = [RainfallEntry::class], version = 1, exportSchema = false)
abstract class RainfallDatabase : RoomDatabase() {

    // Provide a method to access the RainfallEntryDao
    abstract fun rainfallEntryDao(): RainfallDao

    companion object {
        @Volatile
        private var INSTANCE: RainfallDatabase? = null

        fun getDatabase(context: Context): RainfallDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RainfallDatabase::class.java,
                    "RainfallDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}