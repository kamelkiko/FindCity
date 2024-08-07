package com.kamel.findcity.data.local

import android.content.Context
import com.kamel.findcity.data.local.model.CityDto
import com.kamel.findcity.data.repository.local.LocalDataSource
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStreamReader
import javax.inject.Inject

class JsonFileLocalDataSource @Inject constructor(private val context: Context) : LocalDataSource {
    override suspend fun getAllCities(): List<CityDto> {
        val inputStream = context.assets.open("cities.json")
        val reader = InputStreamReader(inputStream)
        return Json.decodeFromString<List<CityDto>>(reader.readText())
    }
}