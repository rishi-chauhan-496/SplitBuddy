package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.model.ExpenseDetails
import com.example.splitbuddy.data.local.database.Database.ExpenseDetailsTable

class ExpenseDetailsQuery(private val dbHelper: Database) {

    // INSERT
    fun insertExpenseDetails(details: ExpenseDetails): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseDetailsTable.ID, details.id)
        cv.put(ExpenseDetailsTable.SPLIT_TYPE_ID, details.splitTypeId)
        cv.put(ExpenseDetailsTable.EXPENSE_ID, details.expenseId)
        cv.put(ExpenseDetailsTable.CREATED_AT, details.createdAt)
        cv.put(ExpenseDetailsTable.UPDATED_AT, details.updatedAt)

        return db.insert(
            ExpenseDetailsTable.TABLE_NAME,
            null,
            cv
        ) > 0
    }

    // UPDATE
    fun updateExpenseDetails(details: ExpenseDetails): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseDetailsTable.SPLIT_TYPE_ID, details.splitTypeId)
        cv.put(ExpenseDetailsTable.UPDATED_AT, details.updatedAt)

        return db.update(
            ExpenseDetailsTable.TABLE_NAME,
            cv,
            "${ExpenseDetailsTable.ID} = ?",
            arrayOf(details.id)
        ) > 0
    }

    // SELECT SINGLE
    fun getExpenseDetails(id: String): ExpenseDetails? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${ExpenseDetailsTable.TABLE_NAME} WHERE ${ExpenseDetailsTable.ID} = ?",
            arrayOf(id)
        )

        var details: ExpenseDetails? = null

        if (cursor.moveToFirst()) {
            details = ExpenseDetails(
                id = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseDetailsTable.ID)),
                splitTypeId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseDetailsTable.SPLIT_TYPE_ID)),
                expenseId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseDetailsTable.EXPENSE_ID)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseDetailsTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseDetailsTable.UPDATED_AT)),
                isDeleted = cursor.getInt(
                    cursor.getColumnIndexOrThrow(ExpenseDetailsTable.IS_DELETED)
                ) == 1
            )
        }

        cursor.close()
        return details
    }
}