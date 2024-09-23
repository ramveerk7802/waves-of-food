package com.example.food.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Data.OrderDetailModel
import com.example.food.adaptor.BuyAgainAdaptor
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.activity.RecentBuyActivity
import com.example.food.databinding.FragmentHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryFragment : Fragment() {
   private lateinit var  binding : FragmentHistoryBinding
   private lateinit var auth: FirebaseAuth
   private lateinit var database:DatabaseReference
   private lateinit var orderList:MutableList<OrderDetailModel>
   private lateinit var itemName: MutableList<String>
   private lateinit var itemImage:MutableList<String>
   private lateinit var itemPrice:MutableList<Int>
    private lateinit var itemQuantity:MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoryBinding.inflate(inflater,container,false)

        auth = Firebase.auth
        database =Firebase.database.reference
        orderList= mutableListOf()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Current user uid
        val userId = auth.currentUser?.uid

        // show recent order
        getOrderAndShow(userId)


        // show previous order
        retrieveHistory()


    }



    private fun getOrderAndShow(userId: String?) {
        val historyRef = database.child("User").child(userId!!).child("OrderHistory")
        historyRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                for(buySnapshot in snapshot.children){
                    val temp = buySnapshot.getValue(OrderDetailModel::class.java)
                    temp?.let {
                        orderList.add(it)
                    }
                }
                orderList.reverse()
                showOrder(orderList)

                }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun showOrder(orderList: MutableList<OrderDetailModel>) {

        if(orderList.isNotEmpty()){
            itemQuantity= orderList[0].itemQuantity!!
            itemPrice= orderList[0].itemPrice!!
            itemName = orderList[0].itemName!!
            itemImage = orderList[0].itemImage!!
            val n = itemImage.size
            binding.apply {
                constraint.visibility= VISIBLE
                recentItemName.text = itemName[n-1]
                recentItemPrice.text= buildString {
                    append("Amount : ")
                    append("₹ ")
                    append(orderList[0].totalAmt.toString())
                }
                Picasso.get().load(itemImage[n-1]).into(recentOrderItemImage)
                cardview.setOnClickListener {
                    val intent = Intent(requireContext(),RecentBuyActivity::class.java).apply {
                        putStringArrayListExtra("itemImage", ArrayList(itemImage))
                        putStringArrayListExtra("itemName",ArrayList(itemName))
                        putIntegerArrayListExtra("itemPrice", ArrayList(itemPrice))
                        putIntegerArrayListExtra("itemQuantity",ArrayList(itemQuantity))


                    }
                    startActivity(intent)
                }

            }
        }else{
            binding.noBuyText.visibility= VISIBLE
            binding.constraint.visibility= GONE
        }

    }

    private fun retrieveHistory() {


    }


}