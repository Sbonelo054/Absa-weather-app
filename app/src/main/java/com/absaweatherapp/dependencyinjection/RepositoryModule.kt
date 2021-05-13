package com.absaweatherapp.dependencyinjection

import com.absaweatherapp.repository.LocationsRepository
import com.absaweatherapp.repository.LocationsRepositoryImpl
import com.absaweatherapp.repository.WeatherRepository
import com.absaweatherapp.repository.WeatherRepositoryImpl
import org.koin.dsl.module

val repoModule = module{
    single<WeatherRepository> {
        WeatherRepositoryImpl()
    }

    single<LocationsRepository> {
        LocationsRepositoryImpl(get())
    }
}