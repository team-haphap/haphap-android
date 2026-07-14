package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import java.time.YearMonth

@Composable
fun CalendarHeader(
    yearMonth: YearMonth,
    onDateClick: () -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_30),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .noRippleClickable(onClick = onBackClick),
            tint = HapHapTheme.colors.gray400,
        )

        Text(
            text = "${yearMonth.year}년 ${yearMonth.monthValue}월",
            color = HapHapTheme.colors.gray700,
            style = HapHapTheme.typography.body.sb16,
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = HapHapTheme.colors.gray100)
                .padding(horizontal = 42.dp, vertical = 4.dp)
                .noRippleClickable(onClick = onDateClick),
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_30),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .noRippleClickable(onClick = onNextClick),
            tint = HapHapTheme.colors.gray400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarHeaderPreview() {
    HapHapTheme {
        CalendarHeader(
            yearMonth = YearMonth.now(),
            onBackClick = {},
            onNextClick = {},
            onDateClick = {},
        )
    }
}
