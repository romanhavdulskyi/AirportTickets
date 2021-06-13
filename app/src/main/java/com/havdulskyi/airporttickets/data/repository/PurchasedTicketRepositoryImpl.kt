package com.havdulskyi.airporttickets.data.repository

import com.havdulskyi.airporttickets.data.AppDatabase
import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class PurchasedTicketRepositoryImpl(private val appDatabase: AppDatabase) :
    PurchasedTicketRepository {
    override suspend fun insertTicket(ticketEntity: PurchasedTicketEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .insert(Mapping.mapEntityToModel(ticketEntity))
        }
    }

    override suspend fun insertTickets(ticketEntities: List<PurchasedTicketEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .insertAll(ticketEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun updateTicket(ticketEntity: PurchasedTicketEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .update(Mapping.mapEntityToModel(ticketEntity))
        }
    }

    override suspend fun updateTickets(ticketEntities: List<PurchasedTicketEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .updateAll(ticketEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override suspend fun deleteTicket(ticketEntity: PurchasedTicketEntity) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .delete(Mapping.mapEntityToModel(ticketEntity))
        }
    }

    override suspend fun deleteTickets(ticketEntities: List<PurchasedTicketEntity>) {
        withContext(Dispatchers.IO)
        {
            appDatabase
                .purchasedTicketDao()
                .deleteAll(ticketEntities.map { Mapping.mapEntityToModel(it) })
        }
    }

    override fun observeTickets(): Flow<List<PurchasedTicketEntity>> {
        return appDatabase
            .purchasedTicketDao()
            .ticketsFlow()
            .flatMapLatest { list ->
                flowOf(list.filterNotNull()
                    .mapNotNull { flight ->
                        DbUtils.fetchPurchasedTicketEntity(appDatabase, flight)
                    })
            }.flowOn(Dispatchers.IO)
    }
}