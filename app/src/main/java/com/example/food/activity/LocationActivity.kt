package com.example.food.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.R
import com.example.food.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity() {
    private val binding :ActivityLocationBinding by lazy {
        ActivityLocationBinding.inflate(layoutInflater)
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val locationList: Array<String> = arrayOf("Uttar Pradesh","Jaipur","Delhi","Karnataka","Mumbai","Goa","West Bengal")
        val adapter:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        binding.listOfLocation.setAdapter(adapter)

        binding.listOfLocation.setOnTouchListener{ _,_ ->
            binding.listOfLocation.showDropDown()
            false
        }

//        binding.listOfLocation.setOnClickListener {
//            binding.listOfLocation.showDropDown()
//        }
//        binding.listOfLocation.setOnClickListener {
//            if (!binding.listOfLocation.isPopupShowing) {
//                binding.listOfLocation.requestFocus()
//                binding.listOfLocation.showDropDown()
//            }
//        }




    }
}