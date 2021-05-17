package com.absaweatherapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.absaweatherapp.database.LocationTable
import com.absaweatherapp.database.LocationsDao
import com.absaweatherapp.database.LocationsDatabase

class LocationsRepositoryImpl(application : Application) : LocationsRepository {
    private lateinit var dao : LocationsDao
    init {
        val database = LocationsDatabase.getInstance(application)
        if (database != null) {
            dao = database.locationsDao()
        }
    }

    override suspend fun saveLocation(locationTable : LocationTable) {
        dao.saveLocation(locationTable)
    }

    override fun getHistory() : LiveData<List<LocationTable>>? {
        return dao.getHistory()
    }
}