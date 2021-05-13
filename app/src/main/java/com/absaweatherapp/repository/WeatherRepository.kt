package com.absaweatherapp.repository

import com.absaweatherapp.model.Weather

interface WeatherRepository {

    suspend fun getWeather(place : String, metric : String) : Weather?
}