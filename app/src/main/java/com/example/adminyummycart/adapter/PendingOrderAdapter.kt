package com.example.adminyummycart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminyummycart.PendingOrderActivity
import com.example.adminyummycart.databinding.PendingOrdersItemBinding

class PendingOrderAdapter(private val customerNames:ArrayList<String>,private val quantity:ArrayList<String>,private val foodImage:ArrayList<Int>): RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding =PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =customerNames.size
    inner class PendingOrderViewHolder(private val binding:PendingOrdersItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                customerName.text = customerNames[position]
                pendingOrderQuantity.text = quantity[position]
                orderfooditemimage.setImageResource(foodImage[position])
            }
        }

    }
}