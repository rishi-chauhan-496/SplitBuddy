package com.example.splitbuddy.ui.home_screen

sealed class Screen(val route: String){
    object ExpenseScreen1: Screen("Expense_Screen_1")
    object ExpenseScreen2: Screen("Expense_Screen_2")
    object ExpenseScreen3: Screen("Expense_Screen_3")
}