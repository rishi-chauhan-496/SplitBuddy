package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.ExpenseShareTable
import com.example.splitbuddy.data.local.model.ExpenseShare
import com.example.splitbuddy.data.remote.expense.Share

class ExpenseShareQuery(private val dbHelper: Database) {

    // INSERT
    fun insertExpenseShare(data: Share): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseShareTable.ID, data.id)
        cv.put(ExpenseShareTable.EXPENSE_ID, data.expenseId)
        cv.put(ExpenseShareTable.USER_ID, data.userId)
        cv.put(ExpenseShareTable.SHARED_AMOUNT, data.shareAmount)
        cv.put(ExpenseShareTable.SHARED_PERCENT, data.sharePercent)
        cv.put(ExpenseShareTable.CREATED_AT, data.createdAt)
        cv.put(ExpenseShareTable.UPDATED_AT, data.updatedAt)

        return db.insertWithOnConflict(
            ExpenseShareTable.TABLE_NAME,
            null,
            cv,
            SQLiteDatabase.CONFLICT_REPLACE
        ) > 0
    }

    // UPDATE
    fun updateLedger(data: ExpenseShare): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseShareTable.SHARED_AMOUNT, data.sharedAmount)
        cv.put(ExpenseShareTable.SHARED_PERCENT, data.sharedPercent)
        cv.put(ExpenseShareTable.UPDATED_AT, data.updatedAt)

        return db.update(
            ExpenseShareTable.TABLE_NAME,
            cv,
            "${ExpenseShareTable.ID} = ?",
            arrayOf(data.id)
        ) > 0
    }

    // SELECT SINGLE
    fun getLedger(id: String): ExpenseShare? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${ExpenseShareTable.TABLE_NAME} WHERE ${ExpenseShareTable.ID} = ?",
            arrayOf(id)
        )

        var ledger: ExpenseShare? = null

        if (cursor.moveToFirst()) {
            ledger = ExpenseShare(
                id = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.ID)),
                expenseId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.EXPENSE_ID)),
                userId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.USER_ID)),
                sharedAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseShareTable.SHARED_AMOUNT)),
                sharedPercent = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseShareTable.SHARED_PERCENT)),
                isIncluded = cursor.getInt(
                    cursor.getColumnIndexOrThrow(ExpenseShareTable.IS_INCLUDED)
                ) == 1,
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.UPDATED_AT)),
                isDeleted = cursor.getInt(
                    cursor.getColumnIndexOrThrow(ExpenseShareTable.IS_DELETED)
                ) == 1
            )
        }

        cursor.close()
        return ledger
    }

    fun getSharesByExpenseId(expenseId: String): List<ExpenseShare> {
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${ExpenseShareTable.TABLE_NAME} WHERE ${ExpenseShareTable.EXPENSE_ID} = ? AND ${ExpenseShareTable.IS_DELETED} = 0",
            arrayOf(expenseId)
        )

        val shares = mutableListOf<ExpenseShare>()

        if (cursor.moveToFirst()) {
            do {
                shares.add(
                    ExpenseShare(
                        id = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.ID)),
                        expenseId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.EXPENSE_ID)),
                        userId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.USER_ID)),
                        sharedAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseShareTable.SHARED_AMOUNT)),
                        sharedPercent = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseShareTable.SHARED_PERCENT)),
                        isIncluded = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseShareTable.IS_INCLUDED)) == 1,
                        createdAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.CREATED_AT)),
                        updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseShareTable.UPDATED_AT)),
                        isDeleted = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseShareTable.IS_DELETED)) == 1
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        return shares
    }
}