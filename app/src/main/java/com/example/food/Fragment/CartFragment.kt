package com.example.food.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Activity.PayOutActivity
import com.example.food.Adaptor.CartAdaptor
import com.example.food.Data.CartModel
import com.example.food.databinding.FragmentCartBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var adaptor: CartAdaptor
    private lateinit var cartList:MutableList<CartModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        database= Firebase.database.reference

        // set adaptor



        binding.cartRecycleView.apply {
            layoutManager= LinearLayoutManager(requireContext())
            adaptor =CartAdaptor(mutableListOf())
            adapter = adaptor
        }

        lifecycleScope.launch {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                cartList = fetchData(userId)

                if (cartList.isEmpty()) {
                    binding.proceedButton.visibility = INVISIBLE
                    binding.noCartText.visibility = VISIBLE
                } else {
                    adaptor.updateList(cartList)
                    binding.proceedButton.visibility = VISIBLE
                    binding.noCartText.visibility = GONE
                }
            }

        }


        binding.proceedButton.setOnClickListener{
            startActivity(Intent(requireContext(),PayOutActivity::class.java))
        }

    }
    private suspend fun fetchData(userId:String):MutableList<CartModel>{

        return withContext(Dispatchers.IO){
            try{
                val cartReference = database.child("User").child(userId).child("Cart")
                val snapshot = cartReference.get().await()
                val dataList = mutableListOf<CartModel>()
                for(data in snapshot.children){
                    val item = data.getValue(CartModel::class.java)
                    item?.let { dataList.add(it) }
                }
                dataList
            }catch (e: Exception){
                     emptyList<CartModel>().toMutableList()
            }
        }

    }


}