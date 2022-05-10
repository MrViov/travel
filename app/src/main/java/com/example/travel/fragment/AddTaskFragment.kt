package com.example.travel.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.travel.R
import com.example.travel.adapter.SharedPreference
import com.example.travel.adapter.TaskDataClass
import com.example.travel.databinding.FragmentAddTaskBinding
import com.example.travel.databinding.FragmentTaskBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddTaskFragment : Fragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance("https://travel-61fd2-default-rtdb.firebaseio.com/").getReference("Tasks")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_addTaskFragment_to_taskFragment)
            }
        })
        binding.saveButton.setOnClickListener {
            if(binding.text.text.isNotEmpty()){
                var a = TaskDataClass(
                    id = binding.text.text.toString()
                )
                database.child(getKey("Tasks").push().key.toString()).setValue(a).addOnCompleteListener {
                    if(it.isSuccessful){
                        findNavController().navigate(R.id.action_addTaskFragment_to_taskFragment)
                    }
                }
            }
            else{
                Toast.makeText(activity,"Вы не ввели текст",Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun getKey(path: String): DatabaseReference {
        return FirebaseDatabase
            .getInstance("https://travel-61fd2-default-rtdb.firebaseio.com/")
            .getReference(path)
    }
}