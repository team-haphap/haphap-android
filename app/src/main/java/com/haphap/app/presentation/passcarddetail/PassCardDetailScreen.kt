package com.haphap.app.presentation.passcarddetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.core.extensions.toastBottomInset
import kotlinx.coroutines.launch

@Composable
fun PassCardDetailScreen(
    uiState: PassCardDetailContract.State,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onBackgroundLoaded: () -> Unit,
    onLogoLoaded: () -> Unit,
    onSaveClick: (ImageBitmap) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()

    Scaffold(
        modifier = modifier,
        containerColor = HapHapTheme.colors.white,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_30),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .noRippleClickable(onClick = onBackClick),
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 14.dp, bottom = 9.dp)
                    .toastBottomInset(),
            ) {
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
                    colorType = ButtonType.Primary(enabled = uiState.isSaveEnabled),
                    onClick = {
                        coroutineScope.launch {
                            val bitmap = graphicsLayer.toImageBitmap()
                            onSaveClick(bitmap)
                        }
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${uiState.userName}님의 합격을 축하드려요!",
                style = HapHapTheme.typography.subtitle.b22,
                color = HapHapTheme.colors.gray800,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "기다려온 순간, 진심으로 축하드려요",
                style = HapHapTheme.typography.body.sb13,
                color = HapHapTheme.colors.gray400,
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Todo: 칩 추가

            Spacer(modifier = Modifier.height(22.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp)
                    .aspectRatio(320f / 440f)
                    .drawWithContent {
                        graphicsLayer.record {
                            this@drawWithContent.drawContent()
                        }
                        drawLayer(graphicsLayer)
                    }
                    .clip(RoundedCornerShape(20.dp))
            ) {
                UrlImage(
                    url = uiState.backgroundImageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    crossfade = false,
                    onSuccess = onBackgroundLoaded,
                    modifier = Modifier.fillMaxSize(),
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 21.dp, end = 85.dp, top = 24.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    UrlImage(
                        url = uiState.logoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        crossfade = false,
                        onSuccess = onLogoLoaded,
                        modifier = Modifier.height(36.dp),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "${uiState.companyName} ${uiState.recruitName}",
                        style = HapHapTheme.typography.body.b14,
                        color = HapHapTheme.colors.primary100,
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCardDetailScreenPreview() {
    HapHapTheme {
        PassCardDetailScreen(
            uiState = PassCardDetailContract.State(
                userName = "박연수",
                recruitName = "2027 신입 채용 공고~~~~~~~~~~~~~~~~~~~~",
                companyName = "카카오",
                logoUrl = "",
                backgroundImageUrl = "",
            ),
            onBackClick = {},
            onHomeClick = {},
            onBackgroundLoaded = {},
            onLogoLoaded = {},
            onSaveClick = {},
        )
    }
}
