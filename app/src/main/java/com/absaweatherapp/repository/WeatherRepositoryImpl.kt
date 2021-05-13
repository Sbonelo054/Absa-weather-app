package com.absaweatherapp.repository

import com.absaweatherapp.model.Weather
import com.absaweatherapp.networking.API
import com.absaweatherapp.networking.Client
import com.absaweatherapp.utils.Constants

class WeatherRepositoryImpl : WeatherRepository {
    private lateinit var api: API

    override suspend fun getWeather(place : String, metric : String) : Weather? {
        api = Client.instance.create(API::class.java)
        return try {
            val results = api.getWeather(place,metric, Constants.appId)
            results
        } catch (e : Exception) {
            null
        }
    }

}