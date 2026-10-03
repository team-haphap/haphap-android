package com.haphap.app.presentation.register.passcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.data.model.register.RegisterPassCardModel

@Composable
fun RegisterPassCardRoute(
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterPassCardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RegisterPassCardScreen(
        uiState = uiState,
        onHomeClick = navigateToHome,
        onSaveClick = {},
        modifier = modifier,
    )
}

@Composable
fun RegisterPassCardScreen(
    uiState: RegisterPassCardContract.State,
    onHomeClick: () -> Unit,
    onSaveClick: (ImageBitmap) -> Unit,
    modifier : Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "${uiState.userName}님의 합격을 축하드려요!",
            style = HapHapTheme.typography.subtitle.b22,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "기다려온 순간, 진심으로 축하드려요",
            style = HapHapTheme.typography.body.sb13,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(31.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .aspectRatio(312f / 540f)
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                }
                .clip(RoundedCornerShape(18.dp))
        ) {
            UrlImage(
                url = uiState.backgroundImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 22.dp, end = 22.dp, top = 26.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                UrlImage(
                    url = uiState.logoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(44.dp),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${uiState.companyName} ${uiState.recruitName}",
                    style = HapHapTheme.typography.body.b18,
                    color = HapHapTheme.colors.primary100,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row {
            HapHapBasicButton(
                text = "홈으로",
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.UnSelected,
                onClick = onHomeClick,
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(12.dp))

            HapHapBasicButton(
                text = "저장하기",
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.Primary(enabled = true),
                onClick = {
                    coroutineScope.launch {
                        val bitmap = graphicsLayer.toImageBitmap()
                        onSaveClick(bitmap)
                    }
                },
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(37.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterPassCardScreenPreview() {
    HapHapTheme {
        RegisterPassCardScreen(
            uiState = RegisterPassCardContract.State(
                userName = "박연수",
                recruitName = "2027 신입 채용 공고~~~~~~~~~~~~~~~~~~~~",
                companyName = "카카오",
                logoUrl = "",
                backgroundImageUrl = "",
            ),
            onHomeClick = {},
            onSaveClick = {},
        )
    }
}
