package com.kamel.findcity.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.kamel.findcity.R

@Composable
fun headline(): TextStyle {
    return TextStyle(
        fontSize = 22.sp,
        lineHeight = 34.5.sp,
        fontFamily = FontFamily(Font(R.font.roboto_bold)),
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun titleLarge(): TextStyle {
    return TextStyle(
        fontSize = 18.sp,
        lineHeight = 20.sp,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun titleMedium(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.W400,
        lineHeight = 22.04.sp,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun body(): TextStyle {
    return TextStyle(
        fontSize = 14.sp,
        lineHeight = 22.43.sp,
        fontFamily = FontFamily(Font(R.font.roboto_medium)),
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun caption(): TextStyle {
    return TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 14.5.sp,
    )
}