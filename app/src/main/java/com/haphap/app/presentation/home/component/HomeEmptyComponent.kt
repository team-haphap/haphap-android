package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun HomeEmptyComponent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = HapHapTheme.typography.caption.sb12,
        color = HapHapTheme.colors.gray400,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp, bottom = 61.dp),
    )
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