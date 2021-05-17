package com.absaweatherapp.repository

import com.absaweatherapp.model.Weather
import com.absaweatherapp.model.WeatherResults

interface WeatherRepository {

    suspend fun getWeather(place : String, unit : String) : WeatherResults<Weather>
}