package com.example.travel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.travel.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    lateinit var binding: ActivityMapBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this,R.id.nav_host_fragment_container)
    }
}