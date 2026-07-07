package com.haphap.app.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
            .padding(bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.drawable.img_logo),
            contentDescription = null,
            modifier = Modifier.size(width = 121.dp, height = 129.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.drawable.img_text_logo),
            contentDescription = null,
            modifier = Modifier.size(width = 144.dp, height = 22.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "합격 발표가 움직이는 순간",
            style = HapHapTheme.typography.body.r14,
            color = HapHapTheme.colors.gray400,
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    HapHapTheme {
        SplashScreen()
    }
}