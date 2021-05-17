package com.absaweatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [LocationTable::class], version = 1, exportSchema = false)
abstract class LocationsDatabase : RoomDatabase() {
    abstract fun locationsDao() : LocationsDao

    companion object{
        @Volatile
        private var instance : LocationsDatabase?= null

        @Synchronized
        fun getInstance(context : Context) : LocationsDatabase?{
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, LocationsDatabase::class.java, "weather_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()
            }
            return instance
        }

        private val callback : Callback = object : Callback() {
            override fun onCreate(db : SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }
    }
}