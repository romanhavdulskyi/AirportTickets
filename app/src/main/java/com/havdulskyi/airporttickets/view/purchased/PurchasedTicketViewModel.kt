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
import com.havdulskyi.airporttickets.data.entity.UserProfileEntity
import com.havdulskyi.airporttickets.data.repository.PurchasedTicketRepository
import com.havdulskyi.airporttickets.data.repository.UserProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class PurchasedTicketViewModel(
    private val userProfileRepository: UserProfileRepository,
    private val purchasedTicketRepository: PurchasedTicketRepository,
) : ViewModel() {

    private var currentUserFlow = MutableSharedFlow<UserProfileEntity>(replay = 1)

    val ticketsLiveData =
        currentUserFlow.flatMapLatest { user ->
            purchasedTicketRepository
                .observeTickets(user.id)
        }
            .flowOn(Dispatchers.IO)
            .asLiveData()

    fun showTicket(purchasedTicketEntity: PurchasedTicketEntity) {
        viewModelScope.launch(Dispatchers.Default) {
            showTicketQr.postValue(Event(QRGEncoder(purchasedTicketEntity.toString(),
                null, QRGContents.Type.TEXT, 300).bitmap))
        }
    }

    val showTicketQr = MutableLiveData<Event<Bitmap?>>()


    init {
        viewModelScope.launch(Dispatchers.IO) {

            val users = userProfileRepository
                .observeUsers()
                .firstOrNull()

            users?.firstOrNull()?.let { currentUserFlow.emit(it) }
        }
    }

}