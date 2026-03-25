package com.example.splitbuddy.data.local.query

import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.SplitTypeTable
import com.example.splitbuddy.data.local.model.SplitType

class SplitTypeQuery(private val dbHelper: Database) {

    fun getSplitType(title: String): SplitType? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${SplitTypeTable.TABLE_NAME} WHERE ${SplitTypeTable.TITLE} = ?",
            arrayOf(title)
        )

        var splitType: SplitType? = null

        if (cursor.moveToFirst()) {
            splitType = SplitType(
                id = cursor.getString(cursor.getColumnIndexOrThrow(SplitTypeTable.ID)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(SplitTypeTable.TITLE)),
                value = cursor.getDouble(cursor.getColumnIndexOrThrow(SplitTypeTable.VALUE)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(SplitTypeTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(SplitTypeTable.UPDATED_AT)),
                isDeleted = cursor.getInt(
                    cursor.getColumnIndexOrThrow(SplitTypeTable.IS_DELETED)
                ) == 1
            )
        }

        cursor.close()
        return splitType
    }
}