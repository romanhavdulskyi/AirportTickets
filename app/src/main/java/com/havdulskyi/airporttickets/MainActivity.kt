package com.havdulskyi.airporttickets

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation)
        val flightFragment = supportFragmentManager.findFragmentById(R.id.flightFragment)
        val ticketFragment = supportFragmentManager.findFragmentById(R.id.ticketFragment)

        flightFragment?.view?.visibility = View.VISIBLE
        ticketFragment?.view?.visibility = View.GONE

        bottomNavigation?.setOnNavigationItemSelectedListener {
            //Todo that's the incredible stupid solution,
            // but it's also so quickly way of proceeding a switch between fragments
            if(it.itemId == R.id.action_flights) {
                flightFragment?.view?.visibility = View.VISIBLE
                ticketFragment?.view?.visibility = View.GONE
            } else {
                flightFragment?.view?.visibility = View.GONE
                ticketFragment?.view?.visibility = View.VISIBLE
            }
            true
        }
    }


}