package com.example.splitbuddy.ui.home_screen.expense.expense_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.theme.Primary
import com.example.splitbuddy.ui.theme.gradient2
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExpenseDetailScreen(
    expenseId: String,
    onBack: () -> Unit
) {
    val viewModel: ExpenseDetailViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(expenseId) {
        viewModel.load(expenseId)
    }

    val deletedMsg = stringResource(R.string.expense_detail_delete_success)

    LaunchedEffect(state.value.isDeleted) {
        if (state.value.isDeleted) {
            Toast.makeText(context, deletedMsg, Toast.LENGTH_SHORT).show()
            onBack()
        }
    }

    val expense = state.value.expense

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            state.value.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Primary)
                }
            }

            expense == null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.expense_detail_not_found), color = Color.Gray)
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    // ── Summary card ──────────────────────────────────────────
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(gradient2)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = expense.title,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "₹${"%.2f".format(expense.amount)}",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Info row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                InfoChip(label = stringResource(R.string.expense_detail_paid_by), value = expense.paidByUser)
                                InfoChip(label = stringResource(R.string.expense_detail_split), value = expense.splitMethod)
                            }

                            if (!expense.description.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = expense.description,
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 13.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ── Share list ────────────────────────────────────────────
                    Text(
                        text = stringResource(R.string.expense_detail_split_breakdown),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val includedShares = state.value.shares.filter { it.isIncluded }

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(includedShares) { share ->
                            ShareDetailCard(
                                userId = share.userId,
                                amount = share.sharedAmount,
                                percent = if (expense.splitMethod == "PERCENTAGE") share.sharedPercent else null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // ── Delete button ─────────────────────────────────────────
                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.Red)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.delete),
                                    contentDescription = "Delete",
                                    modifier = Modifier.size(18.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(stringResource(R.string.expense_detail_delete_button),
                                    color = Color.White,
                                    fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }
        }
    }

    // ── Delete confirmation dialog ────────────────────────────────────────────
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.expense_detail_delete_title)) },
            text = { Text(stringResource(R.string.expense_detail_delete_message)) },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        viewModel.delete(expenseId)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(stringResource(R.string.expense_detail_delete_confirm), color = Color.White)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}

// ── Small info chip inside the summary card ───────────────────────────────────
@Composable
private fun InfoChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
        Text(text = value, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}

// ── Per-member share card ─────────────────────────────────────────────────────
@Composable
private fun ShareDetailCard(
    userId: String,
    amount: Double,
    percent: Double?
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userId.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = userId,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "₹${"%.2f".format(amount)}",
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    fontSize = 15.sp
                )
                if (percent != null && percent > 0) {
                    Text(
                        text = "${"%.2f".format(percent)}%",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}