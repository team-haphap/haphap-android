package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun RegisterProgressBar(
    progress: Int,
    modifier: Modifier = Modifier,
    totalSteps: Int = 3,
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        repeat(totalSteps) { steps ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(
                        color = if (steps < progress) HapHapTheme.colors.gray700
                        else HapHapTheme.colors.gray200
                    )
            )
        }
    }
}

@Preview
@Composable
private fun RegisterProgressBarPreview() {
    HapHapTheme {
        Row(
            modifier = Modifier.background(HapHapTheme.colors.white)
        ) {
            RegisterProgressBar(progress = 2, totalSteps = 3)
        }
    }
}