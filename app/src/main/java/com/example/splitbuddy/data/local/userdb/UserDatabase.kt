package com.example.splitbuddy.data.local.userdb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabase(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "SplitBuddy.db"
        private const val DATABASE_VERSION = 1
    }

    object UserTable {
        const val TABLE_NAME = "User"
        const val ID = "id"
        const val SOCIAL_ID = "social_id"
        const val NAME = "name"
        const val CONTACT = "contact"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val IS_DELETED = "is_deleted"
    }

    // ---------------- CREATE TABLE ----------------

    override fun onCreate(db: SQLiteDatabase) {

        val createUserTable = """
            CREATE TABLE ${UserTable.TABLE_NAME} (
                ${UserTable.ID} VARCHAR PRIMARY KEY,
                ${UserTable.SOCIAL_ID} VARCHAR UNIQUE,
                ${UserTable.NAME} TEXT,
                ${UserTable.CONTACT} TEXT,
                ${UserTable.CREATED_AT} DATETIME,
                ${UserTable.UPDATED_AT} DATETIME,
                ${UserTable.IS_DELETED} BOOLEAN DEFAULT 0
            );
        """.trimIndent()

        db.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    // INSERT

    fun insertUser(user: InsertUser): Boolean {

        val db = writableDatabase
        val cv = ContentValues()

        cv.put(UserTable.ID, user.id)
        cv.put(UserTable.SOCIAL_ID,user.socialId)
        cv.put(UserTable.NAME, user.name)
        cv.put(UserTable.CONTACT, user.contact)
        cv.put(UserTable.CREATED_AT, user.createdAt)
        cv.put(UserTable.UPDATED_AT, user.updatedAt)

        return db.insert(UserTable.TABLE_NAME, null, cv) > 0
    }

    // UPDATE

    fun updateUser(user: User): Boolean {

        val db = writableDatabase
        val cv = ContentValues()

        cv.put(UserTable.NAME, user.name)
        cv.put(UserTable.CONTACT, user.contact)
        cv.put(UserTable.UPDATED_AT, user.updatedAt)

        return db.update(UserTable.TABLE_NAME, cv, "${UserTable.ID} = ?", arrayOf(user.id)) > 0
    }

    // SELECT SINGLE USER

    fun getUser(userId: String): User? {

        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.ID} = ?", arrayOf(userId) )

        var user: User? = null

        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.ID)),
                socialId = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.SOCIAL_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.NAME)),
                contact = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.CONTACT)),
                createdAt = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.CREATED_AT)),
                updatedAt = cursor.getString(cursor.getColumnIndexOrThrow(UserTable.UPDATED_AT)),
                isDeleted = cursor.getInt(cursor.getColumnIndexOrThrow(UserTable.IS_DELETED))
            )
        }

        cursor.close()
        return user
    }
}
