package com.example.splitbuddy.data.local.model

data class ExpenseUserLedger(
    val id: String,
    val expenseDetailId: String,
    val userId: String,
    val sharedAmount: Double,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean
)

data class InsertExpenseUserLedger(
    val id: String,
    val expenseDetailId: String,
    val userId: String,
    val sharedAmount: Double,
    val createdAt: String,
    val updatedAt: String
)