package com.havdulskyi.airporttickets.view.flight

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.havdulskyi.airporttickets.common.Event
import com.havdulskyi.airporttickets.data.entity.ProposedFlightEntity
import com.havdulskyi.airporttickets.data.entity.PurchasedTicketEntity
import com.havdulskyi.airporttickets.data.entity.UserProfileEntity
import com.havdulskyi.airporttickets.data.repository.ProposedFlightRepository
import com.havdulskyi.airporttickets.data.repository.PurchasedTicketRepository
import com.havdulskyi.airporttickets.data.repository.UserProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.*

@FlowPreview
class FlightViewModel(
    private val flightRepository: ProposedFlightRepository,
    private val purchasedTicketRepository: PurchasedTicketRepository,
    private val userProfileRepository: UserProfileRepository,
) : ViewModel() {

    private var currentUser: UserProfileEntity? = null
    private val searchQuery = MutableSharedFlow<String>(replay = 1)
    private val departureDate = MutableSharedFlow<LocalDate>(replay = 1)

    fun setDepartureDate(date: GregorianCalendar) {
        departureDate.tryEmit(date.toZonedDateTime().toLocalDate())
    }

    fun setSearchQuery(query : String)
    {
        searchQuery.tryEmit(query)
    }

    fun bookTicket(proposedFlightEntity: ProposedFlightEntity)
    {
        showBookedDialog.value = Event(proposedFlightEntity.id)
        viewModelScope.launch {

            val randomId = UUID.randomUUID().toString()
            purchasedTicketRepository.insertTicket(PurchasedTicketEntity(
                id = proposedFlightEntity.id + randomId,
                proposedFlight = proposedFlightEntity,
                purchaseId = randomId,
                purchaseTimestamp = ZonedDateTime.now(),
                userId = currentUser?.id ?: ""
            ))
        }
    }

    val flightsLiveData = MutableLiveData<List<ProposedFlightEntity>>()

    val showBookedDialog = MutableLiveData<Event<String>>()

    val showProgressBar = MutableLiveData<Boolean>(true)


    init {
        viewModelScope.launch {

            val users = userProfileRepository
                .observeUsers()
                .firstOrNull()

            currentUser = users?.firstOrNull()

            departureDate.tryEmit(LocalDate.now())
            searchQuery.tryEmit("")
            searchQuery
                .conflate()
                .combine(departureDate.conflate()) { a, b ->
                    showProgressBar.postValue(true)
                    SearchRequest(a, b)
                }
                .flatMapLatest { request ->
                    flightRepository.observeProposedFlights(request.query, request.departureDate)
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    showProgressBar.postValue(false)
                    flightsLiveData.postValue(it)
                }
        }
    }

    private data class SearchRequest(val query: String, val departureDate : LocalDate)

}