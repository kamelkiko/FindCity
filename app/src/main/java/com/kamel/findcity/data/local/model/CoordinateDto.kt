package com.kamel.findcity.data.local.model

import kotlinx.serialization.Serializable

@Serializable
data class CoordinateDto(
    val lon: Double,
    val lat: Double
)