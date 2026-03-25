package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.SettlementTable
import com.example.splitbuddy.data.local.model.Settlement

class SettlementQuery(private val dbHelper: Database) {

    // INSERT
    fun insertSettlement(data: Settlement): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(SettlementTable.ID, data.id)
        cv.put(SettlementTable.USER_ID, data.userId)
        cv.put(SettlementTable.TRIP_ID, data.tripId)
        cv.put(SettlementTable.USER_FINAL_CONTRIBUTION, data.userFinalContribution)
        cv.put(SettlementTable.USER_FINAL_SHARED_AMOUNT, data.userFinalSharedAmount)
        cv.put(SettlementTable.SETTLEMENT_AMT, data.settlementAmt)
        cv.put(SettlementTable.CREATED_AT, data.createdAt)
        cv.put(SettlementTable.UPDATED_AT, data.updatedAt)

        return db.insert(SettlementTable.TABLE_NAME, null, cv) > 0
    }

    // UPDATE
    fun updateSettlement(data: Settlement): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(SettlementTable.USER_FINAL_CONTRIBUTION, data.userFinalContribution)
        cv.put(SettlementTable.USER_FINAL_SHARED_AMOUNT, data.userFinalSharedAmount)
        cv.put(SettlementTable.SETTLEMENT_AMT, data.settlementAmt)
        cv.put(SettlementTable.UPDATED_AT, data.updatedAt)

        return db.update(
            SettlementTable.TABLE_NAME,
            cv,
            "${SettlementTable.ID} = ?",
            arrayOf(data.id)
        ) > 0
    }

    // SELECT BY TRIP
    fun getSettlementByTrip(tripId: String): List<Settlement> {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${SettlementTable.TABLE_NAME} WHERE ${SettlementTable.TRIP_ID} = ?",
            arrayOf(tripId)
        )

        val list = mutableListOf<Settlement>()

        while (cursor.moveToNext()) {
            list.add(
                Settlement(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.ID)),
                    userId = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.USER_ID)),
                    tripId = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.TRIP_ID)),
                    userFinalContribution = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.USER_FINAL_CONTRIBUTION)),
                    userFinalSharedAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.USER_FINAL_SHARED_AMOUNT)),
                    settlementAmt = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.SETTLEMENT_AMT)),
                    createdAt = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.CREATED_AT)),
                    updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.UPDATED_AT)),
                    isDeleted = cursor.getInt(
                        cursor.getColumnIndexOrThrow(SettlementTable.IS_DELETED)
                    ) == 1
                )
            )
        }

        cursor.close()
        return list
    }

    fun getSettlementByUser(userId: String): List<Settlement> {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${SettlementTable.TABLE_NAME} WHERE ${SettlementTable.USER_ID} = ?",
            arrayOf(userId)
        )

        val list = mutableListOf<Settlement>()

        while (cursor.moveToNext()) {
            list.add(
                Settlement(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.ID)),
                    userId = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.USER_ID)),
                    tripId = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.TRIP_ID)),
                    userFinalContribution = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.USER_FINAL_CONTRIBUTION)),
                    userFinalSharedAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.USER_FINAL_SHARED_AMOUNT)),
                    settlementAmt = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.SETTLEMENT_AMT)),
                    createdAt = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.CREATED_AT)),
                    updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.UPDATED_AT)),
                    isDeleted = cursor.getInt(
                        cursor.getColumnIndexOrThrow(SettlementTable.IS_DELETED)
                    ) == 1
                )
            )
        }

        cursor.close()
        return list
    }

    fun getSettlementByTripAndUser(tripId: String, userId: String): List<Settlement> {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${SettlementTable.TABLE_NAME} WHERE ${SettlementTable.TRIP_ID} = ? AND ${SettlementTable.USER_ID} = ?",
            arrayOf(tripId,userId)
        )

        val list = mutableListOf<Settlement>()

        while (cursor.moveToNext()) {
            list.add(
                Settlement(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.ID)),
                    userId = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.USER_ID)),
                    tripId = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.TRIP_ID)),
                    userFinalContribution = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.USER_FINAL_CONTRIBUTION)),
                    userFinalSharedAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.USER_FINAL_SHARED_AMOUNT)),
                    settlementAmt = cursor.getDouble(cursor.getColumnIndexOrThrow(SettlementTable.SETTLEMENT_AMT)),
                    createdAt = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.CREATED_AT)),
                    updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(SettlementTable.UPDATED_AT)),
                    isDeleted = cursor.getInt(
                        cursor.getColumnIndexOrThrow(SettlementTable.IS_DELETED)
                    ) == 1
                )
            )
        }

        cursor.close()
        return list
    }
}