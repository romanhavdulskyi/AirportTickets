package com.havdulskyi.airporttickets.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PurchasedTicket(
    @PrimaryKey
    val id: String,
    val proposedFlightId: String,
    val purchaseId: String,
    val purchaseTimestamp: String
)