package com.example.adminyummycart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminyummycart.adapter.PendingOrderAdapter
import com.example.adminyummycart.databinding.ActivityPendingOrderBinding
import com.example.adminyummycart.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PendingOrderActivity : AppCompatActivity(),PendingOrderAdapter.OnItemClicked {
    private lateinit var binding:ActivityPendingOrderBinding

    private var listOfName:MutableList<String> =mutableListOf()
    private var listOfTotalPrice :MutableList<String> = mutableListOf()
    private  var listOfImageFirstFoodOrder :MutableList<String> = mutableListOf()
    private var listOfOrderItem:ArrayList<OrderDetails> = arrayListOf()
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseOrderDetails: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityPendingOrderBinding.inflate(layoutInflater)
        binding.backbutton.setOnClickListener {
            finish()
        }

        database = FirebaseDatabase.getInstance()
        databaseOrderDetails = database.reference.child("OrderDetails")
        getOrderDetails()
        setContentView(binding.root)



    }

    private fun getOrderDetails() {
        databaseOrderDetails.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(orderSnapshot in snapshot.children){
                    val orderDetails = orderSnapshot.getValue(OrderDetails::class.java)
                    orderDetails?.let {
                        listOfOrderItem.add(it)
                    }

                }

                addDataToListForRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addDataToListForRecyclerView() {
        for (orderItem in listOfOrderItem){
            orderItem.userName?.let { listOfName.add(it) }
            orderItem.totalPrice?.let { listOfTotalPrice.add(it) }
            orderItem.foodImages?.filterNot { it.isEmpty() }?.forEach {
                listOfImageFirstFoodOrder.add(it)
            }
        }
        setAdapter()

    }

    private fun setAdapter() {
        binding.pendingOrderRecycerView.layoutManager =LinearLayoutManager(this)
        val adapter = PendingOrderAdapter(this,listOfName,listOfTotalPrice,listOfImageFirstFoodOrder,this)
        binding.pendingOrderRecycerView.adapter = adapter
    }

    override fun onItemClickListener(position: Int) {

        val intent = Intent(this,OrderDetailsActivity::class.java)
        val userOrderDetails = listOfOrderItem[position]
        intent.putExtra("UserOrderDetails",userOrderDetails)
        startActivity(intent)

    }
}