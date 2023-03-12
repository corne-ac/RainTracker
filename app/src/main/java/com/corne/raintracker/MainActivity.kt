package com.corne.raintracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.corne.raintracker.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.selectedItemId = R.id.home

        //OnClick events for bottom nav
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.add -> replaceFragment(AddFragment())
                R.id.home -> replaceFragment(HomeFragment())
                R.id.chart -> replaceFragment(ChartFragment())
                R.id.list -> replaceFragment(ListFragment())
                R.id.Settings -> replaceFragment(SettingsFragment())
            else -> {}
            }
            true
        }

    }

    //Function to replace frame_layout with the fragment
    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}