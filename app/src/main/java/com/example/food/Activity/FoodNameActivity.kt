package com.example.food.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.Data.CartModel
import com.example.food.R
import com.example.food.databinding.ActivityFoodNameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class FoodNameActivity : AppCompatActivity() {
    private val binding :ActivityFoodNameBinding by lazy {
        ActivityFoodNameBinding.inflate(layoutInflater)
    }

    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // initialize firebase and database

        auth=Firebase.auth
        database = Firebase.database.reference



        binding.itemNameText.text=intent?.getStringExtra("name")
        Picasso.get().load(intent?.getStringExtra("image").orEmpty()).into(binding.image)
        binding.description.text= intent?.getStringExtra("desc").orEmpty()

        //  handle the addCart Button
        binding.addCartButton.setOnClickListener {
            val imageUrl:String = intent?.getStringExtra("image").toString()
            val name =intent?.getStringExtra("name")
            val price = intent?.getIntExtra("price",0)
            val userId = auth.currentUser?.uid
            val cartReference = database.child("User").child(userId!!).child("Cart").push()
            val cartModel = CartModel(imageUrl,name,1,price)
            cartReference.setValue(cartModel).addOnSuccessListener {
                showDialog()
            }
        }

    }
    private  fun showDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Add to Cart Successfully.")
            .setCancelable(false)
            .setPositiveButton("Ok"){dialog,_->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("Success")
        alert.show()
    }
}