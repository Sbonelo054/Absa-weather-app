package com.absaweatherapp.networking

import com.absaweatherapp.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("forecast")
    suspend fun getWeather(@Query("q") place: String?, @Query("units")units:String, @Query("appid") key: String): Weather

}