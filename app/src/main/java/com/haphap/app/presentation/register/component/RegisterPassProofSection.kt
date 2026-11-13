package com.haphap.app.presentation.register.component

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.register.model.MAX_PROOF_IMAGE_COUNT
import com.haphap.app.presentation.register.model.RegisterProofImageModel
import com.haphap.app.presentation.register.type.RegisterAddImageType
import com.haphap.app.presentation.register.type.RegisterProofImageErrorType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RegisterPassProofSection(
    images: ImmutableList<RegisterProofImageModel>,
    onAddImageClick: () -> Unit,
    onRemoveImage: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (images.isEmpty()) {
                "합격 인증을 남겨주세요!"
            } else {
                "첨부된 이미지 (${images.count { it.error == null }}/$MAX_PROOF_IMAGE_COUNT)"
            },
            style = HapHapTheme.typography.body.b18,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(13.dp))

        if (images.isEmpty()) {
            PassProofEmptyContent(onAddImageClick = onAddImageClick)
        } else {
            PassProofAttachedContent(
                images = images,
                onAddImageClick = onAddImageClick,
                onRemoveImage = onRemoveImage
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        RegisterAddImageGuide()
    }
}

@Composable
private fun PassProofEmptyContent(
    onAddImageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "합격 사실을 확인할 수 있는 결과 화면, 문자, 메일 등의\n이미지를 첨부해주세요. (필수)",
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(12.dp))

        RegisterAddImage(
            type = RegisterAddImageType.INITIAL,
            onClick = onAddImageClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun PassProofAttachedContent(
    images: ImmutableList<RegisterProofImageModel>,
    onAddImageClick: () -> Unit,
    onRemoveImage: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        RegisterAddImage(
            type = RegisterAddImageType.ADDITIONAL,
            onClick = onAddImageClick,
            modifier = Modifier.fillMaxWidth(),
        )

        images.forEach { image ->
            key(image.uri) {
                RegisterUploadedImageItem(
                    fileName = image.fileName,
                    fileSize = image.fileSize,
                    thumbnail = image.uri,
                    errorMessage = image.error?.message,
                    onRemoveClick = { onRemoveImage(image.uri) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterPassProofSectionEmptyPreview() {
    HapHapTheme {
        RegisterPassProofSection(
            images = persistentListOf(),
            onAddImageClick = {},
            onRemoveImage = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterPassProofSectionAttachedPreview() {
    HapHapTheme {
        RegisterPassProofSection(
            images = persistentListOf(
                RegisterProofImageModel(
                    uri = Uri.parse("content://preview/1"),
                    fileName = "합격메일_01.png",
                    fileSize = "285.6KB",
                ),
                RegisterProofImageModel(
                    uri = Uri.parse("content://preview/2"),
                    fileName = "합격메일_02.png",
                    fileSize = "285.6KB",
                    error = RegisterProofImageErrorType.SIZE_EXCEEDED,
                ),
            ),
            onAddImageClick = {},
            onRemoveImage = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
