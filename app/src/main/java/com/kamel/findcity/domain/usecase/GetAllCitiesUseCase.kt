package com.kamel.findcity.domain.usecase

import com.kamel.findcity.domain.repository.CityRepository
import javax.inject.Inject

class GetAllCitiesUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke() = cityRepository.getAllCities()
}