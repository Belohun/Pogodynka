package com.example.pogodynka

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pogodynka.database.ApplicationRepository
import com.example.pogodynka.database.City
import com.example.pogodynka.database.CityDatabase
import kotlinx.coroutines.launch

class MainAcivityViewModel (application: Application): AndroidViewModel(application){
    private val repository: ApplicationRepository
    val allCities : LiveData<List<City>>
    init {
        val dao = CityDatabase.getDatabase(application).cityDao()
        repository  = ApplicationRepository(dao)
        allCities = repository.allCities

    }
    fun insert ( city: City){
        viewModelScope.launch {
            repository.insert(city)
        }
    }
}