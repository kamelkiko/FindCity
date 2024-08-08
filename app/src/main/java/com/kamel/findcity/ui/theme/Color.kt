package com.kamel.findcity.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val background: Color,
    val contentPrimary: Color,
    val contentSecondary: Color,
    val contentTertiary: Color,
)

val LightColors = Colors(
    background = Color(0xFFFAFAFA),
    contentPrimary = Color(0xDE1F0000),
    contentSecondary = Color(0x991F0000),
    contentTertiary = Color(0x5E1F0000),
)

val DarkColors = Colors(
    background = Color(0xFF151515),
    contentPrimary = Color(0xDEFFFFFF),
    contentSecondary = Color(0x99FFFFFF),
    contentTertiary = Color(0x5EFFFFFF),
)