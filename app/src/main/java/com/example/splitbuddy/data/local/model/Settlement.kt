package com.example.splitbuddy.data.local.model

data class Settlement(
    val id: String,
    val userId: String,
    val tripId: String,
    val userFinalContribution: Double,
    val userFinalSharedAmount: Double,
    val settlementAmt: Double,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean
)