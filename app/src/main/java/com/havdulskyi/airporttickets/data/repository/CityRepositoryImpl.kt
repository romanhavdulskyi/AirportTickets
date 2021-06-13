package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.AppDatabase
import com.havdulskyi.airporttickets.data.model.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CityRepositoryImpl(private val appDatabase: AppDatabase) : CityRepository {
    override suspend fun insertCity(city: City) {
        withContext(Dispatchers.IO)
        {
            appDatabase.cityDao().insert(city)
        }
    }

    override suspend fun insertCities(cities: List<City>) {
        withContext(Dispatchers.IO)
        {
            appDatabase.cityDao().insertAll(cities)
        }
    }

    override suspend fun updateCity(city: City) {
        withContext(Dispatchers.IO)
        {
            appDatabase.cityDao().update(city)
        }
    }

    override suspend fun updateCities(cities: List<City>) {
        withContext(Dispatchers.IO)
        {
            appDatabase.cityDao().updateAll(cities)
        }
    }

    override suspend fun deleteCity(city: City) {
        withContext(Dispatchers.IO)
        {
            appDatabase.cityDao().delete(city)
        }
    }

    override suspend fun deleteCities(cities: List<City>) {
        withContext(Dispatchers.IO)
        {
            appDatabase.cityDao().deleteAll(cities)
        }
    }

    override suspend fun observeCities(): Flow<List<City>> {
        return appDatabase
            .cityDao()
            .cityFlow()
            .map { it.filterNotNull() }
            .flowOn(Dispatchers.IO)
    }
}