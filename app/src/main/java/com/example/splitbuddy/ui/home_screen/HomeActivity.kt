package com.example.splitbuddy.ui.home_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.home_screen.bottom_bar.BottomNavItem
import com.example.splitbuddy.ui.home_screen.bottom_bar.BottomNavigationBar
import com.example.splitbuddy.ui.home_screen.expense_screen.ExpenseScreen1
import com.example.splitbuddy.ui.home_screen.expense_screen.ExpenseScreen2
import com.example.splitbuddy.ui.home_screen.expense_screen.ExpenseScreen3
import com.example.splitbuddy.ui.home_screen.group_screen.GroupScreen
import com.example.splitbuddy.ui.theme.SplitBuddyTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplitBuddyTheme {
                MainScreen()
            }
        }
    }
}
data class Expense(
    val title: String,
    val by: String,
    val amount: Double,
    val type: String
)
val expenses = listOf(
    Expense("Dinner at Marina Walk", "Rohan", 1050.0, "Equal"),
    Expense("Movie Night", "Amit", 650.0, "Amount"),
    Expense("Cab Ride", "Neha", 320.5, "Percent"),
    Expense("Groceries", "Priya", 890.75, "Equal"),
    Expense("Coffee Meetup", "Karan", 240.0, "Amount")
)
val persons = listOf(
    Triple(R.drawable.ic_launcher_background, "Rohan", 1200.0),
    Triple(R.drawable.ic_launcher_background, "Amit", 850.5),
    Triple(R.drawable.ic_launcher_background, "Neha", 1500.0),
    Triple(R.drawable.ic_launcher_background, "Priya", 980.75),
    Triple(R.drawable.ic_launcher_background, "Karan", 1100.0),
    Triple(R.drawable.ic_launcher_background, "Rohan", 1200.0),
    Triple(R.drawable.ic_launcher_background, "Amit", 850.5),
    Triple(R.drawable.ic_launcher_background, "Neha", 1500.0),
    Triple(R.drawable.ic_launcher_background, "Priya", 980.75),
    Triple(R.drawable.ic_launcher_background, "Karan", 1100.0)
)

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    var expenseName by rememberSaveable { mutableStateOf("Dinner at Marina Walk") }
    var amount by rememberSaveable { mutableStateOf("1000") }
    var paidBy by rememberSaveable { mutableStateOf("Rishi chauhan") }
    var description by rememberSaveable { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Groups.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(BottomNavItem.Groups.route) {
                GroupScreen(
                    expenses,
                    onNext = {
                        navController.navigate(Screen.ExpenseScreen1.route)
                    }
                )
            }

            composable(Screen.ExpenseScreen1.route) {
                ExpenseScreen1(
                    expenseName,
                    onExpenseNameChange = {
                        expenseName = it
                    },
                    amount,
                    onAmountChange = {
                        amount = it
                    },
                    paidBy,
                    onPaidByChange = {
                        paidBy = it
                    },
                    description,
                    onDescriptionChange = {
                        description = it
                    },
                    onNext = {
                        navController.navigate(Screen.ExpenseScreen2.route)
                    }
                )
            }

            composable(Screen.ExpenseScreen2.route) {
                ExpenseScreen2(
                    persons,
                    amount,
                    onNext = {
                        navController.navigate(Screen.ExpenseScreen3.route)
                    }
                )
            }

            composable(Screen.ExpenseScreen3.route) {
                ExpenseScreen3(expenseName,amount,"5 people",persons)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SplitBuddyTheme {
        MainScreen()
    }
}