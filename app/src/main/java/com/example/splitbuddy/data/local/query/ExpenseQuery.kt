package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.ExpenseTable
import com.example.splitbuddy.data.local.model.Expense

class ExpenseQuery(private val dbHelper: Database) {

    // INSERT

    fun insertExpense(expense: Expense): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(ExpenseTable.ID, expense.id)
        cv.put(ExpenseTable.TITLE, expense.title)
        cv.put(ExpenseTable.DESCRIPTION, expense.description)
        cv.put(ExpenseTable.AMOUNT, expense.amount)
        cv.put(ExpenseTable.SPLIT_METHOD, expense.splitMethod)
        cv.put(ExpenseTable.PAID_BY_USER, expense.paidByUser)
        cv.put(ExpenseTable.CURRENCY_CODE,expense.currencyCode)
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
        cv.put(ExpenseTable.DESCRIPTION, expense.description)
        cv.put(ExpenseTable.AMOUNT, expense.amount)
        cv.put(ExpenseTable.SPLIT_METHOD, expense.splitMethod)
        cv.put(ExpenseTable.PAID_BY_USER, expense.paidByUser)
        cv.put(ExpenseTable.UPDATED_AT, expense.updatedAt)

        return db.update(
            ExpenseTable.TABLE_NAME,
            cv,
            "${ExpenseTable.ID} = ?",
            arrayOf(expense.id)
        ) > 0
    }

    // SELECT SINGLE EXPENSE

    fun getExpenseByTripId(tripId: String): List<Expense> {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${ExpenseTable.TABLE_NAME} WHERE ${ExpenseTable.TRIP_ID} = ?",
            arrayOf(tripId)
        )

        val expense = mutableListOf<Expense>()

        if (cursor.moveToFirst()) {
            expense.add(
                Expense(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.TITLE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.DESCRIPTION)),
                    amount = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseTable.AMOUNT)),
                    splitMethod = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.SPLIT_METHOD)),
                    paidByUser = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.PAID_BY_USER)),
                    tripId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.TRIP_ID)),
                    currencyCode = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.CURRENCY_CODE)),
                    createdAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.CREATED_AT)),
                    updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.UPDATED_AT)),
                    isDeleted = cursor.getInt(
                        cursor.getColumnIndexOrThrow(ExpenseTable.IS_DELETED)
                    ) == 1
                )
            )
        }

        cursor.close()
        return expense
    }

    fun getExpenseByPayer(paidByUser: String): List<Expense> {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${ExpenseTable.TABLE_NAME} WHERE ${ExpenseTable.PAID_BY_USER} = ?",
            arrayOf(paidByUser)
        )

        val expense = mutableListOf<Expense>()

        if (cursor.moveToFirst()) {
            expense.add(
                Expense(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.TITLE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.DESCRIPTION)),
                    amount = cursor.getDouble(cursor.getColumnIndexOrThrow(ExpenseTable.AMOUNT)),
                    splitMethod = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.SPLIT_METHOD)),
                    paidByUser = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.PAID_BY_USER)),
                    tripId = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.TRIP_ID)),
                    currencyCode = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.CURRENCY_CODE)),
                    createdAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.CREATED_AT)),
                    updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseTable.UPDATED_AT)),
                    isDeleted = cursor.getInt(
                        cursor.getColumnIndexOrThrow(ExpenseTable.IS_DELETED)
                    ) == 1
                )
            )
        }

        cursor.close()
        return expense
    }
}