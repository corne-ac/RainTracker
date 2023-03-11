package com.corne.raintracker.data

import android.content.Context

// Main class for the RainTracker app
class RainTracker(
    var name: String,
    var description: String,
    var version: String,
    var context: Context,
    var userId: Long
) {
    // Instance of the RainTrackerDatabase for accessing rainfall data
    private val database = RainTrackerDatabase.getDatabase(context)

    // Retrieves all the rainfall entries from the database and displays them
    suspend fun showRainfallData() {
        val dao = database.rainfallDao()
        val entries = dao.getAllEntries()
        // Display the rainfall data
    }
}