package com.kamel.findcity.ui.screen

import androidx.lifecycle.ViewModel
import com.kamel.findcity.domain.usecase.GetAllCitiesUseCase
import com.kamel.findcity.domain.usecase.SearchCitiesByPrefixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getAllCities: GetAllCitiesUseCase,
    private val searchCities: SearchCitiesByPrefixUseCase,
) : ViewModel() {

}