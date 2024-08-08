package com.kamel.findcity.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kamel.findcity.R
import com.kamel.findcity.ui.composable.ErrorView
import com.kamel.findcity.ui.composable.LoadingView
import com.kamel.findcity.ui.composable.SetLayoutDirection
import com.kamel.findcity.ui.theme.Theme

@Composable
fun CityScreen(viewModel: CityViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    CityContent(state = state, viewModel::onPrefixChanged, viewModel::onClear)
}

@Composable
private fun CityContent(
    state: CityUiState,
    onPrefixChange: (String) -> Unit,
    onClearClick: () -> Unit = {}
) {
    val keyboard = LocalSoftwareKeyboardController.current

    SetLayoutDirection(layoutDirection = LayoutDirection.Ltr) {
        Box(
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .fillMaxSize()
                .background(Theme.colors.background),
        ) {
            AnimatedVisibility(visible = state.isLoading) {
                LoadingView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }

            AnimatedVisibility(visible = state.error.isNotEmpty() && !state.isNoMatchFoundSearch) {
                ErrorView(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    errorMessage = state.error,
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = stringResource(R.string.error)
                )
            }

            AnimatedVisibility(visible = state.isSuccess) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = state.prefix,
                        onValueChange = { newFilter ->
                            onPrefixChange(newFilter)
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Theme.colors.contentPrimary,
                            unfocusedBorderColor = Theme.colors.contentSecondary,
                            cursorColor = Theme.colors.contentPrimary,
                            focusedTextColor = Theme.colors.contentPrimary
                        ),
                        placeholder = {
                            Text(
                                stringResource(R.string.search_for_a_city),
                                style = Theme.typography.body,
                                color = Theme.colors.contentTertiary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboard?.hide() }
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search_outlined),
                                contentDescription = stringResource(
                                    R.string.search_icon
                                )
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.clear_icon),
                                modifier = Modifier.clickable {
                                    onClearClick()
                                    keyboard?.hide()
                                }
                            )
                        },
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AnimatedVisibility(visible = state.error.isNotEmpty() && state.isNoMatchFoundSearch) {
                        ErrorView(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            errorMessage = state.error,
                            painter = painterResource(id = R.drawable.search_error),
                            contentDescription = stringResource(R.string.search_not_found),
                        )
                    }

                    AnimatedVisibility(visible = state.cities.isNotEmpty()) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.filteredCity) { city ->
                                CityRow(city)
                            }
                        }
                    }
                }
            }
        }
    }
}

private const val GOOGLE_PACKAGE = "com.google.android.apps.maps"

@Composable
private fun CityRow(city: CityDetailUiState) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val gmmIntentUri =
                    Uri.parse("geo:${city.lat},${city.lon}?q=${city.name}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage(GOOGLE_PACKAGE)
                context.startActivity(mapIntent)
            }
            .padding(8.dp)
    ) {
        Text(
            "${city.name}, ${city.country}",
            style = Theme.typography.titleLarge,
            color = Theme.colors.contentPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            stringResource(R.string.coordinates, city.lat, city.lon),
            style = Theme.typography.caption,
            color = Theme.colors.contentSecondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
    //HorizontalDivider(thickness = 0.5.dp)
}