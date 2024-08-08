package com.kamel.findcity.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.kamel.findcity.ui.theme.Theme

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorMessage: String,
    painter: Painter,
    contentDescription: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .fillMaxWidth(0.55f)
                .padding(bottom = 16.dp)
        )
        Text(
            errorMessage,
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary
        )
    }
}