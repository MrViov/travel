package com.example.travel.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.travel.R
import com.example.travel.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater)
        binding.toMap.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_mapFragment)
        }
        binding.toList.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_taskFragment)
        }
        return binding.root
    }
}