package com.example.food.Activity.SignIn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.denzcoskun.imageslider.animations.Toss
import com.example.food.Data.UserModel
import com.example.food.MainActivity
import com.example.food.R
import com.example.food.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database :DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // initialized of firebase and database
        auth=Firebase.auth
        database = Firebase.database.reference

        binding.dontText.setOnClickListener{
            startActivity(Intent(this@LogInActivity,SignUpActivity::class.java))
            finish()
        }
        binding.logInButton.setOnClickListener{
            val email = binding.edtEmail.text.toString().trim()
            val plainPassword = binding.edtPassword.text.toString().trim()
            if(email.isBlank() || plainPassword.isBlank()){
                Toast.makeText(this,"Fill all  Details !!",Toast.LENGTH_SHORT).show()
            }
            else{
                signInWith(email,plainPassword)
            }
        }

    }

    private fun signInWith(email: String, plainPassword: String) {

        auth.signInWithEmailAndPassword(email,plainPassword)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    user?.reload()?.addOnCompleteListener { reloadTask->
                        if(reloadTask.isSuccessful){
                            if(user.isEmailVerified){
                                val name = intent?.getStringExtra("name").orEmpty()
                                saveData(name,email,plainPassword,user.uid)

                            }
                            else{
                                auth.signOut()
                                Toast.makeText(this,"Email not verified",Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            auth.signOut()
                            Toast.makeText(this,"Reloading user data!!",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    auth.signOut()
                    Toast.makeText(this,"Register first then login!!",Toast.LENGTH_SHORT).show()                }
            }

    }

    private fun saveData(name: String, email: String, plainPassword: String,uid:String) {
        val userReference = database.child("User").child(uid)
        val model = UserModel(name,email,plainPassword)
        userReference.setValue(model)
            .addOnSuccessListener {
                val intent1 =  Intent(this,MainActivity::class.java)
                startActivity(intent1)
                finish()
            }
    }
}