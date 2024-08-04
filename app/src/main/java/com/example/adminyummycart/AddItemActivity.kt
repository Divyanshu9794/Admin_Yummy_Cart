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
import com.example.adminyummycart.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

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

        val menuRef:DatabaseReference = database.getReference("menu")


        //generating a unique key for the new menu item menu

        val newItemKey :String? = menuRef.push().key

        if(foodImage != null){
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")

            val uploadTask =imageRef.putFile(foodImage!!)

            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    downloadUrl ->
                    //create a new menu item

                    val newItem = AllMenu(
                        foodName=foodName,
                        foodPrice=foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUrl.toString(),


                    )
                    newItemKey?.let {
                        key->
                        menuRef.child(key).setValue(newItem).addOnSuccessListener {
                            Toast.makeText(this,"Data Uploaded Successfully",Toast.LENGTH_SHORT).show()

                        }
                            .addOnFailureListener{
                                Toast.makeText(this,"Data Uploading Failed",Toast.LENGTH_SHORT).show()
                            }
                    }
                }

            }
                .addOnFailureListener{
                    Toast.makeText(this,"Data Uploading Failed",Toast.LENGTH_SHORT).show()
                }

        }

        else{

                Toast.makeText(this,"Please Select an Image",Toast.LENGTH_SHORT).show()

        }


    }

    private val pickimage=registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        if(uri != null){
            binding.selectedimage.setImageURI(uri)
            foodImage=uri
        }

    }
}