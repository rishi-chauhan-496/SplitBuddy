package com.example.splitbuddy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.model.SplitType
import com.example.splitbuddy.data.local.query.SplitTypeQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SplitTypeQueryInstrumentedTest {

    private lateinit var context: Context
    private lateinit var database: Database
    private lateinit var query: SplitTypeQuery

    private val splitType = SplitType(
        id = "S1",
        title = "Equal",
        value = 1.0,
        createdAt = "2026-03-25",
        updatedAt = "2026-03-25",
        isDeleted = false
    )

    @Before
    fun setUp() {

        context = ApplicationProvider.getApplicationContext()

        context.deleteDatabase("SplitBuddy.db")

        database = Database(context)
        query = SplitTypeQuery(database)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun getSplitType_whenTitleExists_shouldReturnCorrectValue() {

        val result = query.getSplitType("Equal")

        assertNotNull(result)
        assertEquals(splitType, result)
    }

    @Test
    @Throws(Exception::class)
    fun getSplitType_whenTitleDoesNotExist_shouldReturnNull() {

        val result = query.getSplitType("Percentage")

        assertNull(result)
    }
}