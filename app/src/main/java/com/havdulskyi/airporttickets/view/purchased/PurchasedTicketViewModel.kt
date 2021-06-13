package com.havdulskyi.airporttickets.view.purchased

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.havdulskyi.airporttickets.common.Event
import com.havdulskyi.airporttickets.common.QRGContents
import com.havdulskyi.airporttickets.common.QRGEncoder
import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import com.havdulskyi.airporttickets.data.repository.PurchasedTicketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PurchasedTicketViewModel(private val purchasedTicketRepository: PurchasedTicketRepository)
    : ViewModel() {

    val ticketsLiveData = purchasedTicketRepository
        .observeTickets()
        .flowOn(Dispatchers.IO)
        .asLiveData()

    fun showTicket(purchasedTicketEntity: PurchasedTicketEntity)
    {
        viewModelScope.launch(Dispatchers.Default) {
            showTicketQr.postValue(Event(QRGEncoder(purchasedTicketEntity.toString(),
                null, QRGContents.Type.TEXT, 300).bitmap))
        }
    }

    val showTicketQr = MutableLiveData<Event<Bitmap?>>()


}