package com.example.food.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.Data.CartModel
import com.example.food.Data.ItemModel
import com.example.food.MainActivity
import com.example.food.R
import com.example.food.databinding.ActivityFoodNameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
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



        val foodId  = intent?.getStringExtra("foodId").toString()

        // fetch data from database

        val foodReference = database.child("AllMenu")
        val userId = auth.currentUser?.uid
        var foodModel:ItemModel?=null
        foodReference.child(foodId)
            .addListenerForSingleValueEvent( object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        foodModel = snapshot.getValue(ItemModel::class.java)
                    }
                    foodModel?.let { showData(it) }

                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })




        binding.addCartButton.setOnClickListener {
            val cartReference = database.child("User").child(userId!!).child("Cart").child(foodModel?.foodId!!)
            foodModel?.let {
                val cartModel = CartModel(image = it.foodImage, itemName = it.foodName, quantity = 1, price = it.foodPrice,it.foodId)
                cartReference.setValue(cartModel)
                    .addOnSuccessListener { showDialog() }
            }
        }

    }

    private fun showData(it: ItemModel) {
        binding.description.text = it.foodDesc
        Picasso.get().load(it.foodImage).into(binding.image)
        binding.itemNameText.text = it.foodName
    }

    private  fun showDialog(){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Add to Cart Successfully.")
            .setCancelable(false)
            .setPositiveButton("Ok"){dialog,_->
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Success")
        alert.show()
    }
}