package com.example.splitbuddy.ui.home_screen.group.groups_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.home_screen.group.GroupListCard
import com.example.splitbuddy.ui.home_screen.ownerID
import com.example.splitbuddy.ui.theme.Primary
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupsScreen(
    onNext: (String) -> Unit,
    onCreate: () -> Unit
) {
    val viewModel: GroupsDataViewModel = koinViewModel()
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadGroups(ownerID)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        when {
            state.value.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Primary)
                }
            }

            state.value.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Something went wrong.\n${state.value.error}",
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            state.value.groups.isEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No groups yet",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap + to create your first group",
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.value.groups) { group ->
                        GroupListCard(
                            groupName = group.groupName,
                            totalMember = group.totalMember,
                            totalExpense = group.totalExpense,
                            totalAmount = group.totalAmount,
                            onClick = { onNext(group.id) }
                        )
                    }
                }
            }
        }

        // FAB — always visible
        FloatingActionButton(
            onClick = onCreate,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Primary,
            contentColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = stringResource(R.string.Group_Creation_Button))
        }
    }
}