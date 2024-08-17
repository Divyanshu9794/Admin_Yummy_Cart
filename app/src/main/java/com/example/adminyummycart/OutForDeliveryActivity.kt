package com.example.adminyummycart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminyummycart.adapter.DeliveryAdapter
import com.example.adminyummycart.databinding.ActivityOutForDeliveryBinding
import com.example.adminyummycart.model.OrderDetails
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding : ActivityOutForDeliveryBinding by lazy{
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    private lateinit var database: FirebaseDatabase
    private var listOfCompleteOrderList:ArrayList<OrderDetails> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.backbutton.setOnClickListener {
            finish()
        }

        //retrieve and display completed order

        retrieveCompleteOrderDetail()




    }

    private fun retrieveCompleteOrderDetail() {
        database = FirebaseDatabase.getInstance()
        val completeOrderReference = database.reference.child("CompletedOrder")

            .orderByChild("currentItem")

        completeOrderReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfCompleteOrderList.clear()
                for (orderSnapshot in snapshot.children){

                    val completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrderList.add(it)
                    }


                }
                listOfCompleteOrderList.reverse()

                setDataIntoRecyclerView()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setDataIntoRecyclerView() {

        val customerName = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()


        for(order in listOfCompleteOrderList){
            order.userName?.let { customerName.add(it) }
            moneyStatus.add(order.paymentReceived)
        }

        val adapter = DeliveryAdapter(customerName,moneyStatus)
        binding.DeliveryRecyclerView.adapter = adapter
        binding.DeliveryRecyclerView.layoutManager= LinearLayoutManager(this)
    }
}