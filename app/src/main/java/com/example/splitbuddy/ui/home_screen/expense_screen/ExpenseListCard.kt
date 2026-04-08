package com.example.splitbuddy.ui.home_screen.expense_screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.theme.SplitBuddyTheme


@Composable
fun ExpenseListCard(
    title: String,
    by: String,
    amount: Double,
    type: String
) {
    val isDark = isSystemInDarkTheme()

    Surface(
        modifier = Modifier.fillMaxWidth().padding(12.dp,4.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = if (isDark) Color.White else Color.Black,
                spotColor = if (isDark) Color.White else Color.Black
        ),
        color = MaterialTheme.colorScheme.background
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.by),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = by,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = amount.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = type,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = stringResource(R.string.type),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 150)
@Composable
fun PreviewBillListCard() {
    SplitBuddyTheme {
        ExpenseListCard("Dinner at Marina Walk", "Rishi Chauhan", 1000.00, "Equal")
    }
}