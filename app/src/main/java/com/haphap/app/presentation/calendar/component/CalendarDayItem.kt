package com.haphap.app.presentation.calendar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapColors
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.presentation.calendar.type.DayType
import com.haphap.app.presentation.calendar.type.PresentChance
import java.time.LocalDate

private data class DayItemStyle(
    val textColor: Color,
    val chanceColor: Color,
)

private fun PresentChance.toColor(colors: HapHapColors): Color = when (this) {
    PresentChance.NONE -> colors.gray400
    PresentChance.VERY_LOW -> colors.sub300
    PresentChance.LOW -> colors.sub200
    PresentChance.MEDIUM -> colors.primary100
    PresentChance.HIGH -> colors.primary500
    PresentChance.VERY_HIGH -> colors.sub400
}

private fun DayType.toStyle(colors: HapHapColors): DayItemStyle = when (this) {
    DayType.OutMonth -> DayItemStyle(
        textColor = colors.gray200,
        chanceColor = Color.Transparent,
    )
    is DayType.InMonth -> DayItemStyle(
        textColor = if (isSelected) colors.primary100 else colors.gray700,
        chanceColor = presentChance.toColor(colors),
    )
}

@Composable
fun CalendarDayItem(
    day: LocalDate,
    dayType: DayType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val style = dayType.toStyle(HapHapTheme.colors)

    Column(
        modifier = modifier
            .padding(top = 3.dp)
            .noRippleClickable(
                onClick = onClick,
                isEnabled = dayType is DayType.InMonth,
            )
            .aspectRatio(48f/50f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            style = HapHapTheme.typography.body.sb13,
            color = style.textColor,
        )

        if (dayType is DayType.InMonth) {
            if (dayType.isToday) {
                Spacer(modifier = Modifier.height(2.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_today),
                    contentDescription = null,
                    tint = style.chanceColor,
                    modifier = Modifier
                        .padding(horizontal = 11.dp)
                        .size(26.dp),
                )
            } else {
                Spacer(modifier = Modifier.height(5.dp))

                if (dayType.presentChance != PresentChance.NONE) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 13.dp)
                            .height(6.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = style.chanceColor)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarDayItemPreview() {
    HapHapTheme {
        Row {
            CalendarDayItem(
                day = LocalDate.of(2024, 1, 1),
                dayType = DayType.OutMonth,
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            CalendarDayItem(
                day = LocalDate.of(2024, 1, 2),
                dayType = DayType.InMonth(),
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            CalendarDayItem(
                day = LocalDate.of(2024, 1, 3),
                dayType = DayType.InMonth(presentChance = PresentChance.LOW, isToday = true),
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            CalendarDayItem(
                day = LocalDate.of(2024, 1, 4),
                dayType = DayType.InMonth(isSelected = true),
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            CalendarDayItem(
                day = LocalDate.of(2024, 1, 5),
                dayType = DayType.InMonth(presentChance = PresentChance.HIGH),
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            CalendarDayItem(
                day = LocalDate.of(2024, 1, 6),
                dayType = DayType.InMonth(presentChance = PresentChance.VERY_LOW),
                onClick = {},
                modifier = Modifier.weight(1f)
            )
            CalendarDayItem(
                day = LocalDate.of(2024, 1, 7),
                dayType = DayType.InMonth(presentChance = PresentChance.VERY_HIGH, isSelected = true),
                onClick = {},
                modifier = Modifier.weight(1f)
            )
        }
    }
}
