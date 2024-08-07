package com.kamel.findcity.data.local.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val country: String,
    val name: String,
    @SerialName("_id")
    val id: Int,
    @SerialName("coord")
    val coordinate: CoordinateDto
)