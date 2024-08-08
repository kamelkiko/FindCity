package com.kamel.findcity.data.local.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val country: String? = null,
    val name: String? = null,
    @SerialName("_id")
    val id: Int? = null,
    @SerialName("coord")
    val coordinate: CoordinateDto? = null,
)