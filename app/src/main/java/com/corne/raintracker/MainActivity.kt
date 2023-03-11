package com.corne.raintracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.corne.raintracker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // This method is called when the activity is created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the activity's layout using the binding object.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the bottom navigation view from the layout.
        val navView: BottomNavigationView = binding.navView

        // Get the navigation controller for the activity.
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Create an app bar configuration object that defines the top-level destinations for the app.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        // Set up the action bar to work with the navigation controller.
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Connect the bottom navigation view to the navigation controller.
        navView.setupWithNavController(navController)
    }
}