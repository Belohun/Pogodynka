package com.example.pogodynka.database

import androidx.lifecycle.LiveData
import androidx.room.Dao

class ApplicationRepository(private val dao:CityDao) {
    val allCities: LiveData<List<City>> = dao.selectAllCitys()
    suspend fun insert(city: City) {
        dao.insert(city)
    }

    suspend fun delete(city: City) {
        dao.delete(city)
    }
}