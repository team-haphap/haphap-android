        package com.haphap.app.core.designsystem.component.image

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.haphap.app.core.designsystem.theme.HapHapTheme

/**
 * URL을 통해 이미지를 비동기로 로드하여 표시하는 Composable입니다.
 *
 * Preview 모드에서는 placeholder 이미지를 표시하고,
 * 실제 실행 시에는 네트워크 이미지를 로드합니다.
 *
 * @param url 로드할 이미지의 URL
 * @param placeholderDrawable 로딩 중이거나 이미지가 없거나 로드에 실패했을 때 표시할 이미지의 리소스 ID
 * @param modifier Composable에 적용할 Modifier
 * @param contentScale 이미지 스케일링 방식 (기본: Fit)
 * @param contentDescription 접근성을 위한 이미지 설명
 */

@Composable
fun UrlImage(
    url: String,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderDrawable: Int? = null,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
) {
    if (LocalInspectionMode.current) {
        if (placeholderDrawable != null) {
            Image(
                painter = painterResource(placeholderDrawable),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = modifier,
            )
        } else {
            Box(modifier = modifier.background(HapHapTheme.colors.gray50))
        }
        return
    }

    var isLoading by remember(url) { mutableStateOf(false) }
    var isError by remember(url) { mutableStateOf(false) }
    val placeholder = placeholderDrawable?.let { painterResource(it) }

    AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(url.ifBlank { null })
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = placeholder,
        error = placeholder,
        fallback = placeholder,
        onLoading = {
            isLoading = true
            isError = false
        },
        onSuccess = {
            isLoading = false
            isError = false
        },
        onError = {
            isLoading = false
            isError = true
        },
        // TODO: 로딩 UI 디자인 미정. 일단 로딩 중에도 gray50 배경으로 처리하고, 로딩 디자인이 정해지면 isLoading 분기를 여기서 수정
        modifier = if ((isLoading || isError) && placeholder == null) {
            modifier.background(HapHapTheme.colors.gray50)
        } else {
            modifier
        },
    )
}

@Preview
@Composable
private fun UrlImagePreview() {
    UrlImage(
        url = "",
        modifier = Modifier.size(100.dp),
    )
}
