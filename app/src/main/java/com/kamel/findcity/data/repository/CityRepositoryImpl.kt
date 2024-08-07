package com.kamel.findcity.data.repository

import com.kamel.findcity.data.local.mapper.toEntity
import com.kamel.findcity.data.repository.local.LocalDataSource
import com.kamel.findcity.domain.entity.City
import com.kamel.findcity.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
) : CityRepository {
    override suspend fun getAllCities(): List<City> {
        return localDataSource.getAllCities().map { it.toEntity() }
    }

}