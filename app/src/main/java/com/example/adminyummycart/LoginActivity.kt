package com.example.adminyummycart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminyummycart.databinding.ActivityLoginBinding
import com.example.adminyummycart.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.loginbutton.setOnClickListener{
            email = binding.emailaddress.text.toString().trim()
            password = binding.pass.text.toString().trim()


            if(email.isBlank()|| password.isBlank()){
                Toast.makeText(this,"Please Fill all the Datails",Toast.LENGTH_SHORT).show()
            }
            else{
                createUserAccount(email,password)
            }


        }
        binding.donthave.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ task->

            if(task.isSuccessful){
                val user = auth.currentUser
                updateUI(user)
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        saveUserData()
                        updateUI(user)
                    }
                    else{
                        Toast.makeText(this,"Authentication Failed",Toast.LENGTH_SHORT).show()
                        Log.d("Account","createUserAccount:Authentication failed",task.exception)
                    }
                }
            }
        }
    }

    private fun saveUserData() {
        email = binding.emailaddress.text.toString().trim()
        password = binding.pass.text.toString().trim()


        val user = UserModel(email,password)
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        userID.let {
            if (it != null) {
                database.child("user").child(it).setValue(user)
            }
        }


    }

    private fun updateUI(user: FirebaseUser?) {

        startActivity(Intent(this,MainActivity::class.java))
    }
}