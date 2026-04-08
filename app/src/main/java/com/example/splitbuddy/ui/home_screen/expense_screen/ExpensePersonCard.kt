package com.example.splitbuddy.ui.home_screen.expense_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.splitbuddy.ui.theme.SplitBuddyTheme

@Composable
fun ExpensePersonCard(
    image: Int,
    name: String,
    amount: String,
    isEditable: Boolean,
    onAmountChange: (String) -> Unit
) {
    val isDark = isSystemInDarkTheme()

    Surface(
        modifier = Modifier.fillMaxWidth().padding(0.dp,4.dp)
        .shadow(
            elevation = 12.dp,
            shape = RoundedCornerShape(24.dp),
            ambientColor = if (isDark) Color.White else Color.Black,
            spotColor = if (isDark) Color.White else Color.Black
        ),
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "Person Profile",
                Modifier.size(50.dp)
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            var amount by rememberSaveable { mutableStateOf(amount) }

            TextField(
                value = amount,
                onValueChange = onAmountChange,
                readOnly = !isEditable,
                modifier = Modifier.weight(0.7f).width(150.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview1() {
    SplitBuddyTheme {

    }
}
