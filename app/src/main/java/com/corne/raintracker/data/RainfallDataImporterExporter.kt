package com.corne.raintracker.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*

class RainfallDataImporter(private val context: Context) {

    // Imports rainfall data from a JSON file
    suspend fun importFromJson(jsonFile: String) {
        // Open a file input stream to read the JSON file
        val inputStream: InputStream = context.assets.open(jsonFile)
        // Create a buffered reader to read the file
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        // Use GSON to parse the JSON data into a List of RainfallEntry objects
        val typeToken = object : TypeToken<List<RainfallEntry>>() {}.type
        val data: List<RainfallEntry> = Gson().fromJson(bufferedReader, typeToken)

        // Get the database's DAO object for RainfallEntry
        val dao = RainTrackerDatabase.getDatabase(context).rainfallDao()
        // Insert each entry from the JSON data into the database
        for (entry in data) {
            dao.insertEntry(entry)
        }
    }

    // Exports rainfall data to a JSON file
    suspend fun exportToJson(jsonFile: String) {
        // Get the database's DAO object for RainfallEntry
        val dao = RainTrackerDatabase.getDatabase(context).rainfallDao()
        // Retrieve all the RainfallEntry objects from the database
        val data = dao.getAllEntries()

        // Create a new file in the app's external files directory with the given filename
        val file = File(context.getExternalFilesDir(null), jsonFile)
        // Create a file output stream to write the JSON data to the file
        val outputStream = FileOutputStream(file)
        // Convert the rainfall data to JSON using GSON
        val json = Gson().toJson(data)
        // Write the JSON data to the file and close the stream
        outputStream.write(json.toByteArray())
        outputStream.close()
    }
}