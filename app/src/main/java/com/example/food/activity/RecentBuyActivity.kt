package com.example.food.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.R
import com.example.food.adaptor.RecentBuyAdaptor
import com.example.food.databinding.ActivityRecentBuyBinding

class RecentBuyActivity : AppCompatActivity() {
    private val binding:ActivityRecentBuyBinding by lazy{
        ActivityRecentBuyBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemName = intent?.getStringArrayListExtra("itemName")
        val itemImage = intent?.getStringArrayListExtra("itemImage")
        val itemQuantity =intent?.getIntegerArrayListExtra("itemQuantity")
        val itemPrice = intent?.getIntegerArrayListExtra("itemPrice")

        binding.recentOrderRecycleView.apply {
            layoutManager = LinearLayoutManager(this@RecentBuyActivity)
            val adapter= RecentBuyAdaptor(itemName!!,itemImage!!,itemQuantity!!,itemPrice!!)
            this.adapter=adapter
        }
    }
}