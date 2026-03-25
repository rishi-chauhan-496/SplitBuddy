package com.example.splitbuddy.data.local.query

import android.content.ContentValues
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.database.Database.UserTable
import com.example.splitbuddy.data.local.model.User

class UserQuery(private val dbHelper: Database) {

    // INSERT

    fun insertUser(user: User): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(UserTable.ID, user.id)
        cv.put(UserTable.SOCIAL_ID, user.socialId)
        cv.put(UserTable.NAME, user.name)
        cv.put(UserTable.CONTACT, user.contact)
        cv.put(UserTable.CREATED_AT, user.createdAt)
        cv.put(UserTable.UPDATED_AT, user.updatedAt)

        return db.insert(UserTable.TABLE_NAME, null, cv) > 0
    }


    // UPDATE

    fun updateUser(user: User): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(UserTable.NAME, user.name)
        cv.put(UserTable.CONTACT, user.contact)
        cv.put(UserTable.UPDATED_AT, user.updatedAt)

        return db.update(UserTable.TABLE_NAME, cv, "${UserTable.ID} = ?", arrayOf(user.id)) > 0
    }


    // SELECT SINGLE USER

    fun getUser(userId: String): User? {

        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.ID} = ?",
            arrayOf(userId)
        )

        var user: User? = null

        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.ID)),
                socialId = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.SOCIAL_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.NAME)),
                contact = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.CONTACT)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.UPDATED_AT)),
                isDeleted = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserTable.IS_DELETED)
                ) ==1
            )
        }

        cursor.close()
        return user
    }
}
