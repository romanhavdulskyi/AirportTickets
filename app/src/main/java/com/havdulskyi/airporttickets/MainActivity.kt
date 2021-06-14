package com.havdulskyi.airporttickets

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation)
        val navigationHost =
            Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navigationHost)
    }
}