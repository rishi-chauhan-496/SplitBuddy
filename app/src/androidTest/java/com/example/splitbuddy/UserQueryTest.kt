package com.example.splitbuddy

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.splitbuddy.data.local.database.Database
import com.example.splitbuddy.data.local.model.InsertUser
import com.example.splitbuddy.data.local.model.User
import com.example.splitbuddy.data.local.query.UserQuery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserQueryTest {

    private lateinit var db: Database
    private lateinit var userHelper: UserQuery

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Database(context)
        userHelper = UserQuery(db)


        context.deleteDatabase("SplitBuddy.db")
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertUser_success() {

        val user = InsertUser(
            id = "U1",
            socialId = "123456789",
            name = "Rishi",
            contact = "9999999999",
            createdAt = "2026-03-17",
            updatedAt = "2026-03-17"
        )

        val result = userHelper.insertUser(user)

        assertTrue(result)
    }

    @Test
    fun getUser_returnsCorrectData() {

        val user = InsertUser(
            "U2",
            "123456789",
            "Rahul",
            "8888888888",
            "2026-03-17",
            "2026-03-17"
        )

        val userInfo = User(
            "U2",
            "123456789",
            "Rahul",
            "8888888888",
            "2026-03-17",
            "2026-03-17",
            0
        )

        userHelper.insertUser(user)

        val savedUser = userHelper.getUser("U2")

        assertNotNull(savedUser)
        assertEquals(userInfo, savedUser)
    }

    @Test
    fun updateUser_updatesData() {

        userHelper.insertUser(
            InsertUser("U3","123456789","7777777777","2026","2026","2026")
        )

        val updated = User(
            id = "U3",
            socialId = "123456789",
            name = "Amit Updated",
            contact = "7777777777",
            createdAt = "2026",
            updatedAt = "2026-03-18",
            isDeleted = 0
        )

        val result = userHelper.updateUser(updated)

        val user = userHelper.getUser("U3")

        assertTrue(result)
        assertEquals("Amit Updated", user?.name)
    }
}