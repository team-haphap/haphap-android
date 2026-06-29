package com.haphap.app.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

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
    white = White,
    black = Black,
)
