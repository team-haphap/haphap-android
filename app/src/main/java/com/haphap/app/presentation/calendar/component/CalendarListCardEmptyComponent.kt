package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun CalendarListCardEmptyComponent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = HapHapTheme.colors.gray100),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_calendar_error_53),
            contentDescription = null,
            modifier = Modifier.size(53.dp),
            tint = HapHapTheme.colors.gray200,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "오늘 발표 예상 공고가 없습니다",
            color = HapHapTheme.colors.gray400,
            style = HapHapTheme.typography.caption.sb12,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarListCardEmptyComponentPreview() {
    HapHapTheme {
        CalendarListCardEmptyComponent()
    }
}
