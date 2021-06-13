package com.havdulskyi.airporttickets.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.havdulskyi.airporttickets.data.entity.AirportEntity
import com.havdulskyi.airporttickets.data.entity.ProposedFlightEntity
import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import com.havdulskyi.airporttickets.data.entity.UserProfileEntity
import com.havdulskyi.airporttickets.data.model.*
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
object Mapping {
    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z")

    @RequiresApi(Build.VERSION_CODES.O)
    fun mapZonedDateTimeToString(zonedDateTime: ZonedDateTime): String {
        return zonedDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun mapZonedDateTimeFromString(time: String): ZonedDateTime {
        return ZonedDateTime.parse(time, formatter)
    }

    fun mapEntityToModel(airportEntity: AirportEntity): Airport {
        return Airport(
            id = airportEntity.id,
            airportName = airportEntity.airportName,
            airportCode = airportEntity.airportCode,
            cityId = airportEntity.city.id
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun mapEntityToModel(proposedFlightEntity: ProposedFlightEntity): ProposedFlight {
        return ProposedFlight(
            id = proposedFlightEntity.id,
            fromAirportId = proposedFlightEntity.fromAirport.id,
            toAirportId = proposedFlightEntity.toAirport.id,
            departureTime = mapZonedDateTimeToString(proposedFlightEntity.departureTime),
            arriveTime = mapZonedDateTimeToString(proposedFlightEntity.arriveTime),
            ticketCost = proposedFlightEntity.ticketCost
        )
    }

    fun mapEntityToModel(purchasedTicketEntity: PurchasedTicketEntity): PurchasedTicket {
        return PurchasedTicket(
            id = purchasedTicketEntity.id,
            proposedFlightId = purchasedTicketEntity.proposedFlight.id,
            purchaseId = purchasedTicketEntity.purchaseId,
            purchaseTimestamp = mapZonedDateTimeToString(purchasedTicketEntity.purchaseTimestamp),
            userId = purchasedTicketEntity.userId
        )
    }

    fun mapEntityToModel(userProfileEntity: UserProfileEntity): UserProfile {
        return UserProfile(
            id = userProfileEntity.id,
            nickName = userProfileEntity.nickName,
            id3rdParty = userProfileEntity.id3rdParty
        )
    }

    fun mapModelToEntity(airport: Airport, city: City?): AirportEntity? {
        return city?.let {
            AirportEntity(
                id = airport.id,
                airportName = airport.airportName,
                airportCode = airport.airportCode,
                city = it
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun mapModelToEntity(
        proposedFlight: ProposedFlight,
        fromAirport: AirportEntity,
        toAirport: AirportEntity
    ): ProposedFlightEntity {
        return ProposedFlightEntity(
            id = proposedFlight.id,
            fromAirport = fromAirport,
            toAirport = toAirport,
            departureTime = mapZonedDateTimeFromString(proposedFlight.departureTime),
            arriveTime = mapZonedDateTimeFromString(proposedFlight.arriveTime),
            ticketCost = proposedFlight.ticketCost
        )
    }

    fun mapModelToEntity(
        purchasedTicket: PurchasedTicket,
        proposedFlight: ProposedFlightEntity
    ): PurchasedTicketEntity {
        return PurchasedTicketEntity(
            id = purchasedTicket.id,
            proposedFlight = proposedFlight,
            purchaseId = purchasedTicket.purchaseId,
            purchaseTimestamp = mapZonedDateTimeFromString(purchasedTicket.purchaseTimestamp),
            userId = purchasedTicket.userId
        )
    }

    fun mapModelToEntity(
        userProfile: UserProfile
    ): UserProfileEntity {
        return UserProfileEntity(
            id = userProfile.id,
            nickName = userProfile.nickName,
            id3rdParty = userProfile.id3rdParty
        )
    }
}