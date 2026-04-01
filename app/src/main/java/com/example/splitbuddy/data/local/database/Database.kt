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
        const val USER_NAME = "user_name"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val CONTACT = "contact"
        const val EMAIL = "email"
        const val SOCIAL_ID = "social_media_id"
        const val IS_ACTIVE = "is_Active"
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
        const val ROLE = "role"
        const val IS_ACTIVE = "is_active"
        const val JOINED_AT = "joined_at"
        const val LEFT_AT = "left_at"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val IS_DELETED = "is_deleted"
    }

    object ExpenseTable {
        const val TABLE_NAME = "Expense"
        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val AMOUNT = "amount"
        const val SPLIT_METHOD = "split_method"
        const val PAID_BY_USER = "paid_by_user"
        const val TRIP_ID = "trip_id"
        const val CURRENCY_CODE = "currency_code"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val IS_DELETED = "is_deleted"
    }

    object ExpenseDetailsTable {
        const val TABLE_NAME = "ExpenseDetails"
        const val ID = "id"
        const val SPLIT_TYPE_ID = "split_type_id"
        const val EXPENSE_ID = "expense_id"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val IS_DELETED = "is_deleted"
    }

    object ExpenseUserLedgerTable {
        const val TABLE_NAME = "ExpenseUserLedger"

        const val ID = "id"
        const val EXPENSE_DETAIL_ID = "expense_detail_id"
        const val USER_ID = "user_id"
        const val SHARED_AMOUNT = "shared_amount"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val IS_DELETED = "is_deleted"
    }

    object SettlementTable {

        const val TABLE_NAME = "Settlement"

        const val ID = "id"
        const val USER_ID = "user_id"
        const val TRIP_ID = "trip_id"
        const val USER_FINAL_CONTRIBUTION = "user_final_contribution"
        const val USER_FINAL_SHARED_AMOUNT = "user_final_shared_amount"
        const val SETTLEMENT_AMT = "settlement_amt"
        const val CREATED_AT = "created_at"
        const val UPDATED_AT = "updated_at"
        const val IS_DELETED = "is_deleted"
    }

    // CREATE TABLE

    override fun onCreate(db: SQLiteDatabase) {

        val createUserTable = """
        CREATE TABLE ${UserTable.TABLE_NAME} (
            ${UserTable.ID} TEXT PRIMARY KEY,
            ${UserTable.USER_NAME} TEXT UNIQUE,
            ${UserTable.FIRST_NAME} TEXT,
            ${UserTable.LAST_NAME} TEXT,
            ${UserTable.CONTACT} TEXT,
            ${UserTable.EMAIL} TEXT,
            ${UserTable.SOCIAL_ID} TEXT UNIQUE,
            ${UserTable.IS_ACTIVE} INTEGER DEFAULT 1,
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
            ${TripManagerTable.ROLE} TEXT,
            ${TripManagerTable.IS_ACTIVE} TEXT DEFAULT 1,
            ${TripManagerTable.JOINED_AT} TEXT,
            ${TripManagerTable.LEFT_AT} TEXT,
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

        val createExpenseTable = """
        CREATE TABLE ${ExpenseTable.TABLE_NAME} (
            ${ExpenseTable.ID} TEXT PRIMARY KEY,
            ${ExpenseTable.TITLE} TEXT,
            ${ExpenseTable.DESCRIPTION} TEXT,
            ${ExpenseTable.AMOUNT} REAL,
            ${ExpenseTable.SPLIT_METHOD} TEXT,
            ${ExpenseTable.PAID_BY_USER} TEXT,
            ${ExpenseTable.TRIP_ID} TEXT,
            ${ExpenseTable.CURRENCY_CODE} TEXT,
            ${ExpenseTable.CREATED_AT} TEXT,
            ${ExpenseTable.UPDATED_AT} TEXT,
            ${ExpenseTable.IS_DELETED} INTEGER DEFAULT 0,
            FOREIGN KEY(${ExpenseTable.TRIP_ID})
                REFERENCES ${TripTable.TABLE_NAME}(${TripTable.ID}),
            FOREIGN KEY(${ExpenseTable.PAID_BY_USER})
                REFERENCES ${UserTable.TABLE_NAME}(${UserTable.ID})
        );
    """.trimIndent()

        val createExpenseDetailsTable = """
        CREATE TABLE ${ExpenseDetailsTable.TABLE_NAME} (
            ${ExpenseDetailsTable.ID} TEXT PRIMARY KEY,
            ${ExpenseDetailsTable.SPLIT_TYPE_ID} TEXT,
            ${ExpenseDetailsTable.EXPENSE_ID} TEXT,
            ${ExpenseDetailsTable.CREATED_AT} TEXT,
            ${ExpenseDetailsTable.UPDATED_AT} TEXT,
            ${ExpenseDetailsTable.IS_DELETED} INTEGER DEFAULT 0,
            FOREIGN KEY(${ExpenseDetailsTable.EXPENSE_ID})
                REFERENCES ${ExpenseTable.TABLE_NAME}(${ExpenseTable.ID})
                ON DELETE CASCADE
        );
    """.trimIndent()

        val createExpenseUserLedgerTable = """
        CREATE TABLE ${ExpenseUserLedgerTable.TABLE_NAME} (
            ${ExpenseUserLedgerTable.ID} TEXT PRIMARY KEY,
            ${ExpenseUserLedgerTable.EXPENSE_DETAIL_ID} TEXT,
            ${ExpenseUserLedgerTable.USER_ID} TEXT,
            ${ExpenseUserLedgerTable.SHARED_AMOUNT} REAL,
            ${ExpenseUserLedgerTable.CREATED_AT} TEXT,
            ${ExpenseUserLedgerTable.UPDATED_AT} TEXT,
            ${ExpenseUserLedgerTable.IS_DELETED} INTEGER DEFAULT 0,
            UNIQUE(
                ${ExpenseUserLedgerTable.EXPENSE_DETAIL_ID},
                ${ExpenseUserLedgerTable.USER_ID}
            ),
            FOREIGN KEY(${ExpenseUserLedgerTable.EXPENSE_DETAIL_ID})
                REFERENCES ${ExpenseDetailsTable.TABLE_NAME}(${ExpenseDetailsTable.ID}),
            FOREIGN KEY(${ExpenseUserLedgerTable.USER_ID})
                REFERENCES ${UserTable.TABLE_NAME}(${UserTable.ID})
        );
    """.trimIndent()

        val createSettlementTable = """
        CREATE TABLE ${SettlementTable.TABLE_NAME} (
            ${SettlementTable.ID} TEXT PRIMARY KEY,
            ${SettlementTable.USER_ID} TEXT,
            ${SettlementTable.TRIP_ID} TEXT,
            ${SettlementTable.USER_FINAL_CONTRIBUTION} REAL,
            ${SettlementTable.USER_FINAL_SHARED_AMOUNT} REAL,
            ${SettlementTable.SETTLEMENT_AMT} REAL,
            ${SettlementTable.CREATED_AT} TEXT,
            ${SettlementTable.UPDATED_AT} TEXT,
            ${SettlementTable.IS_DELETED} INTEGER DEFAULT 0,
            FOREIGN KEY(${SettlementTable.USER_ID})
                REFERENCES ${UserTable.TABLE_NAME}(${UserTable.ID}),
            FOREIGN KEY(${SettlementTable.TRIP_ID})
                REFERENCES ${TripTable.TABLE_NAME}(${TripTable.ID})
        );
    """.trimIndent()


        // INSERTING TABLE

        db.execSQL(createUserTable)
        db.execSQL(createGroupTable)
        db.execSQL(createTripManagerTable)
        db.execSQL(createExpenseTable)
        db.execSQL(createExpenseDetailsTable)
        db.execSQL(createExpenseUserLedgerTable)
        db.execSQL(createSettlementTable)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}