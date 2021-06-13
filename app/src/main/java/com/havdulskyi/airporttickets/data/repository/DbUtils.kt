package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.AppDatabase
import com.havdulskyi.airporttickets.data.entity.AirportEntity
import com.havdulskyi.airporttickets.data.entity.ProposedFlightEntity
import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import com.havdulskyi.airporttickets.data.model.Airport
import com.havdulskyi.airporttickets.data.model.ProposedFlight
import com.havdulskyi.airporttickets.data.model.PurchasedTicket

object DbUtils {

    fun fetchAirportEntity(appDatabase: AppDatabase, airport: Airport): AirportEntity? {
        val city = appDatabase.cityDao().fetchCity(airport.cityId)
        return Mapping.mapModelToEntity(airport, city)
    }

    fun fetchProposedFlightEntity(
        appDatabase: AppDatabase,
        flight: ProposedFlight
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

    fun fetchPurchasedTicketEntity(
        appDatabase: AppDatabase,
        purchasedTicket: PurchasedTicket
    ): PurchasedTicketEntity? {
        val flight = appDatabase.proposedFlightDao().fetchFlight(purchasedTicket.proposedFlightId)
        return flight?.let {
            val flightEntity = fetchProposedFlightEntity(appDatabase, it)
            flightEntity?.let {
                val ticket = Mapping.mapModelToEntity(purchasedTicket, flightEntity)
                ticket
            }
        }
    }
}