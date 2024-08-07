package com.kamel.findcity.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamel.findcity.domain.entity.City
import com.kamel.findcity.domain.usecase.GetAllCitiesUseCase
import com.kamel.findcity.domain.usecase.SearchCitiesByPrefixUseCase
import com.kamel.findcity.domain.util.JsonFileNotFoundException
import com.kamel.findcity.domain.util.NotFoundException
import com.kamel.findcity.domain.util.UnknownErrorException
import com.kamel.findcity.ui.screen.util.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
        _state.update {
            it.copy(
                isLoading = true,
                error = ""
            )
        }
        tryToExecute(
            function = getAllCities::invoke,
            onSuccess = ::onFetchAllCitiesSuccess,
            onError = ::handleError
        )
    }

    private fun onFetchAllCitiesSuccess(cities: List<City>) {
        _state.update {
            it.copy(
                isLoading = false,
                error = "",
                cities = cities.map { city -> city.toUiState() }
            )
        }
    }

    fun searchForCityByName(name: String) {
        _state.update {
            it.copy(
                isLoading = true,
                error = ""
            )
        }
        tryToExecute(
            function = { searchCities.invoke(name) },
            onSuccess = ::onSearchForCityByNameSuccess,
            onError = ::handleError
        )
    }

    private fun onSearchForCityByNameSuccess(cities: List<City>) {
        _state.update {
            it.copy(
                isLoading = false,
                error = "",
                cities = cities.map { city -> city.toUiState() }
            )
        }
    }

    private fun handleError(error: ErrorState) {
        when (error) {
            is ErrorState.JsonFileNotFound -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message.toString()
                    )
                }
            }

            is ErrorState.NotFound -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message.toString()
                    )
                }
            }

            is ErrorState.UnknownError -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message.toString()
                    )
                }
            }
        }
    }

    private fun <T> tryToExecute(
        function: suspend () -> T,
        onSuccess: (T) -> Unit,
        onError: (ErrorState) -> Unit,
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
    ): Job {
        return viewModelScope.launch(dispatcher) {
            try {
                val result = function()
                onSuccess(result)
            } catch (exception: NotFoundException) {
                onError(ErrorState.NotFound(exception.message))
            } catch (exception: JsonFileNotFoundException) {
                onError(ErrorState.JsonFileNotFound(exception.message))
            } catch (exception: UnknownErrorException) {
                onError(ErrorState.UnknownError(exception.message))
            } catch (exception: Exception) {
                onError(ErrorState.UnknownError(exception.message))
            }
        }
    }
}