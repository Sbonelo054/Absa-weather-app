package com.absaweatherapp.repository

import com.absaweatherapp.model.Weather
import com.absaweatherapp.model.WeatherResults
import com.absaweatherapp.networking.API
import com.absaweatherapp.networking.Client
import com.absaweatherapp.utils.Constants

class WeatherRepositoryImpl : WeatherRepository {
    private lateinit var api: API

    override suspend fun getWeather(place : String, unit : String) : WeatherResults<Weather> {
        api = Client.instance.create(API::class.java)
        return try {
            val results = api.getWeather(place,unit, Constants.APP_ID)
            WeatherResults.SuccessResults(results)
        } catch (t : Throwable) {
            WeatherResults.Error(t)
        }
    }
}