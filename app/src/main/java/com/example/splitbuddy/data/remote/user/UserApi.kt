package com.example.splitbuddy.data.remote.user

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    @POST("users")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): UserResponse

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") userId: String
    ): UserResponse

    @GET("users")
    suspend fun getAllUser(): List<UserResponse>

    @PATCH("users/{id}")
    suspend fun updateUser(
        @Path("id") userId: String,
        @Body request: UpdateUserRequest
    ): UserResponse
}

object UserRetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    val api: UserApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }
}
