package com.example.adminyummycart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminyummycart.databinding.ItemItemBinding

class AddItemAdapter(private val MenuItemName:ArrayList<String>, private val MenuItemPrice:ArrayList<String>, private val MenuItemImage:ArrayList<Int>): RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder>() {

    private val itemQuantities = IntArray(MenuItemName.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddItemViewHolder(binding)
    }



    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int =MenuItemImage.size

    inner class AddItemViewHolder(private val binding: ItemItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodNameTextView.text = MenuItemName[position]
                priceTextview.text=MenuItemPrice[position]
                foodimage.setImageResource(MenuItemImage[position])

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
            MenuItemName.removeAt(position)
            MenuItemPrice.removeAt(position)
            MenuItemImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,MenuItemName.size)

        }
        private fun increaseQuantity(position: Int){
            if(itemQuantities[position]<10){
                itemQuantities[position]++
                binding.quantitytextView.text = itemQuantities[position].toString()
            }
        }


    }

}