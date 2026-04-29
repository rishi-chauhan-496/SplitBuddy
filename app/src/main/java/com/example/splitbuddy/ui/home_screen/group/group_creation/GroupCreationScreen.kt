package com.example.splitbuddy.ui.home_screen.group.group_creation

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.home_screen.expense.ExpensePersonCard2
import com.example.splitbuddy.ui.home_screen.ownerID
import com.example.splitbuddy.ui.theme.SplitBuddyTheme
import com.example.splitbuddy.ui.theme.gradient
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupCreationScreen(
    onGroupCreated: () -> Unit
) {

    val viewModel: GroupCreationViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( color = MaterialTheme.colorScheme.background )
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.Group_Creation_Screen_Title),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.groupName,
            onValueChange = { viewModel.onGroupNameChange(it) },
            placeholder = { Text("Enter group name here") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF6A1BFF),
                unfocusedIndicatorColor = Color(0xFF6A1BFF),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.Group_Creation_Filed),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(uiState.users) { user ->
                ExpensePersonCard2(
                    user = user,
                    isSelected = uiState.selectedUserIds.contains(user.id),
                    onCheckedChange = {
                        viewModel.onUserSelected(user.id)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.createGroup(ownerID)
            },
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
                    text = stringResource(R.string.save_),
                    color = Color.White
                )
            }
        }

        LaunchedEffect(uiState) {
            if (uiState.success) {
                onGroupCreated()
                viewModel.resetSuccess()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview8() {
    SplitBuddyTheme {

    }
}