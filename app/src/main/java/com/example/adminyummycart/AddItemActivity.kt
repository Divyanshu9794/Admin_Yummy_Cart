package com.example.adminyummycart

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminyummycart.databinding.ActivityAddItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddItemActivity : AppCompatActivity() {

    //food item details

    private lateinit var foodName:String
    private lateinit var foodPrice:String
    private lateinit var foodDescription:String
    private lateinit var foodIngredient:String
    private  var foodImage:Uri?=null


    //firebase

    private lateinit var auth: FirebaseAuth

    private lateinit var database: FirebaseDatabase

    private val binding : ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        //Initialization of Firebase

        auth = FirebaseAuth.getInstance()

        //Initializing Database Instance

        database = FirebaseDatabase.getInstance()

        binding.additembutton.setOnClickListener{
            //get datafrom fields

            foodName = binding.foodname.text.toString().trim()
            foodPrice = binding.foodprice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient =binding.ingredient.text.toString().trim()

            if(!(foodName.isBlank() || foodPrice.isBlank()||foodDescription.isBlank()||foodIngredient.isBlank())){
                uploadData()
                Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_SHORT).show()

                finish()
            }
            else{
                Toast.makeText(this,"Fill All the details",Toast.LENGTH_SHORT).show()
            }

        }
        
        binding.selectimage.setOnClickListener{
            pickimage.launch("image/*")
        }
        

        binding.backbutton.setOnClickListener {
            finish()
        }

    }

    private fun uploadData() {

        //get reference to the menu to the database

        val MenuRef:DatabaseReference = database.getReference("")


    }

    private val pickimage=registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        if(uri != null){
            binding.selectedimage.setImageURI(uri)
        }

    }
}