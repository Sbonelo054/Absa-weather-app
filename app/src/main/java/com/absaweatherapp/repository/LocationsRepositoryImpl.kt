package com.absaweatherapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.absaweatherapp.database.LocationTable
import com.absaweatherapp.database.WeatherDao
import com.absaweatherapp.database.WeatherDatabase

class LocationsRepositoryImpl(application : Application) : LocationsRepository {
    private lateinit var dao : WeatherDao
    init {
        val database = WeatherDatabase.getInstance(application)
        if (database != null) {
            dao = database.dao()
        }
    }

    override suspend fun saveLocation(locationTable : LocationTable) {
        dao.saveLocation(locationTable)
    }

    override fun getLocations() : LiveData<List<LocationTable>>? {
        return dao.getLocations()
    }
}