package com.example.splitbuddy.data.local.model

data class TripManager(
    val id: String,
    val tripId: String,
    val userId: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean
)

data class InsertTripManager(
    val id: String,
    val tripId: String,
    val userId: String,
    val createdAt: String,
    val updatedAt: String
)
