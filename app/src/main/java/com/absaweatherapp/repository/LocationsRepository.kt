package com.absaweatherapp.repository

import androidx.lifecycle.LiveData
import com.absaweatherapp.database.LocationTable

interface LocationsRepository {

    suspend fun saveLocation(locationTable : LocationTable)

    fun getHistory() : LiveData<List<LocationTable>>?
}