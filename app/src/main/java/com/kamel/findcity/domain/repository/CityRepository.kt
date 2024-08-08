package com.kamel.findcity.domain.repository

import com.kamel.findcity.domain.entity.City

interface CityRepository {
    suspend fun getAllCities(): List<City>
    suspend fun searchCityByPrefix(prefix: String): List<City>
}