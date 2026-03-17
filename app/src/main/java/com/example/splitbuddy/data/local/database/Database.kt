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

    object TripTable {
        const val TABLE_NAME = "trips"
        const val ID = "id"
        const val TRIP_TITLE = "trip_title"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val IS_DELETED = "is_deleted"
    }

    // ---------------- CREATE TABLE ----------------

    override fun onCreate(db: SQLiteDatabase) {

        val createUserTable = """
        CREATE TABLE ${UserTable.TABLE_NAME} (
            ${UserTable.ID} TEXT PRIMARY KEY,
            ${UserTable.SOCIAL_ID} TEXT UNIQUE,
            ${UserTable.NAME} TEXT,
            ${UserTable.CONTACT} TEXT,
            ${UserTable.CREATED_AT} TEXT,
            ${UserTable.UPDATED_AT} TEXT,
            ${UserTable.IS_DELETED} INTEGER DEFAULT 0
        );
    """.trimIndent()

        val createGroupTable = """
        CREATE TABLE ${TripTable.TABLE_NAME} (
            ${TripTable.ID} TEXT PRIMARY KEY,
            ${TripTable.TRIP_TITLE} TEXT,
            ${TripTable.CREATED_AT} TEXT,
            ${TripTable.UPDATED_AT} TEXT,
            ${TripTable.IS_DELETED} INTEGER DEFAULT 0
        );
    """.trimIndent()


        db.execSQL(createUserTable)
        db.execSQL(createGroupTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}