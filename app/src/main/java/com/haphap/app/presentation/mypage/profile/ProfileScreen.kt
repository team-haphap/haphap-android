package com.haphap.app.presentation.mypage.profile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.core.extensions.toastBottomInset
import com.haphap.app.presentation.mypage.profile.component.ProfileImageSection
import com.haphap.app.presentation.mypage.profile.component.ProfileTextFieldSection
import com.haphap.app.presentation.mypage.profile.component.ProfileTopBar
import com.haphap.app.presentation.mypage.profile.ProfileContract.SideEffect.NavigateBack
import com.haphap.app.presentation.mypage.profile.ProfileContract.SideEffect.OpenImagePicker
import com.haphap.app.presentation.mypage.profile.ProfileContract.SideEffect.ShowToast

@Composable
fun ProfileRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let(viewModel::onImageSelected) }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NavigateBack -> navigateBack()

                    is OpenImagePicker -> imagePicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )

                    is ShowToast ->
                        Toast.makeText(context, sideEffect.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    ProfileScreen(
        uiState = uiState,
        nicknameState = viewModel.nicknameState,
        onBackClick = viewModel::onBackClick,
        onImageClick = viewModel::onImageClick,
        onSaveClick = viewModel::onSaveClick,
        modifier = modifier,
    )
}

@Composable
private fun ProfileScreen(
    uiState: ProfileContract.State,
    nicknameState: TextFieldState,
    onBackClick: () -> Unit,
    onImageClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = HapHapTheme.colors.white,
        topBar = {
            ProfileTopBar(
                onBackClick = onBackClick,
            )
        },
        bottomBar = {
            HapHapBasicButton(
                text = "저장하기",
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.Primary(enabled = true),
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .toastBottomInset(),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Spacer(modifier = Modifier.height(51.dp))

            ProfileImageSection(
                imageUrl = uiState.profileImageUrl,
                onImageClick = onImageClick,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileTextFieldSection(
                state = nicknameState,
                text = "닉네임",
                nicknameStatus = uiState.nicknameStatus,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    HapHapTheme {
        ProfileScreen(
            uiState = ProfileContract.State(),
            nicknameState = rememberTextFieldState("죠르디"),
            onBackClick = {},
            onImageClick = {},
            onSaveClick = {},
        )
    }
}
