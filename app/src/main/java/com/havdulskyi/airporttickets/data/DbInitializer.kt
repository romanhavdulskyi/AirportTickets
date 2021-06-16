package com.havdulskyi.airporttickets.data

import com.havdulskyi.airporttickets.data.dbModel.City
import com.havdulskyi.airporttickets.data.entity.AirportEntity
import com.havdulskyi.airporttickets.data.entity.ProposedFlightEntity
import com.havdulskyi.airporttickets.data.repository.AirportRepository
import com.havdulskyi.airporttickets.data.repository.CityRepository
import com.havdulskyi.airporttickets.data.repository.ProposedFlightRepository
import com.havdulskyi.airporttickets.data.repository.UserProfileRepository
import java.time.LocalDate
import java.time.ZoneId

class DbInitializer(
    private val cityRepository: CityRepository,
    private val airportRepository: AirportRepository,
    private val proposedFlightRepository: ProposedFlightRepository,
    private val userProfileRepository: UserProfileRepository,
) : BaseInitializer {
    override suspend fun initializeForDate(now: LocalDate) {
        initializeData(now)
    }

    private suspend fun initializeData(date: LocalDate) {
        val cityLviv = City(id = "56ggt6hfh55g_a", city = " Lviv", country = "Ukraine")
        val airportLviv = AirportEntity(id = "56ggt6hfh55g_airport_a",
            airportName = "Lviv airport", airportCode = "LVA", city = cityLviv)
        val cityKyiv = City(id = "56ggt6hfh55g_b", city = " Kyiv", country = "Ukraine")
        val airportKyiv = AirportEntity(id = "56ggt6hfh55g_airport_b",
            airportName = "Kyiv airport", airportCode = "KYV", city = cityKyiv)
        val cityAmsterdam = City(id = "56ggt6hfh55g_c", city = " Amsterdam", country = "Nederland")
        val airportAmsterdam = AirportEntity(id = "56ggt6hfh55g_airport_c",
            airportName = "Amsterdam airport", airportCode = "AMS", city = cityAmsterdam)
        val cityLondon = City(id = "56ggt6hfh55g_d", city = " London", country = "Great Brittan")
        val airportLondon = AirportEntity(id = "56ggt6hfh55g_airport_d",
            airportName = "London airport", airportCode = "LND", city = cityLondon)

        cityRepository.updateCities(listOf(cityLviv, cityKyiv, cityAmsterdam, cityLondon))

        airportRepository.insertAirports(listOf(airportLviv, airportKyiv,
            airportAmsterdam, airportLondon))

        proposedFlightRepository.insertProposedFlights(generateFlightFor(airportLviv, airportKyiv,
            45.0, date))

        proposedFlightRepository.insertProposedFlights(generateFlightFor(airportLviv, airportAmsterdam,
            85.0, date))

        proposedFlightRepository.insertProposedFlights(generateFlightFor(airportLviv, airportLondon,
            145.0, date))

    }

    private fun generateFlightFor(fromAirport : AirportEntity,
                                  toAirport: AirportEntity,
                                  ticketCost : Double,
                                  baseDate : LocalDate) : List<ProposedFlightEntity> {
        val flights = mutableListOf<ProposedFlightEntity>()
        (0..3).forEach { value ->
            val startHour = (1..12).random()
            val duration = (4..8).random()
            val startDateTime = baseDate
                .atStartOfDay(ZoneId.systemDefault())
                .plusDays(value.toLong())
                .withHour(startHour)
            flights.add(ProposedFlightEntity("56ggt6hfh55g_flight_${fromAirport.id}_${toAirport.id}_${startDateTime}",
                fromAirport = fromAirport, toAirport = toAirport,
                departureTime = startDateTime,
                arriveTime = startDateTime
                    .withHour(startHour + duration),
                ticketCost = ticketCost))
        }
        return flights
    }
}