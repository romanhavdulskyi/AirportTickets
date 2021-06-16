package com.havdulskyi.airporttickets.data.dao

import androidx.room.*
import com.havdulskyi.airporttickets.data.dbModel.PurchasedTicket
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseTicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(purchasedTicket: PurchasedTicket)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(purchasedTickets: List<PurchasedTicket>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(purchasedTicket: PurchasedTicket)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(purchasedTickets: List<PurchasedTicket>)

    @Delete
    fun delete(purchasedTicket: PurchasedTicket)

    @Delete
    fun deleteAll(purchasedTickets: List<PurchasedTicket>)

    @Query("SELECT * FROM PurchasedTicket")
    fun ticketsFlow(): Flow<List<PurchasedTicket?>>
}