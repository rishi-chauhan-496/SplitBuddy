package com.example.splitbuddy.ui.home_screen.expense.expense_update_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splitbuddy.data.local.query.ExpenseQuery
import com.example.splitbuddy.data.local.query.ExpenseShareQuery
import com.example.splitbuddy.data.remote.expense.ExpenseRequest
import com.example.splitbuddy.data.remote.expense.ShareRequest
import com.example.splitbuddy.domain.usecase.expense.DeleteExpenseUseCase
import com.example.splitbuddy.domain.usecase.expense.UpdateExpenseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpenseUpdateViewModel(
    private val expenseQuery: ExpenseQuery,
    private val expenseShareQuery: ExpenseShareQuery,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseUpdateUiState())
    val state: StateFlow<ExpenseUpdateUiState> = _state

    fun load(expenseId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val expense = expenseQuery.getExpenseById(expenseId)
                if (expense != null) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            title = expense.title,
                            amount = expense.amount.toString(),
                            description = expense.description ?: "",
                            paidByUser = expense.paidByUser
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun onTitleChange(value: String) {
        _state.update { it.copy(title = value, titleError = null) }
    }

    fun onAmountChange(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d*\\.?\\d*$"))) {
            _state.update { it.copy(amount = value, amountError = null) }
        }
    }

    fun onDescriptionChange(value: String) {
        _state.update { it.copy(description = value) }
    }

    fun update(expenseId: String, groupId: String) {
        val s = _state.value

        val titleError = if (s.title.isBlank()) "Title required" else null
        val amountError = when {
            s.amount.isBlank() -> "Amount required"
            s.amount.toDoubleOrNull() == null -> "Enter a valid number"
            s.amount.toDouble() <= 0 -> "Must be greater than 0"
            else -> null
        }

        if (titleError != null || amountError != null) {
            _state.update { it.copy(titleError = titleError, amountError = amountError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                // Keep existing shares unchanged — only update basic info
                val existingShares = expenseShareQuery.getSharesByExpenseId(expenseId)

                val shareRequests = existingShares.map { share ->
                    ShareRequest(
                        userId = share.userId,
                        shareAmount = "%.2f".format(share.sharedAmount),
                        isIncluded = share.isIncluded,
                        sharePercent = if (share.sharedPercent > 0)
                            "%.2f".format(share.sharedPercent) else null
                    )
                }

                val request = ExpenseRequest(
                    title = s.title,
                    description = s.description.ifBlank { null },
                    amount = "%.2f".format(s.amount.toDoubleOrNull() ?: 0.0),
                    splitMethod = expenseQuery.getExpenseById(expenseId)?.splitMethod ?: "EQUAL",
                    paidByUser = s.paidByUser,
                    groupId = groupId,
                    shares = shareRequests
                )

                updateExpenseUseCase(expenseId, request)
                _state.update { it.copy(isLoading = false, isUpdated = true) }

            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun delete(expenseId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                deleteExpenseUseCase(expenseId)
                _state.update { it.copy(isLoading = false, isDeleted = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}