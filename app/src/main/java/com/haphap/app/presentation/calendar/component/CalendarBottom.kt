package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.PresentChanceType
import com.haphap.app.core.designsystem.type.toColor

@Composable
fun CalendarBottom(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "발표 가능성",
            color = HapHapTheme.colors.gray100,
            style = HapHapTheme.typography.caption.sb12,
            modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = HapHapTheme.colors.gray400)
                .padding(horizontal = 18.dp, vertical = 4.dp),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "낮음",
                color = HapHapTheme.colors.gray400,
                style = HapHapTheme.typography.caption.sb12,
            )

            Spacer(modifier = Modifier.width(2.dp))

            PresentChanceType.entries
                .filter { it != PresentChanceType.NONE }
                .forEach { presentChance ->
                    Box(
                        modifier = Modifier
                            .size(width = 25.dp, height = 8.dp)
                            .clip(shape = CircleShape)
                            .background(color = presentChance.toColor(HapHapTheme.colors)),
                    )
                }

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "높음",
                color = HapHapTheme.colors.gray400,
                style = HapHapTheme.typography.caption.sb12,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarBottomPreview() {
    HapHapTheme {
        CalendarBottom()
    }
}
