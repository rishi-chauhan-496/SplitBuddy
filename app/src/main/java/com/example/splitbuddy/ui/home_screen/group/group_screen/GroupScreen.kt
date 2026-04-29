package com.example.splitbuddy.ui.home_screen.group.group_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.ui.home_screen.Screen
import com.example.splitbuddy.ui.home_screen.expense.ExpenseListCard
import com.example.splitbuddy.ui.theme.gradient2
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupScreen(
    groupId: String,
    onAddExpense: () -> Unit,
    onAddMember: () -> Unit,
    onSettlement: () -> Unit,
    onExpenseClick: (expenseId: String) -> Unit
) {
    val viewModel: GroupDetailViewModel = koinViewModel()
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(groupId) {
        viewModel.load(groupId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        // ── Top info card ─────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(gradient2),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                // Group info section
                Column {
                    Text(
                        text = state.value.groupName.ifBlank { "Group" },
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text(
                            text = "${state.value.memberCount} members",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                        Text(
                            text = "${state.value.expenses.size} expenses",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Total: ₹${"%.2f".format(state.value.totalAmount)}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // ── 3 Action buttons ──────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GroupActionButton(
                        label = "Add Member",
                        onClick = onAddMember,
                        modifier = Modifier.weight(1f)
                    )
                    GroupActionButton(
                        label = "Settlement",
                        onClick = onSettlement,
                        modifier = Modifier.weight(1f)
                    )
                    GroupActionButton(
                        label = "Add Expense",
                        onClick = onAddExpense,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Expense list ──────────────────────────────────────────────────────
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.value.expenses) { expense ->
                ExpenseListCard(
                    title = expense.title,
                    by = expense.paidByUser,
                    amount = expense.amount,
                    type = expense.splitMethod,
                    onClick = { onExpenseClick(expense.id) }
                )
            }
        }
    }
}

// ── Reusable action button ────────────────────────────────────────────────────

@Composable
private fun GroupActionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(64.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White.copy(alpha = 0.15f),
            contentColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontSize = 10.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}