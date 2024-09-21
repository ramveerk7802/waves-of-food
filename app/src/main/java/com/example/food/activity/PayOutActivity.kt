package com.example.food.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.food.Data.CartModel
import com.example.food.Fragment.ConfirmBottomSheetFragment
import com.example.food.R
import com.example.food.databinding.ActivityPayOutBinding
import com.example.food.otherClass.MobileNumberValid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PayOutActivity : AppCompatActivity() {
    private val binding:ActivityPayOutBinding by lazy {
        ActivityPayOutBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference
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

        // initialized firebase

        auth  = Firebase.auth
        database = Firebase.database.reference

        val userId = auth.currentUser?.uid
        // fetch user data
       var amount=0
        val orderRef = database.child("User").child(userId!!).child("Cart")
        orderRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snapshotChild in snapshot.children){
                    val model = snapshotChild.getValue(CartModel::class.java)
                    model?.let {
                        amount= amount+((it.quantity!!)*it.price!!)
                    }
                }
                binding.amount.text = amount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })






        val bottomSheetDialog = ConfirmBottomSheetFragment()
        binding.confirmButton.setOnClickListener{
            val numberValid = MobileNumberValid()
            val name = binding.edtName.text.toString().trim()
            val address  =binding.edtAddress.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()
            if(name.isEmpty() || address.isEmpty() || phone.isEmpty())
                Toast.makeText(this@PayOutActivity,"All fields are mandatory",Toast.LENGTH_SHORT).show()
            else if(!numberValid.numberCheck(phone))
                Toast.makeText(this,"Enter the valid mobile number ",Toast.LENGTH_SHORT).show()
            else{
                startActivity(Intent(this,OrderPlacedActivity::class.java))
            }

//            bottomSheetDialog.show(supportFragmentManager,"Order placed")

        }

    }
}