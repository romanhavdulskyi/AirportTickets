package com.havdulskyi.airporttickets.data.entity

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

data class ProposedFlightEntity(
    val id: String,
    val fromAirport: AirportEntity,
    val toAirport: AirportEntity,
    val departureTime: ZonedDateTime,
    val arriveTime: ZonedDateTime,
    val ticketCost: Double
) {
    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd HH:mm")

    val formatDepartureTime : String
        get() {
           return departureTime.format(formatter)
        }

    val formatArriveTime : String
        get() {
            return arriveTime.format(formatter)
        }

    val formatTicketCost : String
        get() {
            return "${ticketCost.roundToInt()}\$"
        }
}