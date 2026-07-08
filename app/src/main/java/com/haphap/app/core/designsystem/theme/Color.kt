package com.haphap.app.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Primary500 = Color(0xFF0065FF)
val Primary100 = Color(0xFF4A8EFF)

val Sub100 = Color(0xFFE7F1FF)
val Sub200 = Color(0xFF82B1FF)
val Sub300 = Color(0xFFC3D9FF)
val Sub400 = Color(0xFF041B50)
val Yellow = Color(0xFFFEE500)

val Gray50 = Color(0xFFFAFBFB)
val Gray100 = Color(0xFFF3F4F7)
val Gray200 = Color(0xFFD2D6DB)
val Gray300 = Color(0xFFB2B8C0)
val Gray400 = Color(0xFF8E95A0)
val Gray500 = Color(0xFF6D7683)
val Gray600 = Color(0xFF505867)
val Gray700 = Color(0xFF181E27)
val Gray800 = Color(0xFF141920)
val Gray900 = Color(0xFF111214)
val GrayBg = Color(0x33000000)

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

@Immutable
data class HapHapColors(
    val primary500: Color,
    val primary100: Color,
    val sub100: Color,
    val sub200: Color,
    val sub300: Color,
    val sub400: Color,
    val yellow: Color,
    val gray50: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray700: Color,
    val gray800: Color,
    val gray900: Color,
    val grayBg: Color,
    val white: Color,
    val black: Color,
)

val defaultHapHapColors = HapHapColors(
    primary500 = Primary500,
    primary100 = Primary100,
    sub100 = Sub100,
    sub200 = Sub200,
    sub300 = Sub300,
    sub400 = Sub400,
    yellow = Yellow,
    gray50 = Gray50,
    gray100 = Gray100,
    gray200 = Gray200,
    gray300 = Gray300,
    gray400 = Gray400,
    gray500 = Gray500,
    gray600 = Gray600,
    gray700 = Gray700,
    gray800 = Gray800,
    gray900 = Gray900,
    grayBg = GrayBg,
    white = White,
    black = Black,
)

@Preview(showBackground = true)
@Composable
private fun ColorPreview() {
    HapHapTheme {
        Column {
            listOf(
                HapHapTheme.colors.primary500,
                HapHapTheme.colors.primary100,

                HapHapTheme.colors.sub100,
                HapHapTheme.colors.sub200,
                HapHapTheme.colors.sub300,
                HapHapTheme.colors.sub400,
                HapHapTheme.colors.yellow,

                HapHapTheme.colors.gray50,
                HapHapTheme.colors.gray100,
                HapHapTheme.colors.gray200,
                HapHapTheme.colors.gray300,
                HapHapTheme.colors.gray400,
                HapHapTheme.colors.gray500,
                HapHapTheme.colors.gray600,
                HapHapTheme.colors.gray700,
                HapHapTheme.colors.gray800,
                HapHapTheme.colors.gray900,

                HapHapTheme.colors.grayBg,

                HapHapTheme.colors.white,
                HapHapTheme.colors.black,
            ).chunked(6).forEach { rowColors ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    rowColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(color),
                        )
                    }
                }
            }
        }
    }
}