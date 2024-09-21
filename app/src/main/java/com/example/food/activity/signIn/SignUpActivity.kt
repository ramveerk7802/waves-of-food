package com.example.food.activity.signIn

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.R
import com.example.food.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private val binding:ActivitySignUpBinding by lazy{
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference
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

        // initialized firebase and database

        auth=Firebase.auth
        database = Firebase.database.reference


        dialog = Dialog(this@SignUpActivity)
        dialog.setTitle("loading...")
        dialog.setCancelable(false)

        // switch activity
        binding.already.setOnClickListener{
            startActivity(Intent(this@SignUpActivity,LogInActivity::class.java));
            finish()
        }

        binding.createAccountButton.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val plainPassword = binding.edtPassword.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            if(email.isBlank() || plainPassword.isBlank() || name.isBlank())
                Toast.makeText(this,"Fill the All Detail !!",Toast.LENGTH_SHORT).show()
            else{
                createAccount(email,plainPassword,name)
            }
        }


    }

    private fun createAccount(email: String, plainPassword: String, name: String) {

        auth.createUserWithEmailAndPassword(email,plainPassword)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    user?.sendEmailVerification()?.addOnCompleteListener { task1->
                        if(task1.isSuccessful){
                            Toast.makeText(this,"send verification link, Verify your email !!",Toast.LENGTH_SHORT).show()
                            auth.signOut()
                            val intent = Intent(this,LogInActivity::class.java)
                                .apply {
                                    putExtra("name",name)
                                }
                            startActivity(intent)
                            finish()
                        }
                        else{
                            auth.signOut()
                            binding.edtPassword.text?.clear()
                            binding.edtName.text?.clear()
                            binding.edtEmail.text?.clear()
                            Toast.makeText(this,"Failed to send Verification Email !!",Toast.LENGTH_SHORT).show()
                        }

                    }
                }
                else{
                    Log.e("Account",task.exception.toString())
                }
            }
            .addOnFailureListener {
                Toast.makeText(this,"Failed to create Account",Toast.LENGTH_SHORT).show()
            }

    }
}