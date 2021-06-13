package com.havdulskyi.airporttickets.data.entity

import java.time.ZonedDateTime

data class PurchasedTicketEntity(
    val id: String,
    val proposedFlight: ProposedFlightEntity,
    val purchaseId: String,
    val purchaseTimestamp: ZonedDateTime,
    val userId: String,

    )