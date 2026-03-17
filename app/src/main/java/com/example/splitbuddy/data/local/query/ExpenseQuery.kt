package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.ExpenseTable
import com.example.splitbuddy.data.local.model.Expense
import com.example.splitbuddy.data.local.model.InsertExpense

class ExpenseQuery(private val dbHelper: Database) {

    // INSERT

    fun insertExpense(expense: InsertExpense): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseTable.ID, expense.id)
        cv.put(ExpenseTable.TITLE, expense.title)
        cv.put(ExpenseTable.AMOUNT, expense.amount)
        cv.put(ExpenseTable.PAID_BY_USER, expense.paidByUser)
        cv.put(ExpenseTable.TRIP_ID, expense.tripId)
        cv.put(ExpenseTable.CREATED_AT, expense.createdAt)
        cv.put(ExpenseTable.UPDATED_AT, expense.updatedAt)

        return db.insert(ExpenseTable.TABLE_NAME, null, cv) > 0
    }

    // UPDATE

    fun updateExpense(expense: Expense): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseTable.TITLE, expense.title)
        cv.put(ExpenseTable.AMOUNT, expense.amount)
        cv.put(ExpenseTable.UPDATED_AT, expense.updatedAt)
        cv.put(ExpenseTable.IS_DELETED, expense.isDeleted)

        return db.update(
            ExpenseTable.TABLE_NAME,
            cv,
            "${ExpenseTable.ID} = ?",
            arrayOf(expense.id)
        ) > 0
    }

    // SELECT SINGLE EXPENSE

    fun getExpense(expenseId: String): Expense? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${ExpenseTable.TABLE_NAME} WHERE ${ExpenseTable.ID} = ?",
            arrayOf(expenseId)
        )

        var expense: Expense? = null

        if (cursor.moveToFirst()) {
            expense = Expense(
                id = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.TITLE)),
                amount = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseTable.AMOUNT)),
                paidByUser = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.PAID_BY_USER)),
                tripId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.TRIP_ID)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.UPDATED_AT)),
                isDeleted = cursor.getInt(
                    cursor.getColumnIndexOrThrow(ExpenseTable.IS_DELETED)
                ) == 1
            )
        }

        cursor.close()
        return expense
    }
}