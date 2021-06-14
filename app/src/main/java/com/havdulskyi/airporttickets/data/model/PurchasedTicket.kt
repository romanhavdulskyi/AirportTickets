package com.havdulskyi.airporttickets.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(entity = UserProfile::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = ProposedFlight::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("proposedFlightId"),
        onDelete = ForeignKey.CASCADE)
])
data class PurchasedTicket(
    @PrimaryKey
    val id: String,
    val proposedFlightId: String,
    val purchaseId: String,
    val purchaseTimestamp: String,
    val userId: String,
)