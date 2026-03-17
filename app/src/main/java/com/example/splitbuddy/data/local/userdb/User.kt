package com.example.splitbuddy.data.local.userdb

data class User(
    val id: String,
    val socialId: String,
    val name: String,
    val contact: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Int
)

data class InsertUser(
    val id: String,
    val socialId: String,
    val name: String,
    val contact: String,
    val createdAt: String,
    val updatedAt: String
)