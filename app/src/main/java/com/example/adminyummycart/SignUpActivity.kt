package com.example.adminyummycart

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminyummycart.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {4
    private val binding:ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        val locationList = arrayOf("Allahabad","Varanasi","Gorakhpur","Basti")
        val adapter =ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView = binding.lisoflocation
        autoCompleteTextView.setAdapter(adapter)

    }
}