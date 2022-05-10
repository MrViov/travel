package com.example.travel.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.travel.R
import com.example.travel.adapter.SharedPreference
import com.example.travel.adapter.TaskAdapter
import com.example.travel.adapter.TaskDataClass
import com.example.travel.databinding.FragmentTaskBinding
import com.google.firebase.database.*

class TaskFragment : Fragment() {

    private lateinit var adapter: TaskAdapter
    private lateinit var binding: FragmentTaskBinding
    private lateinit var sharedPreference: SharedPreference
    private var database: DatabaseReference = FirebaseDatabase.getInstance("https://travel-61fd2-default-rtdb.firebaseio.com/").getReference("Tasks")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_taskFragment_to_menuFragment)
            }
        })
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_taskFragment_to_addTaskFragment)
        }
        val arrayList = ArrayList<TaskDataClass>()
        val arrayKeys = ArrayList<String>()
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount.toString() == "0") {
                    binding.recycleView.visibility = View.INVISIBLE
                    binding.textNull.visibility = View.VISIBLE
                } else {
                    binding.textNull.visibility = View.INVISIBLE
                    binding.recycleView.visibility = View.VISIBLE
                }
                if (snapshot.exists()) {
                    arrayList.clear()
                    for (snap in snapshot.children) {
                        arrayList.add(snap.getValue(TaskDataClass::class.java)!!)
                        arrayKeys.add(snap.key.toString())
                    }
                }
                adapter = TaskAdapter(arrayList)
                adapter.setOnRecycleViewClick(object :
                    TaskAdapter.OnRecycleViewListener{
                    override fun onRecycleViewClick(position: Int) {
                        var dialog = AlertDialog.Builder(requireContext())
                            .setTitle("Удаление")
                            .setMessage("Вы уверены что хотите удалить?")
                            .setPositiveButton("Да")
                            { _, _ ->
                                database.child(arrayKeys[position]).removeValue()
                            }
                            .setNegativeButton("Нет")
                            { _, _ ->

                            }
                            .create()
                            .show()
                    }
                })
                binding.recycleView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return binding.root
    }
}