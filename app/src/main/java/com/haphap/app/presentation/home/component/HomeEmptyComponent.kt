package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun HomeEmptyComponent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(138.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            style = HapHapTheme.typography.caption.sb12,
            color = HapHapTheme.colors.gray400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeEmptyComponentPreview() {
    HapHapTheme {
        HomeEmptyComponent(
            text = "카테고리에 해당하는 공고가 없어요",
        )
    }
}