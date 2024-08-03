package com.example.adminyummycart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.example.adminyummycart.databinding.ActivityLoginBinding
import com.example.adminyummycart.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference
    private  var userName: String ?= null
    private  var nameOfResturant: String ?= null

    private lateinit var googleSignInclient: GoogleSignInClient

    private val binding:ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.gcm_defaultSenderId)).requestEmail().build()
        auth = Firebase.auth
        database = Firebase.database.reference

        googleSignInclient = GoogleSignIn.getClient(this,googleSignInOptions)

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

        binding.googlebutton.setOnClickListener{
            val signIntent = googleSignInclient.signInIntent
            launcher.launch(signIntent)
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
                Toast.makeText(this,"Login Successfully",Toast.LENGTH_SHORT).show()
                updateUI(user)
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{task->
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        Toast.makeText(this,"User Id Created and Logged In Successfully",Toast.LENGTH_SHORT).show()
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



        val user = UserModel(userName,nameOfResturant,email,password)
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        userID.let {
            if (it != null) {
                database.child("user").child(it).setValue(user)
            }
        }


    }

    private fun updateUI(user: FirebaseUser?) {

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    private val launcher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode== Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful ){
                val account :GoogleSignInAccount = task.result
                val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credentials).addOnCompleteListener{authTask->
                    if(authTask.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Successfully signedIn With Google",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)

                    }
                    else{
                        Toast.makeText(this,"Google sign In Failed",Toast.LENGTH_SHORT).show()
                    }

                }

            }
            else{
                Toast.makeText(this,"Google sign In Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }
}