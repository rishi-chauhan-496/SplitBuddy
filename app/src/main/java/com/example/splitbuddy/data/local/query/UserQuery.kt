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
        cv.put(UserTable.USER_NAME, user.userName)
        cv.put(UserTable.FIRST_NAME, user.firstName)
        cv.put(UserTable.LAST_NAME, user.lastName)
        cv.put(UserTable.CONTACT, user.contact)
        cv.put(UserTable.EMAIL, user.email)
        cv.put(UserTable.SOCIAL_ID, user.socialMediaId)
        cv.put(UserTable.CREATED_AT, user.createdAt)
        cv.put(UserTable.UPDATED_AT, user.updatedAt)

        return db.insert(UserTable.TABLE_NAME, null, cv) > 0
    }


    // UPDATE

    fun updateUser(user: User): Boolean {

        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(UserTable.USER_NAME, user.userName)
        cv.put(UserTable.FIRST_NAME, user.firstName)
        cv.put(UserTable.LAST_NAME, user.lastName)
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
                userName = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.USER_NAME)),
                isActive = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserTable.IS_ACTIVE)
                ) == 1,
                firstName = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.FIRST_NAME)),
                lastName = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.LAST_NAME)),
                contact = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.CONTACT)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.EMAIL)),
                socialMediaId = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.SOCIAL_ID)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.UPDATED_AT)),
                isDeleted = cursor.getInt(
                    cursor.getColumnIndexOrThrow(UserTable.IS_DELETED)
                ) == 1
            )
        }

        cursor.close()
        return user
    }
}
