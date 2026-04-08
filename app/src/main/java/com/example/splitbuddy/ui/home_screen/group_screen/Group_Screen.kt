package com.example.splitbuddy.ui.home_screen.group_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.splitbuddy.ui.home_screen.Expense
import com.example.splitbuddy.ui.home_screen.expense_screen.ExpenseListCard
import com.example.splitbuddy.ui.theme.gradient2

@Composable
fun GroupScreen(expense: List<Expense>,onNext: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(gradient2)
                .clickable {
                    onNext()
                },
            contentAlignment = Alignment.Center
        ) { }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(expense) { expense ->
                ExpenseListCard(expense.title,expense.by,expense.amount,expense.type)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

    }
}