package com.example.splitbuddy.ui.home_screen.expense.expense_creating

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.home_screen.expense.ExpensePersonCard
import com.example.splitbuddy.ui.theme.gradient

@Composable
fun ExpenseScreen2(
    state: ExpenseUiState,
    onSplitMethodChange: (SplitMethod) -> Unit,
    onSplitAmountChange: (Int, String) -> Unit,
    onPercentChange: (Int, String) -> Unit,
    onUserToggle: (String) -> Unit,
    onNext: () -> Unit
) {
    val total = state.amount.toDoubleOrNull() ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = stringResource(R.string.Expense_Screen_2_Title),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )
        Text(
            text = "${stringResource(R.string.Expense_Screen_2_Title_2)} ₹${state.amount} ?",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // How many people are splitting
        Text(
            text = "Splitting among ${state.includedCount} of ${state.members.size} people",
            color = Color.Gray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Split method tabs ─────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            SplitTabButton(
                label = stringResource(R.string.Expense_Screen_2_Equal_Button),
                isSelected = state.splitMethod == SplitMethod.EQUAL,
                shape = RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp),
                modifier = Modifier.weight(1f),
                onClick = { onSplitMethodChange(SplitMethod.EQUAL) }
            )
            SplitTabButton(
                label = stringResource(R.string.Expense_Screen_2_Percent_Button),
                isSelected = state.splitMethod == SplitMethod.PERCENT,
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.weight(1f),
                onClick = { onSplitMethodChange(SplitMethod.PERCENT) }
            )
            SplitTabButton(
                label = stringResource(R.string.Expense_Screen_2_Amount_Button),
                isSelected = state.splitMethod == SplitMethod.AMOUNT,
                shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp),
                modifier = Modifier.weight(1f),
                onClick = { onSplitMethodChange(SplitMethod.AMOUNT) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Running total bar (hidden for Equal) ──────────────────────────────
        when (state.splitMethod) {
            SplitMethod.AMOUNT -> {
                val assigned = state.members.mapIndexed { index, member ->
                    if (state.isIncluded(member.userId))
                        state.splitAmounts.getOrElse(index) { "0.00" }.toDoubleOrNull() ?: 0.0
                    else 0.0
                }.sum()
                RunningTotalBar(assigned = assigned, total = total, unit = "₹")
            }
            SplitMethod.PERCENT -> {
                val assignedPct = state.members.mapIndexed { index, member ->
                    if (state.isIncluded(member.userId))
                        state.percentages.getOrElse(index) { "0.00" }.toDoubleOrNull() ?: 0.0
                    else 0.0
                }.sum()
                RunningTotalBar(assigned = assignedPct, total = 100.0, unit = "%")
            }
            SplitMethod.EQUAL -> {}
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── Member cards ──────────────────────────────────────────────────────
        LazyColumn(modifier = Modifier.weight(1f)) {
            when (state.splitMethod) {

                SplitMethod.EQUAL -> {
                    itemsIndexed(state.members) { index, member ->
                        ExpensePersonCard(
                            name = member.userId,
                            amount = state.splitAmounts.getOrElse(index) { "0.00" },
                            isEditable = false,
                            isIncluded = state.isIncluded(member.userId),
                            suffix = "₹",
                            onToggleInclude = { onUserToggle(member.userId) },
                            onAmountChange = {}
                        )
                    }
                }

                SplitMethod.AMOUNT -> {
                    itemsIndexed(state.members) { index, member ->
                        val included = state.isIncluded(member.userId)
                        ExpensePersonCard(
                            name = member.userId,
                            amount = state.splitAmounts.getOrElse(index) { "" },
                            isEditable = included,
                            isIncluded = included,
                            suffix = "₹",
                            onToggleInclude = { onUserToggle(member.userId) },
                            onAmountChange = { onSplitAmountChange(index, it) }
                        )
                    }
                }

                SplitMethod.PERCENT -> {
                    itemsIndexed(state.members) { index, member ->
                        val included = state.isIncluded(member.userId)
                        ExpensePersonCard(
                            name = member.userId,
                            amount = state.percentages.getOrElse(index) { "" },
                            isEditable = included,
                            isIncluded = included,
                            suffix = "%",
                            onToggleInclude = { onUserToggle(member.userId) },
                            onAmountChange = { onPercentChange(index, it) }
                        )
                    }
                }
            }
        }

        state.splitError?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onNext,
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
                Text(text = stringResource(R.string.next_), color = Color.White)
            }
        }
    }
}

@Composable
private fun SplitTabButton(
    label: String,
    isSelected: Boolean,
    shape: Shape,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = shape,
        border = BorderStroke(1.dp, if (isSelected) Color(0xFF6A1BFF) else Color.Gray),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF6A1BFF).copy(alpha = 0.12f)
            else MaterialTheme.colorScheme.background
        ),
        modifier = modifier.fillMaxHeight()
    ) {
        Text(
            text = label,
            color = if (isSelected) Color(0xFF6A1BFF) else Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun RunningTotalBar(
    assigned: Double,
    total: Double,
    unit: String
) {
    val progress = if (total > 0) (assigned / total).toFloat().coerceIn(0f, 1f) else 0f
    val isOver = assigned > total + 0.01
    val remaining = total - assigned
    val barColor = if (isOver) Color.Red else Color(0xFF6A1BFF)

    Column {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = barColor,
            trackColor = Color.LightGray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$unit${"%.2f".format(assigned)} assigned",
                fontSize = 12.sp,
                color = barColor,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = if (isOver) "$unit${"%.2f".format(-remaining)} over"
                else "$unit${"%.2f".format(remaining)} remaining",
                fontSize = 12.sp,
                color = if (isOver) Color.Red else Color.Gray
            )
        }
    }
}