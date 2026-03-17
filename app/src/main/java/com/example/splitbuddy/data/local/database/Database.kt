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

    object TripManagerTable {
        const val TABLE_NAME = "TripManager"
        const val ID = "id"
        const val TRIP_ID = "trip_id"
        const val USER_ID = "user_id"
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

        val createTripManagerTable = """
    CREATE TABLE ${TripManagerTable.TABLE_NAME} (
        ${TripManagerTable.ID} TEXT PRIMARY KEY,
        ${TripManagerTable.TRIP_ID} TEXT,
        ${TripManagerTable.USER_ID} TEXT,
        ${TripManagerTable.CREATED_AT} TEXT,
        ${TripManagerTable.UPDATED_AT} TEXT,
        ${TripManagerTable.IS_DELETED} INTEGER DEFAULT 0,

        UNIQUE(${TripManagerTable.TRIP_ID}, ${TripManagerTable.USER_ID}),

        FOREIGN KEY(${TripManagerTable.TRIP_ID})
            REFERENCES Trip(id),

        FOREIGN KEY(${TripManagerTable.USER_ID})
            REFERENCES User(id)
    );
    """.trimIndent()

        db.execSQL(createUserTable)
        db.execSQL(createGroupTable)
        db.execSQL(createTripManagerTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}