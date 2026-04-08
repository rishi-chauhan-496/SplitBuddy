package com.example.splitbuddy.ui.home_screen.group_screen

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.splitbuddy.ui.theme.SplitBuddyTheme

@Composable
fun GroupListCard() {
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
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            Text(
                text = "Group name",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "members",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview4() {
    SplitBuddyTheme {
        GroupListCard()
    }
}