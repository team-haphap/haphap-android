package com.haphap.app.presentation.auth

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.state.UiState

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val loginState by viewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is UiState.Failure -> {
                Toast.makeText(context, state.msg, Toast.LENGTH_SHORT).show()
                viewModel.consumeFailure()
            }
            is UiState.Success -> {
                onLoginSuccess()
            }
            else -> Unit
        }
    }

    LoginScreen(
        modifier = modifier,
        isLoading = loginState is UiState.Loading,
        onKakaoLoginClick = {viewModel.onKakaoLoginClick(context)}
    )
}