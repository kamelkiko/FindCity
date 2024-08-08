package com.kamel.findcity.domain.usecase

import com.kamel.findcity.domain.ds.TrieCity
import com.kamel.findcity.domain.entity.City
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val trieCity: TrieCity,
) {
   suspend fun insertCitiesToTrie(cities: List<City>) {
        cities.forEach { city ->
            trieCity.insert(city.name.lowercase(), city)
        }
    }

    suspend fun searchCityByPrefix(prefix: String): List<City> {
        return trieCity.search(prefix.lowercase()).sortedBy { it.name }
    }
}