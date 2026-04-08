package com.example.splitbuddy.ui.home_screen.expense_screen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.theme.SplitBuddyTheme
import com.example.splitbuddy.ui.theme.gradient

@Composable
fun ExpenseScreen1(expenseName: String,
                   onExpenseNameChange: (String) -> Unit,
                   amount: String,
                   onAmountChange: (String) -> Unit,
                   paidBy: String,
                   onPaidByChange: (String) -> Unit,
                   description: String,
                   onDescriptionChange: (String) -> Unit,
                   onNext: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( color = MaterialTheme.colorScheme.background )
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

        Text(
            text = stringResource(R.string.Expense_Screen_1_TextFiled),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
            modifier = Modifier.height(18.dp)
        )

        TextField(
            value = expenseName,
            onValueChange = onExpenseNameChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.Expense_Screen_1_TextFiled_2),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
            modifier = Modifier.height(18.dp)
        )

        TextField(
            value = amount,
            onValueChange =  onAmountChange ,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.Expense_Screen_1_TextFiled_3),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
            modifier = Modifier.height(18.dp)
        )

        TextField(
            value = paidBy,
            onValueChange = onPaidByChange ,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )

        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.Expense_Screen_1_TextFiled_4),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
            modifier = Modifier.height(18.dp)
        )

        TextField(
            value = description,
            onValueChange = onDescriptionChange ,
            modifier = Modifier.fillMaxWidth().height(100.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )

        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onNext() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .background(gradient)
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SplitBuddyTheme {

    }
}