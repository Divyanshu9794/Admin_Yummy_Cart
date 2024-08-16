package com.example.adminyummycart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminyummycart.adapter.DeliveryAdapter
import com.example.adminyummycart.adapter.PendingOrderAdapter
import com.example.adminyummycart.databinding.ActivityPendingOrderBinding
import com.example.adminyummycart.databinding.PendingOrdersItemBinding

class PendingOrderActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPendingOrderBinding
    
    private var listOfName:MutableList<String> =mutableListOf()
    private var listOfTotalPrice :MutableList<String> = mutableListOf()
    private  var listOfImageFirstFoodOrder :MutableList<String> = mutableListOf()
    private var listOfOrderItem:MutableList<OrderDetails> = mutableListOf()

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityPendingOrderBinding.inflate(layoutInflater)
        binding.backbutton.setOnClickListener {
            finish()
        }
        setContentView(binding.root)

        

    }
}