package com.haphap.app.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    lineHeightPercent: Float = 1.4f,
) = TextStyle(
    fontFamily = Pretendard,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = (size * lineHeightPercent).sp,
    letterSpacing = 0.sp,
)

@Immutable
data class TitleStyle(
    val b30: TextStyle = pretendard(FontWeight.Bold, 30),
    val b26: TextStyle = pretendard(FontWeight.Bold, 26),
)

@Immutable
data class SubtitleStyle(
    val b24: TextStyle = pretendard(FontWeight.Bold, 24, 1.3f),
    val sb24: TextStyle = pretendard(FontWeight.SemiBold, 24),
    val b22: TextStyle = pretendard(FontWeight.Bold, 22),
    val b20: TextStyle = pretendard(FontWeight.Bold, 20),
)

@Immutable
data class BodyStyle(
    val b18: TextStyle = pretendard(FontWeight.Bold, 18),
    val sb18: TextStyle = pretendard(FontWeight.SemiBold, 18),
    val m18: TextStyle = pretendard(FontWeight.Medium, 18),
    val r18: TextStyle = pretendard(FontWeight.Normal, 18),
    val sb16: TextStyle = pretendard(FontWeight.SemiBold, 16),
    val r16: TextStyle = pretendard(FontWeight.Normal, 16),
    val b14: TextStyle = pretendard(FontWeight.Bold, 14, 1.3f),
    val sb14: TextStyle = pretendard(FontWeight.SemiBold, 14),
    val m14: TextStyle = pretendard(FontWeight.Medium, 14),
    val r14: TextStyle = pretendard(FontWeight.Normal, 14),
    val sb13: TextStyle = pretendard(FontWeight.SemiBold, 13),
)

@Immutable
data class CaptionStyle(
    val b12: TextStyle = pretendard(FontWeight.Bold, 12),
    val sb12: TextStyle = pretendard(FontWeight.SemiBold, 12),
    val m12: TextStyle = pretendard(FontWeight.Medium, 12),
    val r12: TextStyle = pretendard(FontWeight.Normal, 12),
    val r11: TextStyle = pretendard(FontWeight.Normal, 11),
    val sb10: TextStyle = pretendard(FontWeight.SemiBold, 10),
    val r10: TextStyle = pretendard(FontWeight.Normal, 10),
)

@Immutable
data class HaphapTypography(
    val title: TitleStyle = TitleStyle(),
    val subtitle: SubtitleStyle = SubtitleStyle(),
    val body: BodyStyle = BodyStyle(),
    val caption: CaptionStyle = CaptionStyle(),
)

val defaultHaphapTypography = HaphapTypography()