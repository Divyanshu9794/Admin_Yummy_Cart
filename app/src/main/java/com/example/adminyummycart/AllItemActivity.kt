package com.example.adminyummycart

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminyummycart.adapter.MenuItemAdapter
import com.example.adminyummycart.databinding.ActivityAllItemBinding
import com.example.adminyummycart.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList

class AllItemActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private  var menuItems: ArrayList<AllMenu> = ArrayList()
    private val binding:ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        databaseReference =FirebaseDatabase.getInstance().reference
        retrieveMenuItem()



        binding.backbutton.setOnClickListener {
            finish()
        }

    }

    private fun retrieveMenuItem() {
        database =FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference = database.reference.child("menu")

        //fetch data From data base

        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear existing data
                menuItems.clear()

                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }

                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }

        })
    }

    private fun setAdapter(){
        val adapter = MenuItemAdapter(this@AllItemActivity,menuItems,databaseReference){position->
            deleteMenuItems(position)
        }

        binding.menurecyclerview.layoutManager=LinearLayoutManager(this)
        binding.menurecyclerview.adapter = adapter

    }

    private fun deleteMenuItems(position: Int) {

        val menuItemToDelete = menuItems[position]
        val menuItemKey = menuItemToDelete.key
        val foodMenuReference =database.reference.child("menu").child(menuItemKey!!)
        foodMenuReference.removeValue().addOnCompleteListener { task->
            if(task.isSuccessful){
                menuItems.removeAt(position)
                binding.menurecyclerview.adapter?.notifyItemRemoved(position)


            }
            else{
                Toast.makeText(this, "Item Deletion Failed", Toast.LENGTH_SHORT).show()
            }
        }


    }
}