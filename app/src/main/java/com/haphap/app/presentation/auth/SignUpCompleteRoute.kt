package com.haphap.app.presentation.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignUpCompleteRoute(
    modifier: Modifier = Modifier,
    userName: String,
    onStartClick: () -> Unit,
) {
    SignUpCompleteScreen(
        modifier = modifier,
        userName = userName.ifBlank { "사용자" },
        onStartClick = onStartClick
    )
}