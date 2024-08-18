package com.example.adminyummycart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminyummycart.databinding.ActivityAdminProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
        binding.saveInfoButton.isEnabled = false


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

        retrieveUserData()
    }

    private fun retrieveUserData() {

        adminReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var ownerName = snapshot.child("name").getValue()
                    var email = snapshot.child("email").getValue()
                    var password = snapshot.child("password").getValue()
                    var address = snapshot.child("address").getValue()
                    var phone = snapshot.child("phone").getValue()

                    setDataToTextView(ownerName,email,password,address,phone)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {

        binding.name.setText(ownerName.toString())
        binding.emailid.setText(email.toString())
        binding.password.setText(password.toString())
        binding.address.setText(address.toString())
        binding.phonenumber.setText(phone.toString())

    }

}