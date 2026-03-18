package com.example.splitbuddy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.query.SplitTypeQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplitTypeQueryTest {

    private lateinit var db: Database
    private lateinit var splitTypeQuery: SplitTypeQuery

    @Before
    fun setup() {

        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Database(context)
        splitTypeQuery = SplitTypeQuery(db)

        context.deleteDatabase("SplitBuddy.db")
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getSplitType_returnsEqualType() {

        val splitType = splitTypeQuery.getSplitType("S1")

        assertNotNull(splitType)
        assertEquals("Equal", splitType?.title)
    }

    @Test
    fun getSplitType_invalidId_returnsNull() {

        val splitType = splitTypeQuery.getSplitType("S4")

        assertEquals(null, splitType)
    }
}