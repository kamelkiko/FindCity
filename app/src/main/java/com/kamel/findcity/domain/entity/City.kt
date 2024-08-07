package com.kamel.findcity.domain.entity

data class City(
    val country: String,
    val name: String,
    val id: Int,
    val coordinate: Coordinate
)