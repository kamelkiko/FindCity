package com.kamel.findcity.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamel.findcity.domain.entity.City
import com.kamel.findcity.domain.usecase.GetAllCitiesUseCase
import com.kamel.findcity.domain.usecase.SearchCitiesByPrefixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val getAllCities: GetAllCitiesUseCase,
    private val searchCities: SearchCitiesByPrefixUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CityUiState())
    val state = _state.asStateFlow()

    init {
        fetchAllCities()
    }

    private fun fetchAllCities() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                startLoading()
                val cities = getAllCities.invoke()
                stopLoadingWithData(cities)
            } catch (e: Exception) {
                showError(e.message.toString())
            }
        }
    }

    fun searchForCityByName(name: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                startLoading()
                val cities = searchCities.invoke(name)
                stopLoadingWithData(cities)
            } catch (e: Exception) {
                showError(e.message.toString())
            }
        }
    }

    private fun startLoading() {
        _state.update {
            it.copy(
                isLoading = true,
                error = ""
            )
        }
    }

    private fun stopLoadingWithData(cities: List<City>) {
        _state.update {
            it.copy(
                isLoading = false,
                error = "",
                cities = cities.map { city -> city.toUiState() }
            )
        }
    }

    private fun showError(errorMessage: String) {
        _state.update {
            it.copy(
                isLoading = false,
                error = errorMessage
            )
        }
    }
}