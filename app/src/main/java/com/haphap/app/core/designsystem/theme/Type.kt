package com.haphap.app.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.haphap.app.R

val Pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
)

private fun pretendard(
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
    val b30: TextStyle = pretendard(weight = FontWeight.Bold, size = 30),
    val b26: TextStyle = pretendard(weight = FontWeight.Bold, size = 26),
)

@Immutable
data class SubtitleStyle(
    val b24: TextStyle = pretendard(weight = FontWeight.Bold, size = 24, lineHeight = 1.3.em),
    val sb24: TextStyle = pretendard(weight = FontWeight.SemiBold, size = 24),
    val b22: TextStyle = pretendard(weight = FontWeight.Bold, size = 22),
    val b20: TextStyle = pretendard(weight = FontWeight.Bold, size = 20),
)

@Immutable
data class BodyStyle(
    val b18: TextStyle = pretendard(weight = FontWeight.Bold, size = 18),
    val sb18: TextStyle = pretendard(weight = FontWeight.SemiBold, size = 18),
    val m18: TextStyle = pretendard(weight = FontWeight.Medium, size = 18),
    val r18: TextStyle = pretendard(weight = FontWeight.Normal, size = 18),
    val sb16: TextStyle = pretendard(weight = FontWeight.SemiBold, size = 16),
    val r16: TextStyle = pretendard(weight = FontWeight.Normal, size = 16),
    val b14: TextStyle = pretendard(weight = FontWeight.Bold, size = 14, lineHeight = 1.3.em),
    val sb14: TextStyle = pretendard(weight = FontWeight.SemiBold, size = 14),
    val m14: TextStyle = pretendard(weight = FontWeight.Medium, size = 14),
    val r14: TextStyle = pretendard(weight = FontWeight.Normal, size = 14),
    val sb13: TextStyle = pretendard(weight = FontWeight.SemiBold, size = 13),
)

@Immutable
data class CaptionStyle(
    val b12: TextStyle = pretendard(weight = FontWeight.Bold, size = 12),
    val sb12: TextStyle = pretendard(weight = FontWeight.SemiBold, size = 12),
    val m12: TextStyle = pretendard(weight = FontWeight.Medium, size = 12),
    val r12: TextStyle = pretendard(weight = FontWeight.Normal, size = 12),
    val r11: TextStyle = pretendard(weight = FontWeight.Normal, size = 11),
    val sb10: TextStyle = pretendard(weight = FontWeight.SemiBold, size = 10, lineHeight= 1.3.em),
    val r10: TextStyle = pretendard(weight = FontWeight.Normal, size = 10),
)

@Immutable
data class HapHapTypography(
    val title: TitleStyle = TitleStyle(),
    val subtitle: SubtitleStyle = SubtitleStyle(),
    val body: BodyStyle = BodyStyle(),
    val caption: CaptionStyle = CaptionStyle(),
)

val defaultHapHapTypography = HapHapTypography()