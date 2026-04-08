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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.theme.SplitBuddyTheme
import com.example.splitbuddy.ui.theme.gradient2

@Composable
fun ExpenseScreen3(
    title: String,
    amount: String,
    person: String,
    persons: List<Triple<Int, String, Double>>
) {

    var isEditable by remember { mutableStateOf(false) }

    val amounts = remember {
        persons.map { it.third.toString() }.toMutableStateList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( color = MaterialTheme.colorScheme.background )
            .padding(16.dp)
    ) {

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(gradient2),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = amount,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = person,
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

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
            onClick = {},
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
                    .background(com.example.splitbuddy.ui.theme.gradient)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.save_),
                    color = Color.White
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    SplitBuddyTheme {

    }
}