package com.example.food.Activity.SignIn

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.R
import com.example.food.databinding.ActivitySignUpBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SignUpActivity : AppCompatActivity() {
    private val binding:ActivitySignUpBinding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dialog = Dialog(this@SignUpActivity)
        dialog.setTitle("loading...")
        dialog.setCancelable(false)
        binding.already.setOnClickListener{
            startActivity(Intent(this@SignUpActivity,LogInActivity::class.java));
            finish()
        }

    }
}