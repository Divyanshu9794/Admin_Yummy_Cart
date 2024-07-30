package com.example.adminyummycart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminyummycart.adapter.DeliveryAdapter
import com.example.adminyummycart.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding : ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.backbutton.setOnClickListener {
            finish()
        }

        val customerName = arrayListOf(
            "Divyanshu Singh",
            "Amar Singh ",
            "Dhruv Dwivedi"
        )
        val moneyStatus = arrayListOf(
            "received",
            "notReceived",
            "pending"
        )
        val adapter = DeliveryAdapter(customerName,moneyStatus)
        binding.DeliveryRecyclerView.adapter = adapter
        binding.DeliveryRecyclerView.layoutManager= LinearLayoutManager(this)

    }
}