package com.haphap.app.core.designsystem.theme

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

val t_b_30: TextStyle = pretendard(FontWeight.Bold, 30)
val t_b_26: TextStyle = pretendard(FontWeight.Bold, 26)
val st_b_24: TextStyle = pretendard(FontWeight.Bold, 24, 1.3f)
val st_sb_24: TextStyle = pretendard(FontWeight.SemiBold, 24)
val st_b_22: TextStyle = pretendard(FontWeight.Bold, 22)
val st_b_20: TextStyle = pretendard(FontWeight.Bold, 20)
val b_b_18: TextStyle = pretendard(FontWeight.Bold, 18)
val b_sb_18: TextStyle = pretendard(FontWeight.SemiBold, 18)
val b_m_18: TextStyle = pretendard(FontWeight.Medium, 18)
val b_r_18: TextStyle = pretendard(FontWeight.Normal, 18)
val b_sb_16: TextStyle = pretendard(FontWeight.SemiBold, 16)
val b_r_16: TextStyle = pretendard(FontWeight.Normal, 16)
val b_b_14: TextStyle = pretendard(FontWeight.Bold, 14, 1.3f)
val b_sb_14: TextStyle = pretendard(FontWeight.SemiBold, 14)
val b_m_14: TextStyle = pretendard(FontWeight.Medium, 14)
val b_r_14: TextStyle = pretendard(FontWeight.Normal, 14)
val b_sb_13: TextStyle = pretendard(FontWeight.SemiBold, 13)
val c_b_12: TextStyle = pretendard(FontWeight.Bold, 12)
val c_sb_12: TextStyle = pretendard(FontWeight.SemiBold, 12)
val c_m_12: TextStyle = pretendard(FontWeight.Medium, 12)
val c_r_12: TextStyle = pretendard(FontWeight.Normal, 12)
val c_r_11: TextStyle = pretendard(FontWeight.Normal, 11)
val c_sb_10: TextStyle = pretendard(FontWeight.SemiBold, 10)
val c_r_10: TextStyle = pretendard(FontWeight.Normal, 10)