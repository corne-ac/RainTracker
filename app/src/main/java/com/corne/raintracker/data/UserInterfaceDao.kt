package com.corne.raintracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserInterfaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: RainTracker)

    @Query("SELECT * FROM user_interface WHERE id = :id")
    suspend fun getSettings(id: Long): RainTracker
}