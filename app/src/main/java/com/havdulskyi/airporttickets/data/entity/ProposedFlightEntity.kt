package com.havdulskyi.airporttickets.data.entity

import java.time.ZonedDateTime

data class ProposedFlightEntity(
    val id: String,
    val fromAirport: AirportEntity,
    val toAirport: AirportEntity,
    val arriveTime: ZonedDateTime,
    val ticketCost: Double
)