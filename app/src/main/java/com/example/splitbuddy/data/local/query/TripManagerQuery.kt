package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.TripManagerTable
import com.example.splitbuddy.data.local.model.InsertTripManager
import com.example.splitbuddy.data.local.model.TripManager

class TripManagerQuery(private val dbHelper: Database) {

    // ---------------- INSERT ----------------

    fun insertTripManager(manager: InsertTripManager): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(TripManagerTable.ID, manager.id)
        cv.put(TripManagerTable.TRIP_ID, manager.tripId)
        cv.put(TripManagerTable.USER_ID, manager.userId)
        cv.put(TripManagerTable.CREATED_AT, manager.createdAt)
        cv.put(TripManagerTable.UPDATED_AT, manager.updatedAt)

        return db.insert(TripManagerTable.TABLE_NAME, null, cv) > 0
    }

    // ---------------- UPDATE ----------------

    fun updateTripManager(manager: TripManager): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(TripManagerTable.TRIP_ID, manager.tripId)
        cv.put(TripManagerTable.USER_ID, manager.userId)
        cv.put(TripManagerTable.UPDATED_AT, manager.updatedAt)
        cv.put(TripManagerTable.IS_DELETED, manager.isDeleted)

        return db.update(
            TripManagerTable.TABLE_NAME,
            cv,
            "${TripManagerTable.ID} = ?",
            arrayOf(manager.id)
        ) > 0
    }

    // ---------------- SELECT SINGLE ----------------

    fun getTripManager(id: String): TripManager? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${TripManagerTable.TABLE_NAME} WHERE ${TripManagerTable.ID} = ?",
            arrayOf(id)
        )

        var manager: TripManager? = null

        if (cursor.moveToFirst()) {
            manager = TripManager(
                id = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.ID)),
                tripId = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.TRIP_ID)),
                userId = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.USER_ID)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.UPDATED_AT)),
                isDeleted = cursor.getInt(
                    cursor.getColumnIndexOrThrow(TripManagerTable.IS_DELETED)
                ) == 1
            )
        }

        cursor.close()
        return manager
    }
}