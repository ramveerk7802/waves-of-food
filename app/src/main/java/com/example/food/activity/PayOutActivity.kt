package com.example.food.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.food.Data.CartModel
import com.example.food.Data.OrderDetailModel
import com.example.food.Data.UserModel
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PayOutActivity : AppCompatActivity() {
    private val binding:ActivityPayOutBinding by lazy {
        ActivityPayOutBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var name:String
    private lateinit var address:String
    private lateinit var number:String
    private lateinit var itemName:MutableList<String>
    private lateinit var itemImage:MutableList<String>
    private lateinit var itemQuantity:MutableList<Int>
    private lateinit var itemPrice:MutableList<Int>
    private lateinit var dialog: Dialog
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
        // setUp process dialog
        dialog = Dialog(this)
        dialog.setContentView(R.layout.processing_box_layout)
        dialog.setCancelable(false)

        // initialized firebase
        auth  = Firebase.auth
        database = Firebase.database.reference

        // fetch user data
        val userId = auth.currentUser?.uid
        showUserInformation(userId)

        // initialize all variable and list



        itemImage = mutableListOf()
        itemName= mutableListOf()
        itemPrice= mutableListOf()
        itemQuantity= mutableListOf()


        // calculate total payable amount and  get all order
       var amount=0
        val orderRef = database.child("User").child(userId!!).child("Cart")
        orderRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snapshotChild in snapshot.children){
                    val model = snapshotChild.getValue(CartModel::class.java)
                    model?.let {
                        amount= amount+((it.quantity!!)*it.price!!)
                        itemName.add(it.itemName!!)
                        itemImage.add(it.image!!)
                        itemQuantity.add(it.quantity!!)
                        itemPrice.add(it.price!!)
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
            dialog.show()
            name = binding.edtName.text.toString().trim()
            address = binding.edtAddress.text.toString().trim()
            number = binding.edtPhone.text.toString().trim()
            val numberValid = MobileNumberValid()
            if(name.isEmpty() || address.isEmpty() || number.isEmpty()) {
                Toast.makeText(this@PayOutActivity, "All fields are mandatory", Toast.LENGTH_SHORT)
                    .show()
                dialog.dismiss()
            }
            else if(!numberValid.numberCheck(number)) {
                Toast.makeText(this, "Enter the valid mobile number ", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            else{
                placedOrder(userId,name,address,number,amount)
//                startActivity(Intent(this,OrderPlacedActivity::class.java))
            }


        }

    }

    private fun placedOrder(userId: String, name: String, address: String, number: String,amount:Int) {
        val currentTime = System.currentTimeMillis()
        val orderRef = database.child("OrderDetail")
        val orderKey = orderRef.push().key
        val orderModel = OrderDetailModel(userId,name,address,number, amount,itemName,itemImage,itemQuantity,itemPrice,currentTime,orderAccepted=false,paymentReceived=false,orderKey!!)
        orderRef.child(orderKey)
            .setValue(orderModel)
            .addOnSuccessListener {
                removeFromCart(userId,orderModel,orderKey)
            }
    }

    private fun removeFromCart(userId: String,orderModel:OrderDetailModel,orderKey:String) {
        val cartReference = database.child("User").child(userId).child("Cart")
        cartReference.removeValue().addOnSuccessListener {
            addToOrderHistory(userId,orderModel,orderKey)

        }


    }

    private fun addToOrderHistory(userId: String, orderModel: OrderDetailModel,orderKey: String){
        val historyRef= database.child("User").child(userId).child("OrderHistory").child(orderKey)
        historyRef.setValue(orderModel)
            .addOnSuccessListener {
                dialog.dismiss()
                startActivity(Intent(this@PayOutActivity,OrderPlacedActivity::class.java))
            }

    }


    private fun showUserInformation(userId: String?) {

        lifecycleScope.launch(Dispatchers.IO) {
            val userRef = database.child("User").child(userId!!)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        val model = snapshot.getValue(UserModel::class.java)
                        model?.let {
                            lifecycleScope.launch(Dispatchers.Main) {
                                binding.apply {
                                    edtName.setText(it.name)
                                    edtAddress.setText(it.address)
                                    edtPhone.setText(it.mobile)
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }

    }
}