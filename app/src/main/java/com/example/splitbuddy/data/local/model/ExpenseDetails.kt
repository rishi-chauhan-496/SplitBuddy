package com.example.splitbuddy.data.local.model

data class ExpenseDetails(
    val id: String,
    val splitTypeId: String,
    val expenseId: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean
)

data class InsertExpenseDetails(
    val id: String,
    val splitTypeId: String,
    val expenseId: String,
    val createdAt: String,
    val updatedAt: String
)
