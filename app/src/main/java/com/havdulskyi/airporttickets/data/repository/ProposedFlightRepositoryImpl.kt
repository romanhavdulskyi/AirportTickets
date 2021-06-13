package com.havdulskyi.airporttickets.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.havdulskyi.airporttickets.data.AppDatabase
import com.havdulskyi.airporttickets.data.entity.ProposedFlightEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class ProposedFlightRepositoryImpl(private val appDatabase: AppDatabase) :
    ProposedFlightRepository {

    override suspend fun insertProposedFlight(proposedFlightEntity: ProposedFlightEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .proposedFlightDao()
                .insert(Mapping.mapEntityToModel(proposedFlightEntity))
        }
    }

    override suspend fun insertProposedFlights(proposedFlightEntities: List<ProposedFlightEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .proposedFlightDao()
                .insertAll(proposedFlightEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun updateProposedFlight(proposedFlightEntity: ProposedFlightEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .proposedFlightDao()
                .update(Mapping.mapEntityToModel(proposedFlightEntity))
        }
    }

    override suspend fun updateProposedFlights(proposedFlightEntities: List<ProposedFlightEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .proposedFlightDao()
                .updateAll(proposedFlightEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun deleteProposedFlight(proposedFlightEntity: ProposedFlightEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .proposedFlightDao()
                .delete(Mapping.mapEntityToModel(proposedFlightEntity))
        }
    }

    override suspend fun deleteProposedFlights(proposedFlightEntities: List<ProposedFlightEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .proposedFlightDao()
                .deleteAll(proposedFlightEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun observeProposedFlights(): Flow<List<ProposedFlightEntity>> {
        return appDatabase
            .proposedFlightDao()
            .proposedFlightFlow()
            .flatMapLatest { list ->
                flowOf(list.filterNotNull()
                    .mapNotNull { flight ->
                        DbUtils.fetchProposedFlightEntity(appDatabase, flight)
                    })
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun observeProposedFlights(
        query: String,
        departureDate: LocalDate,
    ): Flow<List<ProposedFlightEntity>> {
        return observeProposedFlights()
            .map { list ->
                Log.e("ProposedFlightRepository", "Items $list")
                list.filter {
                    (query.isEmpty() ||
                            it.toAirport
                        .airportName
                        .contains(query, ignoreCase = true))
                            &&
                            it.departureTime.toLocalDate() == departureDate
                }.sortedBy { it.departureTime }
            }
    }
}