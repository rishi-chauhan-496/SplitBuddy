package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.ExpenseUserLedgerTable
import com.example.splitbuddy.data.local.model.ExpenseUserLedger
import com.example.splitbuddy.data.local.model.InsertExpenseUserLedger

class ExpenseUserLedgerQuery(private val dbHelper: Database) {

    // INSERT
    fun insertLedger(data: InsertExpenseUserLedger): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseUserLedgerTable.ID, data.id)
        cv.put(ExpenseUserLedgerTable.EXPENSE_DETAIL_ID, data.expenseDetailId)
        cv.put(ExpenseUserLedgerTable.USER_ID, data.userId)
        cv.put(ExpenseUserLedgerTable.SHARED_AMOUNT, data.sharedAmount)
        cv.put(ExpenseUserLedgerTable.CREATED_AT, data.createdAt)
        cv.put(ExpenseUserLedgerTable.UPDATED_AT, data.updatedAt)

        return db.insert(
            ExpenseUserLedgerTable.TABLE_NAME,
            null,
            cv
        ) > 0
    }

    // UPDATE
    fun updateLedger(data: ExpenseUserLedger): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseUserLedgerTable.SHARED_AMOUNT, data.sharedAmount)
        cv.put(ExpenseUserLedgerTable.UPDATED_AT, data.updatedAt)
        cv.put(ExpenseUserLedgerTable.IS_DELETED, data.isDeleted)

        return db.update(
            ExpenseUserLedgerTable.TABLE_NAME,
            cv,
            "${ExpenseUserLedgerTable.ID} = ?",
            arrayOf(data.id)
        ) > 0
    }

    // SELECT SINGLE
    fun getLedger(id: String): ExpenseUserLedger? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${ExpenseUserLedgerTable.TABLE_NAME} WHERE ${ExpenseUserLedgerTable.ID} = ?",
            arrayOf(id)
        )

        var ledger: ExpenseUserLedger? = null

        if (cursor.moveToFirst()) {
            ledger = ExpenseUserLedger(
                id = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseUserLedgerTable.ID)),
                expenseDetailId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseUserLedgerTable.EXPENSE_DETAIL_ID)),
                userId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseUserLedgerTable.USER_ID)),
                sharedAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseUserLedgerTable.SHARED_AMOUNT)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseUserLedgerTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseUserLedgerTable.UPDATED_AT)),
                isDeleted = cursor.getInt(
                    cursor.getColumnIndexOrThrow(ExpenseUserLedgerTable.IS_DELETED)
                ) == 1
            )
        }

        cursor.close()
        return ledger
    }
}