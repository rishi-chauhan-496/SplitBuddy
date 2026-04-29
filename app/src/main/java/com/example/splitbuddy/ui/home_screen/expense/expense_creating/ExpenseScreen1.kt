package com.example.splitbuddy.ui.home_screen.expense.expense_creating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.data.local.model.TripManager
import com.example.splitbuddy.ui.theme.gradient
import kotlin.collections.forEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen1(
    state: ExpenseUiState,
    onTitleChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onPaidByChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = stringResource(R.string.Expense_Screen_1_Title),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )
        Text(
            text = stringResource(R.string.Expense_Screen_1_Title_2),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.Expense_Screen_1_Text),
            color = Color.Gray,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ── Title ────────────────────────────────────────────────────────────
        Text(
            text = stringResource(R.string.Expense_Screen_1_TextFiled),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp
        )
        TextField(
            value = state.title,
            onValueChange = onTitleChange,
            isError = state.titleError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        state.titleError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Amount ───────────────────────────────────────────────────────────
        Text(
            text = stringResource(R.string.Expense_Screen_1_TextFiled_2),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp
        )
        TextField(
            value = state.amount,
            onValueChange = onAmountChange,
            isError = state.amountError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        state.amountError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Paid By (Dropdown from group members) ────────────────────────────
        Text(
            text = stringResource(R.string.Expense_Screen_1_TextFiled_3),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp
        )
        PaidByDropdown(
            members = state.members,
            selectedUserId = state.paidByUserId,
            onSelect = onPaidByChange,
            isError = state.paidByError != null
        )
        state.paidByError?.let { Text(it, color = Color.Red, fontSize = 12.sp) }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Description ──────────────────────────────────────────────────────
        Text(
            text = stringResource(R.string.Expense_Screen_1_TextFiled_4),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp
        )
        TextField(
            value = state.description,
            onValueChange = onDescriptionChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        // ── Next Button — enabled as soon as all required fields are filled ──
        Button(
            onClick = onNext,
            enabled = state.isFormFilled,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .background(if (state.isFormFilled) gradient else Brush.linearGradient(listOf(Color.Gray, Color.Gray)))
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.continue_),
                    color = Color.White
                )
            }
        }
    }
}

// ── Paid-By Dropdown ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PaidByDropdown(
    members: List<TripManager>,
    selectedUserId: String?,
    onSelect: (String) -> Unit,
    isError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedLabel = members.find { it.userId == selectedUserId }?.userId ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedLabel,
            onValueChange = {},
            readOnly = true,
            isError = isError,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            members.forEach { member ->
                DropdownMenuItem(
                    text = { Text(member.userId) },
                    onClick = {
                        onSelect(member.userId)
                        expanded = false
                    }
                )
            }
        }
    }
}
