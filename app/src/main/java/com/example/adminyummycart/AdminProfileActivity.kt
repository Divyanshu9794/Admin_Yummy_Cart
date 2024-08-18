package com.example.adminyummycart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminyummycart.databinding.ActivityAdminProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminProfileActivity : AppCompatActivity() {
    private val binding:ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")
        
        binding.backbutton.setOnClickListener {
            finish()
        }

        binding.name.isEnabled=false
        binding.address.isEnabled=false
        binding.emailid.isEnabled=false
        binding.phonenumber.isEnabled=false
        binding.password.isEnabled=false

        var isEnable = false
        binding.editButton.setOnClickListener{
            isEnable =!isEnable

            binding.name.isEnabled=isEnable
            binding.address.isEnabled=isEnable
            binding.emailid.isEnabled=isEnable
            binding.phonenumber.isEnabled=isEnable
            binding.password.isEnabled=isEnable

            if(isEnable){
                binding.name.requestFocus()
            }

        }
    }

}