package com.absaweatherapp.dependencyinjection

import com.absaweatherapp.viewmodel.LocationsViewModel
import com.absaweatherapp.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel {
        WeatherViewModel(get())
    }

    viewModel {
        LocationsViewModel(get())
    }
}