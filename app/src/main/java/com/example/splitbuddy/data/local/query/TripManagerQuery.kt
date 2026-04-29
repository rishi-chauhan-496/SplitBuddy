package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.TripManagerTable
import com.example.splitbuddy.data.local.model.TripManager
import com.example.splitbuddy.data.remote.group.Member
import kotlin.collections.mutableListOf

class TripManagerQuery(private val dbHelper: Database) {

    // ---------------- INSERT ----------------

    fun insertTripManager(manager: Member): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(TripManagerTable.ID, manager.id)
        cv.put(TripManagerTable.TRIP_ID, manager.groupId)
        cv.put(TripManagerTable.USER_ID, manager.userId)
        cv.put(TripManagerTable.ROLE, manager.role)
        cv.put(TripManagerTable.JOINED_AT, manager.joinedAt)
        cv.put(TripManagerTable.LEFT_AT, manager.leftAt)
        cv.put(TripManagerTable.CREATED_AT, manager.createdAt)
        cv.put(TripManagerTable.UPDATED_AT, manager.updatedAt)

        return db.insertWithOnConflict(TripManagerTable.TABLE_NAME, null, cv,
            SQLiteDatabase.CONFLICT_REPLACE) > 0
    }

    // ---------------- UPDATE ----------------

    fun updateTripManager(manager: Member): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(TripManagerTable.IS_ACTIVE, manager.isActive)
        cv.put(TripManagerTable.IS_DELETED, manager.isDeleted)
        cv.put(TripManagerTable.ROLE, manager.role)
        cv.put(TripManagerTable.UPDATED_AT, manager.updatedAt)

        return db.update(
            TripManagerTable.TABLE_NAME,
            cv,
            "${TripManagerTable.ID} = ?",
            arrayOf(manager.id)
        ) > 0
    }

    // ---------------- SELECT SINGLE ----------------

    fun getTripManagerByUserIdAndTripId(userId: String, tripId: String): TripManager? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${TripManagerTable.TABLE_NAME} WHERE ${TripManagerTable.USER_ID} = ? AND ${TripManagerTable.TRIP_ID} = ?",
            arrayOf(userId, tripId)
        )

        var manager: TripManager? = null

        if (cursor.moveToFirst()) {
            manager = TripManager(
                id = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.ID)),
                tripId = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.TRIP_ID)),
                userId = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.USER_ID)),
                role = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.ROLE)),
                isActive = cursor.getInt(
                    cursor.getColumnIndexOrThrow(TripManagerTable.IS_ACTIVE)
                ) == 1,
                joinedAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.JOINED_AT)),
                leftAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.LEFT_AT)),
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

    fun getTripManagerByUserId(userId: String): List<TripManager> {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${TripManagerTable.TABLE_NAME} WHERE ${TripManagerTable.USER_ID} = ?",
            arrayOf(userId)
        )

        val manager = mutableListOf<TripManager>()

        if (cursor.moveToFirst()) {
            do {
                manager.add(
                    TripManager(
                        id = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.ID)),
                        tripId = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.TRIP_ID)),
                        userId = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.USER_ID)),
                        role = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.ROLE)),
                        isActive = cursor.getInt(
                            cursor.getColumnIndexOrThrow(TripManagerTable.IS_ACTIVE)
                        ) == 1,
                        joinedAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.JOINED_AT)),
                        leftAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.LEFT_AT)),
                        createdAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.CREATED_AT)),
                        updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.UPDATED_AT)),
                        isDeleted = cursor.getInt(
                            cursor.getColumnIndexOrThrow(TripManagerTable.IS_DELETED)
                        ) == 1
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return manager
    }

    fun getTripManagerByTripId(tripId: String): List<TripManager> {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${TripManagerTable.TABLE_NAME} WHERE ${TripManagerTable.TRIP_ID} = ?",
            arrayOf(tripId)
        )

        val manager = mutableListOf<TripManager>()

        if (cursor.moveToFirst()) {
            do {
                manager.add(
                    TripManager(
                        id = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.ID)),
                        tripId = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.TRIP_ID)),
                        userId = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.USER_ID)),
                        role = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.ROLE)),
                        isActive = cursor.getInt(
                            cursor.getColumnIndexOrThrow(TripManagerTable.IS_ACTIVE)
                        ) == 1,
                        joinedAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.JOINED_AT)),
                        leftAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.LEFT_AT)),
                        createdAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.CREATED_AT)),
                        updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(TripManagerTable.UPDATED_AT)),
                        isDeleted = cursor.getInt(
                            cursor.getColumnIndexOrThrow(TripManagerTable.IS_DELETED)
                        ) == 1
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return manager
    }
}