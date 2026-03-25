package com.example.splitbuddy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.model.ExpenseDetails
import com.example.splitbuddy.data.local.query.ExpenseDetailsQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ExpenseDetailsQueryInstrumentedTest {

    private lateinit var context: Context
    private lateinit var database: Database
    private lateinit var query: ExpenseDetailsQuery

    private val details = ExpenseDetails(
        id = "ED1",
        splitTypeId = "S1",
        expenseId = "E1",
        createdAt = "2026-03-25",
        updatedAt = "2026-03-25",
        isDeleted = false
    )

    @Before
    fun setUp() {

        context = ApplicationProvider.getApplicationContext()

        context.deleteDatabase("SplitBuddy.db")

        database = Database(context)
        query = ExpenseDetailsQuery(database)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertExpenseDetails_whenValidDataProvided_shouldInsertSuccessfully() {

        val result = query.insertExpenseDetails(details)

        assertTrue(result)
    }

    @Test
    @Throws(Exception::class)

    fun getExpenseDetails_whenValidExpenseIdProvided_shouldReturnCorrectExpenseDetails() {

        query.insertExpenseDetails(details)

        val saved = query.getExpenseDetails("ED1")

        assertNotNull(saved)
        assertEquals(details, saved)
    }

    @Test
    @Throws(Exception::class)

    fun updateExpenseDetails_whenDataUpdated_shouldGetChangeValues() {

        query.insertExpenseDetails(details)

        val updated = details.copy(
            splitTypeId = "S2",
            updatedAt = "2026-03-26"
        )

        val result = query.updateExpenseDetails(updated)
        val saved = query.getExpenseDetails("ED1")

        assertTrue(result)
        assertEquals("S2", saved?.splitTypeId)
    }
}