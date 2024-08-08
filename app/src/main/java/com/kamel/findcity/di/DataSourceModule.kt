package com.kamel.findcity.di

import com.kamel.findcity.data.local.JsonFileLocalDataSource
import com.kamel.findcity.data.repository.local.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindJsonFileLocalDataSource(
        jsonFileLocalDataSource: JsonFileLocalDataSource
    ): LocalDataSource

}