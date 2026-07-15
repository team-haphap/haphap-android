package com.haphap.app.presentation.register.passcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.data.model.register.RegisterPassCardModel

@Composable
fun RegisterPassCardRoute(
    passCardModel: RegisterPassCardModel,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RegisterPassCardScreen(
        passCardModel = passCardModel,
        onHomeClick = navigateToHome,
        modifier = modifier,
    )
}

@Composable
fun RegisterPassCardScreen(
    passCardModel: RegisterPassCardModel,
    onHomeClick: () -> Unit,
    modifier : Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
            .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "${passCardModel.userName}님의 합격을 축하드려요!",
            style = HapHapTheme.typography.subtitle.b22,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "기다려온 순간, 진심으로 축하드려요.",
            style = HapHapTheme.typography.body.sb13,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(31.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .aspectRatio(312f / 540f)
                .clip(RoundedCornerShape(18.dp))
        ) {
            UrlImage(
                url = passCardModel.backgroundImageUrl,
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
                    url = passCardModel.logoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(44.dp),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${passCardModel.companyName} ${passCardModel.recruitName}",
                    style = HapHapTheme.typography.body.b18,
                    color = HapHapTheme.colors.primary100,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "홈으로",
            style = HapHapTheme.typography.body.sb13.copy(
                textDecoration = TextDecoration.Underline,
            ),
            color = HapHapTheme.colors.gray300,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable(onClick = onHomeClick)
        )

        Spacer(modifier = Modifier.height(37.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterPassCardScreenPreview() {
    HapHapTheme {
        RegisterPassCardScreen(
            passCardModel = RegisterPassCardModel(
                userName = "박연수",
                recruitName = "2027 신입 채용 공고~~~~~~~~~~~~~~~~~~~~",
                companyName = "카카오",
                logoUrl = "",
                backgroundImageUrl = "",
            ),
            onHomeClick = {},
        )
    }
}