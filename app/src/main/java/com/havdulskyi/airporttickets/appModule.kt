package com.havdulskyi.airporttickets

import android.content.Context
import androidx.room.Room
import com.havdulskyi.airporttickets.data.AppDatabase
import com.havdulskyi.airporttickets.data.BaseInitializer
import com.havdulskyi.airporttickets.data.DbInitializer
import com.havdulskyi.airporttickets.data.repository.*
import com.havdulskyi.airporttickets.view.flight.FlightViewModel
import com.havdulskyi.airporttickets.view.purchased.PurchasedTicketViewModel
import org.koin.android.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AirportRepository> { AirportRepositoryImpl(provideDatabase(get())) }
    single<CityRepository> { CityRepositoryImpl(provideDatabase(get())) }
    single<ProposedFlightRepository> { ProposedFlightRepositoryImpl(provideDatabase(get())) }
    single<PurchasedTicketRepository> { PurchasedTicketRepositoryImpl(provideDatabase(get())) }
    single<UserProfileRepository> { UserProfileRepositoryImpl(provideDatabase(get())) }

    single<BaseInitializer> { DbInitializer(get(), get(), get(), get()) }
    viewModel { FlightViewModel(get(), get()) }
    viewModel { PurchasedTicketViewModel(get()) }
}

fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java, "AppDatabase"
    ).fallbackToDestructiveMigration()
        .build()
}