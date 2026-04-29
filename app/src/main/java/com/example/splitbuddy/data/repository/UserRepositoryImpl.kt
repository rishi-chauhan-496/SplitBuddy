package com.example.splitbuddy.data.repository

import com.example.splitbuddy.data.local.model.User
import com.example.splitbuddy.data.local.query.UserQuery
import com.example.splitbuddy.data.remote.user.CreateUserRequest
import com.example.splitbuddy.data.remote.user.UpdateUserRequest
import com.example.splitbuddy.data.remote.user.UserApiInterface
import com.example.splitbuddy.domain.repository.UserRepository
import kotlin.String

class UserRepositoryImpl(
    val userApiInterface: UserApiInterface,
    val userQuery: UserQuery
) : UserRepository {

    override suspend fun createUser(request: CreateUserRequest): Boolean {
        val data = userApiInterface.createUser(request)

        return userQuery.insertUser(data)
    }

    override suspend fun getAllUser(): List<User> {
        val users = userApiInterface.getAllUser()

        users.forEach { user ->
            userQuery.insertUser(user)
        }

        return userQuery.getALLUser()
    }

    override suspend fun getUserById(userId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(
        userId: String,
        request: UpdateUserRequest
    ): Boolean {
        TODO("Not yet implemented")
    }

}