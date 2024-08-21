package com.example.food.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.Fragment.ConfirmBottomSheetFragment
import com.example.food.R
import com.example.food.databinding.ActivityPayOutBinding

class PayOutActivity : AppCompatActivity() {
    private val binding:ActivityPayOutBinding by lazy {
        ActivityPayOutBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomSheetDialog = ConfirmBottomSheetFragment()
        binding.confirmButton.setOnClickListener{
            bottomSheetDialog.show(supportFragmentManager,"Order placed")
        }

    }
}