package com.example.splitbuddy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.model.InsertTrip
import com.example.splitbuddy.data.local.model.Trip
import com.example.splitbuddy.data.local.query.TripsQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TripDatabaseTest {

    private lateinit var db: Database
    private lateinit var tripHelper: TripsQuery

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Database(context)
        tripHelper = TripsQuery(db)


        context.deleteDatabase("SplitBuddy.db")
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertTrip_success() {

        val trip = InsertTrip(
            id = "T1",
            tripTitle = "Goa Trip",
            createdAt = "2026-03-17",
            updatedAt = "2026-03-17"
        )

        val result = tripHelper.insertTrips(trip)

        assertTrue(result)
    }

    @Test
    fun getTrip_returnsCorrectData() {

        val trip = InsertTrip(
            id = "T2",
            tripTitle = "Manali Trip",
            createdAt = "2026-03-17",
            updatedAt = "2026-03-17"
        )

        val expectedTrip = Trip(
            id = "T2",
            tripTitle = "Manali Trip",
            createdAt = "2026-03-17",
            updatedAt = "2026-03-17",
            isDeleted = false
        )

        tripHelper.insertTrips(trip)

        val savedTrip = tripHelper.getTrips("T2")

        assertNotNull(savedTrip)
        assertEquals(expectedTrip, savedTrip)
    }

    @Test
    fun updateTrip_updatesData() {

        tripHelper.insertTrips(
            InsertTrip(
                id = "T3",
                tripTitle = "Old Trip",
                createdAt = "2026",
                updatedAt = "2026"
            )
        )

        val updatedTrip = Trip(
            id = "T3",
            tripTitle = "New Trip Updated",
            createdAt = "2026",
            updatedAt = "2026-03-18",
            isDeleted = false
        )

        val result = tripHelper.updateTrips(updatedTrip)

        val trip = tripHelper.getTrips("T3")

        assertTrue(result)
        assertEquals("New Trip Updated", trip?.tripTitle)
    }
}