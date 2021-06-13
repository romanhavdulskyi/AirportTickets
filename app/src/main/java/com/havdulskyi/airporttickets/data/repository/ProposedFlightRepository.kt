package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.entity.ProposedFlightEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ProposedFlightRepository {

    suspend fun insertProposedFlight(proposedFlightEntity: ProposedFlightEntity)
    suspend fun insertProposedFlights(proposedFlightEntities: List<ProposedFlightEntity>)

    suspend fun updateProposedFlight(proposedFlightEntity: ProposedFlightEntity)
    suspend fun updateProposedFlights(proposedFlightEntities: List<ProposedFlightEntity>)

    suspend fun deleteProposedFlight(proposedFlightEntity: ProposedFlightEntity)
    suspend fun deleteProposedFlights(proposedFlightEntities: List<ProposedFlightEntity>)

    suspend fun observeProposedFlights(): Flow<List<ProposedFlightEntity>>

    suspend fun observeProposedFlights(query : String, departureDate : LocalDate): Flow<List<ProposedFlightEntity>>
}