package com.haphap.app.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class HaphapTypography(
    val t_b_30: TextStyle,
    val t_b_26: TextStyle,
    val st_b_24: TextStyle,
    val st_sb_24: TextStyle,
    val st_b_22: TextStyle,
    val st_b_20: TextStyle,
    val b_b_18: TextStyle,
    val b_sb_18: TextStyle,
    val b_m_18: TextStyle,
    val b_r_18: TextStyle,
    val b_sb_16: TextStyle,
    val b_r_16: TextStyle,
    val b_b_14: TextStyle,
    val b_sb_14: TextStyle,
    val b_m_14: TextStyle,
    val b_r_14: TextStyle,
    val b_sb_13: TextStyle,
    val c_b_12: TextStyle,
    val c_sb_12: TextStyle,
    val c_m_12: TextStyle,
    val c_r_12: TextStyle,
    val c_r_11: TextStyle,
    val c_sb_10: TextStyle,
    val c_r_10: TextStyle,
)

private val defaultHaphapTypography = HaphapTypography(
    t_b_30 = t_b_30,
    t_b_26 = t_b_26,
    st_b_24 = st_b_24,
    st_sb_24 = st_sb_24,
    st_b_22 = st_b_22,
    st_b_20 = st_b_20,
    b_b_18 = b_b_18,
    b_sb_18 = b_sb_18,
    b_m_18 = b_m_18,
    b_r_18 = b_r_18,
    b_sb_16 = b_sb_16,
    b_r_16 = b_r_16,
    b_b_14 = b_b_14,
    b_sb_14 = b_sb_14,
    b_m_14 = b_m_14,
    b_r_14 = b_r_14,
    b_sb_13 = b_sb_13,
    c_b_12 = c_b_12,
    c_sb_12 = c_sb_12,
    c_m_12 = c_m_12,
    c_r_12 = c_r_12,
    c_r_11 = c_r_11,
    c_sb_10 = c_sb_10,
    c_r_10 = c_r_10,
)

private val LocalHaphapColors = staticCompositionLocalOf { defaultHaphapColors }
private val LocalHaphapTypography = staticCompositionLocalOf { defaultHaphapTypography }

object HapHapTheme {
    val colors: HaphapColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHaphapColors.current

    val typography: HaphapTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHaphapTypography.current
}

@Composable
fun HapHapTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalHaphapColors provides defaultHaphapColors,
        LocalHaphapTypography provides defaultHaphapTypography,
    ) {
        MaterialTheme(content = content)
    }
}