package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.entity.AirportEntity
import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import kotlinx.coroutines.flow.Flow

interface PurchasedTicketRepository {
    suspend fun insertTicket(ticketEntity: PurchasedTicketEntity)
    suspend fun insertTickets(ticketEntities : List<PurchasedTicketEntity>)

    suspend fun updateTicket(ticketEntity: PurchasedTicketEntity)
    suspend fun updateTickets(ticketEntities : List<PurchasedTicketEntity>)

    suspend fun deleteTicket(ticketEntity: PurchasedTicketEntity)
    suspend fun deleteTickets(ticketEntities : List<PurchasedTicketEntity>)

    suspend fun observeTickets() : Flow<List<PurchasedTicketEntity>>
}