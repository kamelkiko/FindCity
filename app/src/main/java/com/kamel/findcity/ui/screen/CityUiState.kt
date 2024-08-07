package com.kamel.findcity.ui.screen

import androidx.compose.runtime.Immutable
import com.kamel.findcity.domain.entity.City

@Immutable
data class CityUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val cities: List<CityDetailUiState> = emptyList(),
    val prefix: String = "",
)

@Immutable
data class CityDetailUiState(
    val country: String = "",
    val name: String = "",
    val id: Int = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
)

fun City.toUiState() = CityDetailUiState(
    country = country,
    name = name,
    id = id,
    lat = coordinate.lat,
    lon = coordinate.lon
)
