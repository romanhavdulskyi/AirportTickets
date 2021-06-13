package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun insertCity(city: City)
    suspend fun insertCities(cities: List<City>)

    suspend fun updateCity(city: City)
    suspend fun updateCities(cities: List<City>)

    suspend fun deleteCity(city: City)
    suspend fun deleteCities(cities: List<City>)

    suspend fun observeCities(): Flow<List<City>>
}