package com.example.splitbuddy.data.remote.group

import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface GroupApiService {

    // Create group
    @POST("groups")
    suspend fun createGroup(
        @Body request: CreateGroupRequest
    ): GroupResponse

    // Get all groups by userId
    @GET("groups/user/{userId}")
    suspend fun getGroupsByUserId(
        @Path("userId") userId: String
    ): List<GroupResponse>

    // Update group
    @PATCH("groups/{groupId}")
    suspend fun updateGroup(
        @Path("groupId") groupId: String,
        @Body request: UpdateGroupRequest
    ): GroupResponse

    // Delete group
    @DELETE("groups/{groupId}")
    suspend fun deleteGroup(
        @Path("groupId") groupId: String
    ): GroupResponse

    // Add multiple member to group
    @POST("groups/{groupId}/members/bulk")
    suspend fun addMembersToGroup(
        @Path("groupId") groupId: String,
        @Body request: AddMembersRequest
    ): List<Member>

    //Update member to group
    @DELETE("groups/{groupId}/members/{memberId}")
    suspend fun updateMember(
        @Path("groupId") groupId: String,
        @Path("memberId") memberId: String
    ): Member

}

object GroupRetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    val api: GroupApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupApiService::class.java)
    }
}
