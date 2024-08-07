package com.kamel.findcity.domain.util

open class FindCityException(message: String?) : Exception(message)

class NotFoundException(override val message: String?) : FindCityException(message)

class UnknownErrorException(override val message: String?) : FindCityException(message)

class JsonFileNotFoundException(override val message: String?) : FindCityException(message)