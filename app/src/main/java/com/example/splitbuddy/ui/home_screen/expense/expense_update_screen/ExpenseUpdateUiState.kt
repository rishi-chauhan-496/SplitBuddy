package com.example.splitbuddy.ui.home_screen.expense.expense_update_screen

data class ExpenseUpdateUiState(
    val isLoading: Boolean = false,
    val title: String = "",
    val amount: String = "",
    val description: String = "",
    val paidByUser: String = "",
    val titleError: String? = null,
    val amountError: String? = null,
    val isUpdated: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null
)
