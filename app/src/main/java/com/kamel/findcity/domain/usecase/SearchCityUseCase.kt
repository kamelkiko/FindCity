package com.kamel.findcity.domain.usecase

import com.kamel.findcity.domain.entity.City
import com.kamel.findcity.domain.repository.CityRepository
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val cityRepository: CityRepository,
) {

    suspend operator fun invoke(prefix: String): List<City> {
        return cityRepository.searchCityByPrefix(prefix.lowercase()).sortedBy { it.name }
    }
}