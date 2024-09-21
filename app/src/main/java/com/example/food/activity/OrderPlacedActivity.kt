package com.example.food.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.MainActivity
import com.example.food.R
import com.example.food.databinding.ActivityOrderPlacedBinding

class OrderPlacedActivity : AppCompatActivity() {
    private  val binding:ActivityOrderPlacedBinding by lazy {
        ActivityOrderPlacedBinding.inflate(layoutInflater)
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

        binding.okBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finishAffinity()
        }


    }
}