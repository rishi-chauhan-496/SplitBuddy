package com.example.splitbuddy.ui.home_screen.group.group_add_member_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.splitbuddy.R
import com.example.splitbuddy.data.local.model.User
import com.example.splitbuddy.ui.theme.gradient
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddMemberScreen(
    groupId: String,
    onBack: () -> Unit
) {
    val viewModel: AddMemberViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(groupId) {
        viewModel.load(groupId)
    }

    // Navigate back automatically after successful save
    LaunchedEffect(state.value.isSaved) {
        if (state.value.isSaved) onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.add_member_button),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Selection counter
        val count = state.value.selectedUserIds.size
        Text(
            text = when (count) {
                0 -> stringResource(R.string.add_member_hint)
                1 -> stringResource(R.string.add_member_selected_single)
                else -> stringResource(R.string.add_member_selected_plural, count)
            },
            color = if (count == 0) Color.Gray else Color(0xFF6A1BFF),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.value.isLoading -> {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF6A1BFF))
                }
            }

            state.value.error != null -> {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.add_member_error, state.value.error ?: ""))
                }
            }

            else -> {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.value.users) { user ->
                        val isExisting = user.id in state.value.existingMemberIds
                        val isSelected = user.id in state.value.selectedUserIds

                        UserSelectCard(
                            user = user,
                            isSelected = isSelected,
                            isExisting = isExisting,
                            onClick = { viewModel.onUserToggle(user.id) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Add Members button
        Button(
            onClick = { viewModel.addMembers(groupId) },
            enabled = state.value.selectedUserIds.isNotEmpty() && !state.value.isSaving,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        if (state.value.selectedUserIds.isNotEmpty()) gradient
                        else Brush.linearGradient(
                            listOf(Color.Gray, Color.Gray)
                        )
                    )
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (state.value.isSaving) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = stringResource(R.string.add_member_button),
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

// ── User selection card ───────────────────────────────────────────────────────

@Composable
private fun UserSelectCard(
    user: User,
    isSelected: Boolean,
    isExisting: Boolean,
    onClick: () -> Unit
) {
    val borderColor = when {
        isExisting -> Color.LightGray
        isSelected -> Color(0xFF6A1BFF)
        else -> Color.Transparent
    }
    val bgColor = when {
        isExisting -> MaterialTheme.colorScheme.background
        isSelected -> Color(0xFF6A1BFF).copy(alpha = 0.08f)
        else -> MaterialTheme.colorScheme.background
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable(enabled = !isExisting) { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Initials avatar
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(
                    if (isExisting) Color.LightGray
                    else Color(0xFF6A1BFF).copy(alpha = 0.15f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.firstName.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                color = if (isExisting) Color.Gray else Color(0xFF6A1BFF),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${user.firstName} ${user.lastName}",
                fontWeight = FontWeight.SemiBold,
                color = if (isExisting) Color.Gray else MaterialTheme.colorScheme.secondary
            )
            Text(
                text = if (isExisting) stringResource(R.string.add_member_already_in_group)
                else user.email,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // Checkmark when selected
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF6A1BFF)),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}