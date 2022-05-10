package com.example.travel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travel.R

class TaskAdapter(private val data: ArrayList<TaskDataClass>) :
    RecyclerView.Adapter<TaskAdapter.VH>() {

    private lateinit var mListener: OnRecycleViewListener

    interface OnRecycleViewListener{
        fun onRecycleViewClick (position: Int)
    }

    fun setOnRecycleViewClick(listener: OnRecycleViewListener){
        mListener = listener
    }

    class VH(itemView: View, listener: OnRecycleViewListener) : RecyclerView.ViewHolder(itemView) {
        var header : TextView = itemView.findViewById(R.id.header)
        init {
            itemView.setOnClickListener {
                listener.onRecycleViewClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.task_one_item, parent, false), mListener)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.header.text = data[holder.adapterPosition].id
    }

    override fun getItemCount(): Int {
        return data.size
    }
}