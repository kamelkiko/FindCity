package com.kamel.findcity.data.local.mapper

import com.kamel.findcity.data.local.model.CityDto
import com.kamel.findcity.data.local.model.CoordinateDto
import com.kamel.findcity.domain.entity.City
import com.kamel.findcity.domain.entity.Coordinate

fun CityDto.toEntity(): City {
    return City(
        name = name ?: "",
        country = country ?: "",
        id = id ?: 0,
        coordinate = coordinate?.toEntity() ?: Coordinate(0.0, 0.0),
    )
}


fun CoordinateDto.toEntity(): Coordinate {
    return Coordinate(
        lat = lat ?: 0.0,
        lon = lon ?: 0.0,
    )
}