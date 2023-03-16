package com.corne.raintracker.data

//val DATABASE_VERSION: Int = 1
//val DATABASE_NAME: String? = "RainfallDB"
//private const val SQL_CREATE_ENTRIES =
//
//
//class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//    override fun onCreate(db: SQLiteDatabase?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        super.onDowngrade(db, oldVersion, newVersion)
//    }
//}

import RainfallEntry
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "rainfall_entries.db"
        private const val DATABASE_VERSION = 1

        // Table Name
        private const val TABLE_NAME = "rainfall_entries"

        // Column Names
        private const val COLUMN_ID = "id"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_TIME = "time"
        private const val COLUMN_AMOUNT = "amount"
        private const val COLUMN_NOTE = "note"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create Table
        val createTableSQL = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_DATE INTEGER, " +
                "$COLUMN_TIME INTEGER, " +
                "$COLUMN_AMOUNT REAL, " +
                "$COLUMN_NOTE TEXT" +
                ")"
        db?.execSQL(createTableSQL)
    }

    fun isDatabaseExist(context: Context): Boolean {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        return dbFile.exists()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop older table if exists
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")

        // Create tables again
        onCreate(db)
    }

    fun insertRainfallEntry(entry: RainfallEntry): Long {
        // Get writable database as we want to write data
        val db = this.writableDatabase

        // Prepare the values to be inserted
        val values = ContentValues()
        values.put(COLUMN_DATE, entry.date)
        values.put(COLUMN_TIME, entry.time)
        values.put(COLUMN_AMOUNT, entry.amount)
        values.put(COLUMN_NOTE, entry.note)

        // Insert row
        val id = db.insert(TABLE_NAME, null, values)

        // Close database connection
        db.close()

        // Return the inserted row id
        return id
    }

    fun getRainfallEntries(): List<RainfallEntry> {
        val entries = mutableListOf<RainfallEntry>()

        // Select All Query
        val selectQuery = "SELECT * FROM $TABLE_NAME"

        // Get readable database as we are not inserting data
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        // Loop through all rows and add to list
        val columnIndexDate = cursor.getColumnIndex(COLUMN_DATE)
        val columnIndexTime = cursor.getColumnIndex(COLUMN_TIME)
        val columnIndexAmount = cursor.getColumnIndex(COLUMN_AMOUNT)
        val columnIndexNote = cursor.getColumnIndex(COLUMN_NOTE)
        val columnIndexId = cursor.getColumnIndex(COLUMN_ID)

        if (columnIndexDate >= 0 && columnIndexTime >= 0 && columnIndexAmount >= 0 && columnIndexNote >= 0 && columnIndexId >= 0) {
            if (cursor.moveToFirst()) {
                do {
                    val entry = RainfallEntry(
                        cursor.getLong(columnIndexDate),
                        cursor.getLong(columnIndexTime),
                        cursor.getDouble(columnIndexAmount),
                        cursor.getString(columnIndexNote)
                    )
                    entry.id = cursor.getLong(columnIndexId)
                    entries.add(entry)
                } while (cursor.moveToNext())
            }
        }

        // Close cursor and database connection
        cursor.close()
        db.close()

        // Return the list of entries
        return entries
    }

    fun deleteRainfallEntry(entry: RainfallEntry) {
        // Get writable database as we want to write data
        val db = this.writableDatabase

        // Delete row
        db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(entry.id.toString()))

        // Close database connection
        db.close()
    }
}
