package com.example.splitbuddy.data.remote.group

class GroupApiInterfaceImpl(): GroupApiInterface {

    private val api = GroupRetrofitInstance.api

    override suspend fun groupCreationApi(request: CreateGroupRequest): GroupResponse {
        val response = api.createGroup(request)
        return response
    }

    override suspend fun getGroupsByUserId(userId: String): List<GroupResponse> {
        val response = api.getGroupsByUserId(userId)
        return response
    }

    override suspend fun updateGroup(groupId: String, request: UpdateGroupRequest): GroupResponse {
        val response = api.updateGroup(groupId,request)
        return response
    }

    override suspend fun deleteGroup(groupId: String): GroupResponse {
        val response = api.deleteGroup(groupId)
        return response
    }

    override suspend fun addMultipleMemberToGroup(groupId: String, request: AddMembersRequest): List<Member> {
        val response = api.addMembersToGroup(groupId, request)
        return response
    }

    override suspend fun updateMembersToGroup(groupId: String, userId: String): Member {
        val response = api.updateMember(groupId,userId)
        return response
    }

}