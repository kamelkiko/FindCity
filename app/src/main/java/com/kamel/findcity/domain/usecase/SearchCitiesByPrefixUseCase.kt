package com.kamel.findcity.domain.usecase

import com.kamel.findcity.domain.repository.CityRepository
import javax.inject.Inject

class SearchCitiesByPrefixUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    operator fun invoke(prefix: String) = cityRepository.searchCityByPrefix(prefix)
}