package com.kamel.findcity.data.repository.local

import com.kamel.findcity.data.local.model.CityDto

interface LocalDataSource {
    suspend fun getAllCities(): List<CityDto>
}