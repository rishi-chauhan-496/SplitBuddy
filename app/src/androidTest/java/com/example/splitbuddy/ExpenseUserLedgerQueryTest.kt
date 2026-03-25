package com.example.splitbuddy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.model.ExpenseUserLedger
import com.example.splitbuddy.data.local.query.ExpenseUserLedgerQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExpenseUserLedgerQueryInstrumentedTest {

    private lateinit var context: Context
    private lateinit var database: Database
    private lateinit var query: ExpenseUserLedgerQuery

    private val ledger = ExpenseUserLedger(
        id = "L1",
        expenseDetailId = "ED1",
        userId = "U1",
        sharedAmount = 400.0,
        createdAt = "2026-03-25",
        updatedAt = "2026-03-25",
        isDeleted = false
    )

    @Before
    fun setUp() {

        context = ApplicationProvider.getApplicationContext()

        context.deleteDatabase("SplitBuddy.db")

        database = Database(context)
        query = ExpenseUserLedgerQuery(database)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertLedger_whenValidDataProvided_shouldInsertSuccessfully() {

        val result = query.insertLedger(ledger)

        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)
    fun getLedger_whenValidLedgerIdProvided_shouldReturnCorrectLedger() {

        query.insertLedger(ledger)

        val saved = query.getLedger("L1")

        assertNotNull(saved)
        assertEquals(ledger, saved)
    }

    @Test
    @Throws(Exception::class)
    fun updateLedger_whenSharedAmountUpdated_shouldGetChangesValues() {

        query.insertLedger(ledger)

        val updated = ledger.copy(
            sharedAmount = 500.0,
            updatedAt = "2026-03-26"
        )

        val result = query.updateLedger(updated)
        val saved = query.getLedger("L1")

        assertTrue(result)
        assertEquals(500.0, saved?.sharedAmount)
    }
}