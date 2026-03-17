package com.example.splitbuddy.data.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(
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

}