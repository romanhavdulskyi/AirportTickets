package com.havdulskyi.airporttickets

import android.app.Application
import com.havdulskyi.airporttickets.data.BaseInitializer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent
import java.time.LocalDate

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }

        val initializer = KoinJavaComponent.get(BaseInitializer::class.java)
        GlobalScope.launch {
            initializer.initializeForDate(LocalDate.now())
        }
    }
}