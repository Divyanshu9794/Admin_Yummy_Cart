package com.example.adminyummycart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminyummycart.databinding.ActivityOrderDetailsBinding
import com.example.adminyummycart.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {

    private val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    private var userName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null

    private var totalPrice: String? = null
    private var foodNames: MutableList<String> = mutableListOf()
    private var foodImages: MutableList<String> = mutableListOf()
    private var foodQuantities: MutableList<Int> = mutableListOf()
    private var foodPrices: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)



        binding.backbutton.setOnClickListener {
            finish()
        }
        getDataFromIntent()

    }

    private fun getDataFromIntent() {

        val receivedOrderDetails = intent.getParcelableExtra<OrderDetails>("UserOrderDetails")
        if (receivedOrderDetails != null) {
            userName = receivedOrderDetails.userName
            foodNames = receivedOrderDetails.foodNames!!
            foodImages = receivedOrderDetails.foodImages!!
            foodQuantities = receivedOrderDetails.foodQuantities!!
            foodPrices = receivedOrderDetails.foodPrices!!
            totalPrice = receivedOrderDetails.totalPrice
            address = receivedOrderDetails.address
            phoneNumber =receivedOrderDetails.phoneNumber

        }




    }
}