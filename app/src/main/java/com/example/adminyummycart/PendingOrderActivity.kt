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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityPendingOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val orderedcustomerName = arrayListOf(
            "Divyanshu Singh",
            "Amar Singh ",
            "Dhruv Dwivedi"
        )
        val orderedQuanity = arrayListOf(
            "8",
            "6",
            "5"
        )
        val orderedFoodImage= arrayListOf(R.drawable.burger,R.drawable.cholekulche,R.drawable.cholebhature)
        val adapter = PendingOrderAdapter(orderedcustomerName,orderedQuanity,orderedFoodImage)
        binding.pendingOrderRecycerView.adapter = adapter
        binding.pendingOrderRecycerView.layoutManager= LinearLayoutManager(this)

    }
}