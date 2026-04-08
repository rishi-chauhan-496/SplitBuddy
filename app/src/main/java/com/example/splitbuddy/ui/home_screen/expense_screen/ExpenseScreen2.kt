package com.example.splitbuddy.ui.home_screen.expense_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
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
fun ExpenseScreen2(
    persons: List<Triple<Int, String, Double>>,
    amount: String,
    onNext: () -> Unit
) {

    var isEditable by rememberSaveable { mutableStateOf(false) }

    val amounts = rememberSaveable {
        persons.map { it.third.toString() }.toMutableStateList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( color = MaterialTheme.colorScheme.background )
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        val message = stringResource(id = R.string.Expense_Screen_2_Title_2)
        Text(
            text = stringResource(R.string.Expense_Screen_2_Title),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )
        Text(
            text = "$message $amount ?",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        var flag by rememberSaveable { mutableIntStateOf(0) }

        Row(
            modifier = Modifier.fillMaxWidth().height(38.dp)
        ){

            Button(
                onClick = { isEditable = false; flag = 0 },
                shape = RoundedCornerShape(10.dp,0.dp,0.dp,10.dp),
                border = BorderStroke(1.dp,Color.Gray),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier.weight(1f).height(52.dp)
                ) {
                Text(
                    text = stringResource(R.string.Expense_Screen_2_Equal_Button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Button(
                onClick = { isEditable = true; flag = 1 },
                shape = RoundedCornerShape(0.dp,0.dp,0.dp,0.dp),
                border = BorderStroke(1.dp,Color.Gray),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier.weight(1f).height(52.dp)
            ) {
                Text(
                    text = stringResource(R.string.Expense_Screen_2_Percent_Button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Button(
                onClick = { isEditable = true; flag = 2 },
                shape = RoundedCornerShape(0.dp,10.dp,10.dp,0.dp),
                border = BorderStroke(1.dp,Color.Gray),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background),
                modifier = Modifier.weight(1f).height(52.dp)
            ) {
                Text(
                    text = stringResource(R.string.Expense_Screen_2_Amount_Button),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = when(flag) {
                0 -> stringResource(R.string.Expense_Screen_2_Text_Equal)
                1 -> stringResource(R.string.Expense_Screen_2_Text_Percent)
                2 -> stringResource(R.string.Expense_Screen_2_Text_Amount)
                else -> ""
            },
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(persons) { index, person ->

                ExpensePersonCard(
                    image = person.first,
                    name = person.second,
                    amount = amounts[index],
                    isEditable = isEditable,
                    onAmountChange = {
                        amounts[index] = it
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

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
                    text = stringResource(R.string.next_),
                    color = Color.White
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    SplitBuddyTheme {

    }
}