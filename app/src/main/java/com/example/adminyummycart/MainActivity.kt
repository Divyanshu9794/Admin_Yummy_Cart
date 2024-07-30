package com.example.adminyummycart

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminyummycart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        binding.addmenu.setOnClickListener {
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }
        binding.allItemMenu.setOnClickListener {
            val intent = Intent(this,AllItemActivity::class.java)
            startActivity(intent)
        }
        binding.outForDeliveryButton.setOnClickListener{
            val intent =Intent(this,OutForDeliveryActivity::class.java)
            startActivity(intent)
        }

        binding.profile.setOnClickListener{
            val intent =Intent(this,AdminProfileActivity::class.java)
            startActivity(intent)
        }

        binding.createUser.setOnClickListener{
            val intent =Intent(this,CreateUserActivity::class.java)
            startActivity(intent)
        }
        binding.pendingordertextview.setOnClickListener{
            val intent = Intent(this,PendingOrderActivity::class.java)
            startActivity(intent)
        }

    }
}