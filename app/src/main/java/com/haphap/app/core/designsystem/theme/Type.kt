package com.haphap.app.core.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.haphap.app.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
)

private fun haphapTextStyle(
    weight: FontWeight,
    size: Int,
    lineHeight: TextUnit = 1.4.em,
) = TextStyle(
    fontFamily = Pretendard,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = lineHeight,
    letterSpacing = 0.em,
    platformStyle = PlatformTextStyle(includeFontPadding = false),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    ),
)

@Immutable
data class TitleStyle(
    val b30: TextStyle = haphapTextStyle(weight = FontWeight.Bold, size = 30),
    val b26: TextStyle = haphapTextStyle(weight = FontWeight.Bold, size = 26),
)

@Immutable
data class SubtitleStyle(
    val b24: TextStyle = haphapTextStyle(weight = FontWeight.Bold, size = 24, lineHeight = 1.3.em),
    val sb24: TextStyle = haphapTextStyle(weight = FontWeight.SemiBold, size = 24),
    val b22: TextStyle = haphapTextStyle(weight = FontWeight.Bold, size = 22),
    val b20: TextStyle = haphapTextStyle(weight = FontWeight.Bold, size = 20),
)

@Immutable
data class BodyStyle(
    val b18: TextStyle = haphapTextStyle(weight = FontWeight.Bold, size = 18),
    val sb18: TextStyle = haphapTextStyle(weight = FontWeight.SemiBold, size = 18),
    val m18: TextStyle = haphapTextStyle(weight = FontWeight.Medium, size = 18),
    val r18: TextStyle = haphapTextStyle(weight = FontWeight.Normal, size = 18),
    val sb16: TextStyle = haphapTextStyle(weight = FontWeight.SemiBold, size = 16),
    val r16: TextStyle = haphapTextStyle(weight = FontWeight.Normal, size = 16),
    val b14: TextStyle = haphapTextStyle(weight = FontWeight.Bold, size = 14, lineHeight = 1.3.em),
    val sb14: TextStyle = haphapTextStyle(weight = FontWeight.SemiBold, size = 14),
    val m14: TextStyle = haphapTextStyle(weight = FontWeight.Medium, size = 14),
    val r14: TextStyle = haphapTextStyle(weight = FontWeight.Normal, size = 14),
    val sb13: TextStyle = haphapTextStyle(weight = FontWeight.SemiBold, size = 13),
)

@Immutable
data class CaptionStyle(
    val b12: TextStyle = haphapTextStyle(weight = FontWeight.Bold, size = 12),
    val sb12: TextStyle = haphapTextStyle(weight = FontWeight.SemiBold, size = 12),
    val m12: TextStyle = haphapTextStyle(weight = FontWeight.Medium, size = 12),
    val r12: TextStyle = haphapTextStyle(weight = FontWeight.Normal, size = 12),
    val r11: TextStyle = haphapTextStyle(weight = FontWeight.Normal, size = 11),
    val sb10: TextStyle = haphapTextStyle(weight = FontWeight.SemiBold, size = 10, lineHeight= 1.3.em),
    val m10: TextStyle = haphapTextStyle(weight = FontWeight.Medium, size =10, lineHeight= 1.3.em),
    val r10: TextStyle = haphapTextStyle(weight = FontWeight.Normal, size = 10),
)

@Immutable
data class HapHapTypography(
    val title: TitleStyle = TitleStyle(),
    val subtitle: SubtitleStyle = SubtitleStyle(),
    val body: BodyStyle = BodyStyle(),
    val caption: CaptionStyle = CaptionStyle(),
)

val defaultHapHapTypography = HapHapTypography()

@Preview(showBackground = true)
@Composable
private fun TypographyPreview() {
    HapHapTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Title b30", style = HapHapTheme.typography.title.b30)
            Text("Title b26", style = HapHapTheme.typography.title.b26)
            Text("Subtitle b24", style = HapHapTheme.typography.subtitle.b24)
            Text("Subtitle sb24", style = HapHapTheme.typography.subtitle.sb24)
            Text("Subtitle b22", style = HapHapTheme.typography.subtitle.b22)
            Text("Subtitle b20", style = HapHapTheme.typography.subtitle.b20)
            Text("Body b18", style = HapHapTheme.typography.body.b18)
            Text("Body r16", style = HapHapTheme.typography.body.r16)
            Text("Body b14", style = HapHapTheme.typography.body.b14)
            Text("Body sb13", style = HapHapTheme.typography.body.sb13)
            Text("Caption b12", style = HapHapTheme.typography.caption.b12)
            Text("Caption r11", style = HapHapTheme.typography.caption.r11)
            Text("Caption sb10", style = HapHapTheme.typography.caption.sb10)
            Text("Caption m10", style = HapHapTheme.typography.caption.m10)
            Text("Caption r10", style = HapHapTheme.typography.caption.r10)
        }
    }
}