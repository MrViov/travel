package com.example.travel.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.travel.adapter.InfoAdapter
import com.example.travel.PlacesArray
import com.example.travel.R
import com.example.travel.databinding.FragmentInfoBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.Exception

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        try{
            binding = FragmentInfoBinding.inflate(inflater)
            var id = requireActivity().getSharedPreferences("Prefs", Context.MODE_PRIVATE).getInt("ID", 0)
            binding.viewPager.adapter = InfoAdapter(context = requireContext(), data = PlacesArray().places[id].uris)
            TabLayoutMediator(binding.tabs, binding.viewPager){_,_->}.attach()
            requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_infoFragment_to_mapFragment2)
                }
            })
        }
        catch (ex : Exception){
            Log.e("Exception in InfoFragment.kt: ", "$ex")
        }
        return binding.root
    }
}