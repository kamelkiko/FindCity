package com.kamel.findcity.data.repository

import com.kamel.findcity.data.local.mapper.toEntity
import com.kamel.findcity.data.repository.local.LocalDataSource
import com.kamel.findcity.domain.entity.City
import com.kamel.findcity.domain.repository.CityRepository
import com.kamel.findcity.domain.trie.TrieCity
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val trieCity: TrieCity,
) : CityRepository {
    override suspend fun getAllCities(): List<City> {
        return localDataSource.getAllCities().map { it.toEntity() }.onEach { city ->
            insertCityToTrie(city)
        }
    }

    private suspend fun insertCityToTrie(city: City) {
        trieCity.insert(city.name.lowercase(), city)
    }

    override suspend fun searchCityByPrefix(prefix: String): List<City> {
        return trieCity.search(prefix.lowercase())
    }
}