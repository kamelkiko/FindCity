package com.kamel.findcity.ui.screen.util

sealed interface ErrorState {
    data class NotFound(val message: String?) : ErrorState

    data class UnknownError(val message: String?) : ErrorState

    data class JsonFileNotFound(val message: String?) : ErrorState
}