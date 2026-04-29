package com.example.splitbuddy.ui.home_screen.group.group_updating

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.ui.theme.gradient
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupUpdatingScreen(
    groupId: String,
    onBack: () -> Unit
) {
    val viewModel: GroupUpdatingViewModel = koinViewModel()
    val state = viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(groupId) {
        viewModel.load(groupId)
    }

    val context = LocalContext.current

    LaunchedEffect(state.value.isUpdated, state.value.isDeleted) {

        if (state.value.isUpdated) {
            Toast.makeText(context, "Group Updated", Toast.LENGTH_SHORT).show()
            onBack()
        }

        if (state.value.isDeleted) {
            Toast.makeText(context, "Group Deleted", Toast.LENGTH_SHORT).show()
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( color = MaterialTheme.colorScheme.background )
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.Group_Updating_Screen_Title),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.value.groupName,
            onValueChange = { viewModel.onNameChange(it) },
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
            text = stringResource(R.string.Group_Updating_Filed),
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

//        LazyColumn {
//            items(){ friend ->
//                ExpensePersonCard2(friend)
//            }
//        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.update(groupId, state.value.groupName)
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

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                showDialog = true
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
                    .background(color = Color.Red)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = "Delete",
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = stringResource(R.string.delete),
                        color = Color.White
                    )
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Delete Group") },
                text = { Text("Are you sure you want to delete this group?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            viewModel.delete(groupId)
                        }
                    ) {
                        Text("Yes, Delete")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}