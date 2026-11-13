package com.haphap.app.presentation.passcertification.detail

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.core.extensions.toastBottomInset
import com.haphap.app.data.model.passcertification.PassCertificationDetailModel
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.presentation.passcertification.detail.PassCertificationDetailContract.SideEffect.NavigateToComplete
import com.haphap.app.presentation.passcertification.detail.PassCertificationDetailContract.SideEffect.OnShowToast
import com.haphap.app.presentation.passcertification.component.PassCertificationAddImageSection
import com.haphap.app.presentation.passcertification.component.PassCertificationImageSection
import com.haphap.app.presentation.passcertification.component.PassCertificationInfoSection
import com.haphap.app.presentation.passcertification.component.PassCertificationNotice
import com.haphap.app.presentation.passcertification.component.PassCertificationRejectReason
import com.haphap.app.presentation.passcertification.component.PassCertificationTopBar
import com.haphap.app.presentation.passcertification.model.PassCertificationImageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PassCertificationDetailRoute(
    navigateBack: () -> Unit,
    navigateToComplete: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PassCertificationDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val showToast = LocalToastTrigger.current

    val singleImagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let { viewModel.onImagesPicked(listOf(it)) }
    }

    val remainingImageCount = uiState.remainingImageCount
    val multipleImagePickerContract = remember(remainingImageCount) {
        ActivityResultContracts.PickMultipleVisualMedia(
            maxItems = remainingImageCount.coerceAtLeast(2),
        )
    }
    val multipleImagePicker = rememberLauncherForActivityResult(
        multipleImagePickerContract,
    ) { uris ->
        viewModel.onImagesPicked(uris)
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToComplete -> navigateToComplete()
                    is OnShowToast -> showToast(sideEffect.message, sideEffect.isAlarm)
                }
            }
        }
    }

    PassCertificationDetailScreen(
        certification = uiState.certification,
        isResubmitMode = uiState.isResubmitMode,
        newImages = uiState.newImages,
        isResubmitEnabled = uiState.isResubmitEnabled,
        onBackClick = navigateBack,
        onSubmitClick = viewModel::onSubmitClick,
        onAddImageClick = {
            val request = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            when {
                remainingImageCount <= 0 -> Unit
                remainingImageCount == 1 -> singleImagePicker.launch(request)
                else -> multipleImagePicker.launch(request)
            }
        },
        onDeleteImageClick = viewModel::onDeleteImageClick,
        onResubmitClick = viewModel::onResubmitClick,
        modifier = modifier,
    )
}

@Composable
fun PassCertificationDetailScreen(
    certification: PassCertificationDetailModel,
    isResubmitMode: Boolean,
    newImages: ImmutableList<PassCertificationImageModel>,
    isResubmitEnabled: Boolean,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onAddImageClick: () -> Unit,
    onDeleteImageClick: (Int) -> Unit,
    onResubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = HapHapTheme.colors.white,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            PassCertificationTopBar(
                onBackClick = onBackClick,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
        },
        bottomBar = {
            Column {
                if (!isResubmitMode) {
                    Spacer(modifier = Modifier.height(15.dp))

                    PassCertificationNotice()
                }

                HapHapBasicButton(
                    text = if (isResubmitMode) "다시 제출하기" else "인증자료 제출하기",
                    textStyle = HapHapTheme.typography.body.b18,
                    colorType = ButtonType.Primary(
                        enabled = !isResubmitMode || isResubmitEnabled,
                    ),
                    onClick = if (isResubmitMode) onResubmitClick else onSubmitClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .toastBottomInset(),
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            PassCertificationInfoSection(
                companyName = certification.companyName,
                title = certification.title,
                stageName = certification.stageName,
                submittedDate = certification.submittedDate,
            )

            Spacer(modifier = Modifier.height(24.dp))

            PassCertificationRejectReason(
                reason = certification.rejectReason,
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isResubmitMode) {
                PassCertificationAddImageSection(
                    images = newImages,
                    onAddImageClick = onAddImageClick,
                    onDeleteImageClick = onDeleteImageClick,
                )
            } else {
                PassCertificationImageSection(
                    imageUrls = certification.imageUrls,
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCertificationDetailScreenPreview() {
    HapHapTheme {
        PassCertificationDetailScreen(
            certification = previewCertification,
            isResubmitMode = false,
            newImages = persistentListOf(),
            isResubmitEnabled = false,
            onBackClick = {},
            onSubmitClick = {},
            onAddImageClick = {},
            onDeleteImageClick = {},
            onResubmitClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCertificationDetailScreenResubmitPreview() {
    HapHapTheme {
        PassCertificationDetailScreen(
            certification = previewCertification,
            isResubmitMode = true,
            newImages = persistentListOf(),
            isResubmitEnabled = false,
            onBackClick = {},
            onSubmitClick = {},
            onAddImageClick = {},
            onDeleteImageClick = {},
            onResubmitClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCertificationDetailScreenResubmitWithImagePreview() {
    HapHapTheme {
        PassCertificationDetailScreen(
            certification = previewCertification,
            isResubmitMode = true,
            newImages = persistentListOf(
                PassCertificationImageModel(uri = "", imageId = 1),
                PassCertificationImageModel(uri = "", isError = true),
            ),
            isResubmitEnabled = true,
            onBackClick = {},
            onSubmitClick = {},
            onAddImageClick = {},
            onDeleteImageClick = {},
            onResubmitClick = {},
        )
    }
}

private val previewCertification = PassCertificationDetailModel(
    companyName = "가온테크",
    title = "서비스 기획 인턴",
    stageName = "서류",
    submittedDate = "2026.10.15.",
    rejectReason = "첨부 이미지에서 공고명과 합격 여부를 확인하기 어려워요.",
    imageUrls = persistentListOf(""),
)
