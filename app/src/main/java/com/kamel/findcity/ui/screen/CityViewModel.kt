package com.kamel.findcity.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamel.findcity.domain.entity.City
import com.kamel.findcity.domain.usecase.GetAllCitiesUseCase
import com.kamel.findcity.domain.usecase.SearchCityUseCase
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
    private val searchCity: SearchCityUseCase,
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
        viewModelScope.launch(Dispatchers.Default) {
            searchCity.insertCitiesToTrie(cities)
            _state.update {
                it.copy(
                    isLoading = false,
                    isSuccess = true,
                    error = "",
                    cities = cities.map { city -> city.toUiState() },
                    filteredCity = cities.map { city -> city.toUiState() }
                )
            }
        }
    }

    fun onPrefixChanged(prefix: String) {
        _state.update { it.copy(prefix = prefix) }
        if (prefix.isEmpty()) _state.update {
            it.copy(
                filteredCity = state.value.cities,
                isNoMatchFoundSearch = false,
                error = "",
                isSuccess = true
            )
        }
        else searchForCityByName(prefix)
    }

    fun onClear() {
        _state.update {
            it.copy(
                prefix = "",
                filteredCity = state.value.cities,
                isNoMatchFoundSearch = false,
                isSuccess = true,
                error = ""
            )
        }
    }

    private fun searchForCityByName(name: String) {
        _state.update {
            it.copy(
                error = ""
            )
        }
        tryToExecute(
            function = { searchCity.searchCityByPrefix(name) },
            onSuccess = ::onSearchForCityByNameSuccess,
            onError = ::handleError
        )
    }

    private fun onSearchForCityByNameSuccess(cities: List<City>) {
        _state.update {
            it.copy(
                isLoading = false,
                error = "",
                filteredCity = cities.map { city ->
                    city.toUiState()
                },
                isNoMatchFoundSearch = false
            )
        }
    }

    private fun handleError(error: ErrorState) {
        when (error) {
            is ErrorState.JsonFileNotFound -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message.toString(),
                        isNoMatchFoundSearch = false,
                        isSuccess = false
                    )
                }
            }

            is ErrorState.NotFound -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message.toString(),
                        isNoMatchFoundSearch = true
                    )
                }
            }

            is ErrorState.UnknownError -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message.toString(),
                        isNoMatchFoundSearch = false,
                        isSuccess = false
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