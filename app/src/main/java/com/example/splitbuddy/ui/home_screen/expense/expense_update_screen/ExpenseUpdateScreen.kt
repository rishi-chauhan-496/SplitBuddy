package com.example.splitbuddy.ui.home_screen.expense.expense_update_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.theme.gradient
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseUpdateScreen(
    expenseId: String,
    groupId: String,
    onBack: () -> Unit
) {
    val viewModel: ExpenseUpdateViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(expenseId) {
        viewModel.load(expenseId)
    }

    val updatedMsg = stringResource(R.string.expense_update_success)
    val deletedMsg = stringResource(R.string.expense_detail_delete_success)

    LaunchedEffect(state.value.isUpdated, state.value.isDeleted) {
        if (state.value.isUpdated) {
            Toast.makeText(context, updatedMsg, Toast.LENGTH_SHORT).show()
            onBack()
        }
        if (state.value.isDeleted) {
            Toast.makeText(context, deletedMsg, Toast.LENGTH_SHORT).show()
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.expense_update_title),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(stringResource(R.string.expense_update_field_title), color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
        TextField(
            value = state.value.title,
            onValueChange = viewModel::onTitleChange,
            isError = state.value.titleError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF3D5AFE),
                unfocusedIndicatorColor = Color(0xFF3D5AFE),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        state.value.titleError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(modifier = Modifier.height(12.dp))

        // Amount
        Text(stringResource(R.string.expense_update_field_amount), color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
        TextField(
            value = state.value.amount,
            onValueChange = viewModel::onAmountChange,
            isError = state.value.amountError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF3D5AFE),
                unfocusedIndicatorColor = Color(0xFF3D5AFE),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        state.value.amountError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(modifier = Modifier.height(12.dp))

        // Description
        Text(stringResource(R.string.expense_update_field_description), color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
        TextField(
            value = state.value.description,
            onValueChange = viewModel::onDescriptionChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF3D5AFE),
                unfocusedIndicatorColor = Color(0xFF3D5AFE),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Paid by — read only (changing payer requires re-split)
        Text(stringResource(R.string.expense_update_field_paid_by), color = MaterialTheme.colorScheme.secondary, fontSize = 12.sp)
        TextField(
            value = state.value.paidByUser,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray
            )
        )
        Text(
            text = stringResource(R.string.expense_update_paid_by_hint),
            fontSize = 11.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.weight(1f))

        // Save button
        Button(
            onClick = { viewModel.update(expenseId, groupId) },
            enabled = !state.value.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .background(gradient)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (state.value.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(stringResource(R.string.save_), color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Delete button
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
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(R.string.delete), color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.expense_update_delete_title)) },
            text = { Text(stringResource(R.string.expense_update_delete_message)) },
            confirmButton = {
                Button(
                    onClick = { showDialog = false; viewModel.delete(expenseId) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) { Text(stringResource(R.string.expense_detail_delete_confirm), color = Color.White) }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }
}