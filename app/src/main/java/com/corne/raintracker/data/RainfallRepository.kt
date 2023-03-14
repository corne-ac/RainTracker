package com.corne.raintracker.data

import RainfallEntry
import androidx.annotation.WorkerThread
import java.sql.Date

class RainfallRepository(private val rainfallDao: RainfallDao) {

    val AllRainfallLogs: List<RainfallEntry> = rainfallDao.getAllEntries()

    @WorkerThread
    suspend fun insertRainfall(rainfallEntry: RainfallEntry) {
        rainfallDao.addEntry(rainfallEntry)
    }

    @WorkerThread
    suspend fun getRainfallByID(id: Long): RainfallEntry? {
        return rainfallDao.getEntryById(id)
    }

    @WorkerThread
    suspend fun getRainfallBetweenDates(dateStart: Date, dateEnd: Date): List<RainfallEntry> {
        return rainfallDao.getEntriesByDate(dateStart, dateEnd)
    }

    @WorkerThread
    suspend fun deleteRainfall(rainfallEntry: RainfallEntry): Boolean {
        return rainfallDao.deleteEntry(rainfallEntry) > 0 //returns true if deleted
    }

}
