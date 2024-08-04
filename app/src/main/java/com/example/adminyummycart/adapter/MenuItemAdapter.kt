package com.example.adminyummycart.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminyummycart.databinding.ItemItemBinding
import com.example.adminyummycart.model.AllMenu

class MenuItemAdapter(
    private val context:Context,
    private val menuList:ArrayList<AllMenu>
): RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =menuList.size

    inner class AddItemViewHolder(private val binding: ItemItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                foodNameTextView.text = menuItem.foodName
                priceTextview.text=menuItem.foodPrice
                Glide.with(context).load(uri).into(foodimage)

                MinusButton.setOnClickListener{
                    decreseQuantity(position)

                }
                plusButton.setOnClickListener {
                    increaseQuantity(position)

                }
                deletebutton.setOnClickListener {
                    var itemposition = adapterPosition
                    if(itemposition!=RecyclerView.NO_POSITION){
                        deleteitem(itemposition)
                    }

                }
            }

        }
        private fun decreseQuantity(position: Int){
            if(itemQuantities[position]>1){
                itemQuantities[position]--
                binding.quantitytextView.text = itemQuantities[position].toString()
            }
        }
        private fun deleteitem(position: Int){
            menuList.removeAt(position)
            menuList.removeAt(position)
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,menuList.size)

        }
        private fun increaseQuantity(position: Int){
            if(itemQuantities[position]<10){
                itemQuantities[position]++
                binding.quantitytextView.text = itemQuantities[position].toString()
            }
        }


    }

}