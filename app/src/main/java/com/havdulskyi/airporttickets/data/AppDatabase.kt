package com.havdulskyi.airporttickets.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.havdulskyi.airporttickets.data.dao.*
import com.havdulskyi.airporttickets.data.dbModel.*

@Database(
    entities = [UserProfile::class, Airport::class, City::class,
        ProposedFlight::class, PurchasedTicket::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun cityDao(): CityDao
    abstract fun proposedFlightDao(): ProposedFlightDao
    abstract fun purchasedTicketDao(): PurchaseTicketDao
    abstract fun userProfileDao(): UserProfileDao
}