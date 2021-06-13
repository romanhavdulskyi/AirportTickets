package com.havdulskyi.airporttickets.data.repository

import android.util.Log
import com.havdulskyi.airporttickets.data.AppDatabase
import com.havdulskyi.airporttickets.data.entity.AirportEntity
import com.havdulskyi.airporttickets.data.entity.ProposedFlightEntity
import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import com.havdulskyi.airporttickets.data.model.Airport
import com.havdulskyi.airporttickets.data.model.City
import com.havdulskyi.airporttickets.data.model.ProposedFlight
import com.havdulskyi.airporttickets.data.model.PurchasedTicket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.time.LocalDate

class DefaultFlightRepository(private val appDatabase: AppDatabase) : AirportRepository,
    ProposedFlightRepository,
    PurchasedTicketRepository,
    CityRepository {

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
                            prepareAirportEntity(airport)
                        })
            }.flowOn(Dispatchers.IO)
    }

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
                        prepareProposedFlightEntity(flight)
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

    override suspend fun insertTicket(ticketEntity: PurchasedTicketEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .insert(Mapping.mapEntityToModel(ticketEntity))
        }
    }

    override suspend fun insertTickets(ticketEntities: List<PurchasedTicketEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .insertAll(ticketEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun updateTicket(ticketEntity: PurchasedTicketEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .update(Mapping.mapEntityToModel(ticketEntity))
        }
    }

    override suspend fun updateTickets(ticketEntities: List<PurchasedTicketEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .updateAll(ticketEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun deleteTicket(ticketEntity: PurchasedTicketEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .delete(Mapping.mapEntityToModel(ticketEntity))
        }
    }

    override suspend fun deleteTickets(ticketEntities: List<PurchasedTicketEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .deleteAll(ticketEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override fun observeTickets(userId: String): Flow<List<PurchasedTicketEntity>> {
        return appDatabase
            .purchasedTicketDao()
            .ticketsFlow()
            .flatMapLatest { list ->
                flowOf(list.filterNotNull()
                    .mapNotNull { flight ->
                        preparePurchasedTicketEntity(flight)
                    }.filter { it.userId == userId })
            }.flowOn(Dispatchers.IO)
    }

    private fun prepareAirportEntity(airport: Airport): AirportEntity? {
        val city = appDatabase.cityDao().fetchCity(airport.cityId)
        return Mapping.mapModelToEntity(airport, city)
    }

    private fun prepareProposedFlightEntity(
        flight: ProposedFlight,
    ): ProposedFlightEntity? {
        val fromAirport =
            appDatabase.airportDao().fetchAirport(flight.fromAirportId)
        val fromCity =
            fromAirport?.cityId?.let { appDatabase.cityDao().fetchCity(it) }
        val toAirport = appDatabase.airportDao().fetchAirport(flight.toAirportId)
        val toCity = toAirport?.cityId?.let { appDatabase.cityDao().fetchCity(it) }
        return fromAirport?.let { fromAirportTmp ->
            Mapping.mapModelToEntity(fromAirportTmp, fromCity)
                ?.let { fromAirportEntity ->
                    toAirport?.let { toAirportTmp ->
                        Mapping.mapModelToEntity(toAirportTmp, toCity)
                            ?.let { toAirportEntity ->
                                Mapping.mapModelToEntity(
                                    flight,
                                    fromAirportEntity,
                                    toAirportEntity
                                )
                            }
                    }
                }
        }
    }

    private fun preparePurchasedTicketEntity(
        purchasedTicket: PurchasedTicket,
    ): PurchasedTicketEntity? {
        val flight = appDatabase.proposedFlightDao().fetchFlight(purchasedTicket.proposedFlightId)
        return flight?.let {
            val flightEntity = prepareProposedFlightEntity(it)
            flightEntity?.let {
                val ticket = Mapping.mapModelToEntity(purchasedTicket, flightEntity)
                ticket
            }
        }
    }

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