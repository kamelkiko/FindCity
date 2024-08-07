package com.kamel.findcity.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val localColorScheme = staticCompositionLocalOf { LightColors }
private val localTypography = staticCompositionLocalOf { Typography() }

@Composable
fun FindCityTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) DarkColors else LightColors

    val typography = Typography(
        headline = headline(),
        titleLarge = titleLarge(),
        title = title(),
        titleMedium = titleMedium(),
        body = body(),
        caption = caption()
    )

    CompositionLocalProvider(
        localColorScheme provides colorScheme,
        localTypography provides typography,
    ) {
        content()
    }
}

object Theme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = localColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = localTypography.current
}