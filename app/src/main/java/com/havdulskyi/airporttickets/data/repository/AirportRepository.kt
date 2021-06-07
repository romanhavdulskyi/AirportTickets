package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.entity.AirportEntity
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    suspend fun insertAirport(airport : AirportEntity)
    suspend fun insertAirports(airports : List<AirportEntity>)

    suspend fun updateAirport(airport : AirportEntity)
    suspend fun updateAirports(airport : List<AirportEntity>)

    suspend fun deleteAirport(airport : AirportEntity)
    suspend fun deleteAirports(airport : List<AirportEntity>)

    suspend fun observeAirports() : Flow<List<AirportEntity>>
}