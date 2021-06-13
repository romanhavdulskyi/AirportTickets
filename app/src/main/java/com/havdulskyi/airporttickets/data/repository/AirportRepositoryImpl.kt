package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.AppDatabase
import com.havdulskyi.airporttickets.data.entity.AirportEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AirportRepositoryImpl(private val appDatabase: AppDatabase) : AirportRepository {
    override suspend fun insertAirport(airport: AirportEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase.airportDao().insert(Mapping.mapEntityToModel(airport))
        }
    }

    override suspend fun insertAirports(airports: List<AirportEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase.airportDao().insertAll(airports.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun updateAirport(airport: AirportEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase.airportDao().update(Mapping.mapEntityToModel(airport))
        }
    }

    override suspend fun updateAirports(airport: List<AirportEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase.airportDao().updateAll(airport.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun deleteAirport(airport: AirportEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase.airportDao().delete(Mapping.mapEntityToModel(airport))
        }
    }

    override suspend fun deleteAirports(airport: List<AirportEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase.airportDao().deleteAll(airport.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun observeAirports(): Flow<List<AirportEntity>> {
        return appDatabase
            .airportDao()
            .airportFlow()
            .flatMapLatest { airports ->
                flowOf(
                    airports
                        .filterNotNull()
                        .mapNotNull { airport ->
                            DbUtils.fetchAirportEntity(appDatabase, airport)
                        })
            }.flowOn(Dispatchers.IO)
    }


}