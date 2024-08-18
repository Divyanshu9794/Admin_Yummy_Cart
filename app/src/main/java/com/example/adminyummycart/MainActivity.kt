package com.example.adminyummycart

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminyummycart.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var database:FirebaseDatabase
    private lateinit var auth :FirebaseAuth

    private lateinit var completedOrderReference : DatabaseReference
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

        pendingOrders()
        completeOrders()

    }


    private fun completeOrders() {
        val completedOrderReference = database.reference.child("CompletedOrder")

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val completeOrderItemCount = snapshot.childrenCount.toInt()
                binding.completeOrders.text = completeOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        }
        completedOrderReference.addValueEventListener(valueEventListener)

    }

    private lateinit var pendingOrderReference: DatabaseReference
    private lateinit var valueEventListener: ValueEventListener

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        pendingOrderReference = database.reference.child("OrderDetails")

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrders.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        }
        pendingOrderReference.addValueEventListener(valueEventListener)
    }

    override fun onStop() {
        super.onStop()
        pendingOrderReference.removeEventListener(valueEventListener)
    }
}
