package com.example.adminyummycart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminyummycart.adapter.AddItemAdapter
import com.example.adminyummycart.databinding.ActivityAllItemBinding
import java.util.ArrayList

class AllItemActivity : AppCompatActivity() {
    private val binding:ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        val menufoodname = listOf("Burger","Samosa","Paneer Tikka")
        val menuItemPrice = listOf("₹55","₹5","₹75")
        val menuImage = listOf(R.drawable.burger,R.drawable.samosa,R.drawable.paneertikka)
        val adapter = AddItemAdapter(ArrayList(menufoodname),
            ArrayList(menuItemPrice),ArrayList(menuImage)
        )

        binding.menurecyclerview.layoutManager=LinearLayoutManager(this)
        binding.menurecyclerview.adapter = adapter

    }
}