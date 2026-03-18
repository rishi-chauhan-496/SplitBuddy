package com.example.splitbuddy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.model.InsertTripManager
import com.example.splitbuddy.data.local.model.TripManager
import com.example.splitbuddy.data.local.query.TripManagerQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TripManagerDatabaseTest {

    private lateinit var db: Database
    private lateinit var tripManagerHelper: TripManagerQuery

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Database(context)
        tripManagerHelper = TripManagerQuery(db)

        context.deleteDatabase("SplitBuddy.db")
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertTripManager_success() {

        val manager = InsertTripManager(
            id = "TM1",
            tripId = "T1",
            userId = "U1",
            createdAt = "2026-03-17",
            updatedAt = "2026-03-17"
        )

        val result = tripManagerHelper.insertTripManager(manager)

        assertTrue(result)
    }

    @Test
    fun getTripManager_returnsCorrectData() {

        val manager = InsertTripManager(
            id = "TM2",
            tripId = "T2",
            userId = "U2",
            createdAt = "2026-03-17",
            updatedAt = "2026-03-17"
        )

        val expectedManager = TripManager(
            id = "TM2",
            tripId = "T2",
            userId = "U2",
            createdAt = "2026-03-17",
            updatedAt = "2026-03-17",
            isDeleted = false
        )

        tripManagerHelper.insertTripManager(manager)

        val savedManager = tripManagerHelper.getTripManager("TM2")

        assertNotNull(savedManager)
        assertEquals(expectedManager, savedManager)
    }


    @Test
    fun updateTripManager_updatesData() {

        tripManagerHelper.insertTripManager(
            InsertTripManager(
                id = "TM3",
                tripId = "T3",
                userId = "U3",
                createdAt = "2026",
                updatedAt = "2026"
            )
        )

        val updatedManager = TripManager(
            id = "TM3",
            tripId = "T3",
            userId = "U3",
            createdAt = "2026",
            updatedAt = "2026-03-18",
            isDeleted = false
        )

        val result = tripManagerHelper.updateTripManager(updatedManager)

        val manager = tripManagerHelper.getTripManager("TM3")

        assertTrue(result)
        assertEquals("2026-03-18", manager?.updatedAt)
    }
}