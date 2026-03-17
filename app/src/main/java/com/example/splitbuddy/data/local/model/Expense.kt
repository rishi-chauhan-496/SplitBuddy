package com.example.splitbuddy.data.local.model

data class Expense(
    val id: String,
    val title: String,
    val amount: Double,
    val paidByUser: String,
    val tripId: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean
)

data class InsertExpense(
    val id: String,
    val title: String,
    val amount: Double,
    val paidByUser: String,
    val tripId: String,
    val createdAt: String,
    val updatedAt: String
)
