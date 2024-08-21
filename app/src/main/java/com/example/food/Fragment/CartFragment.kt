package com.example.food.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Activity.PayOutActivity
import com.example.food.Adaptor.CartAdaptor
import com.example.food.Data.CartModel
import com.example.food.R
import com.example.food.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding


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

        binding.proceedButton.setOnClickListener{
            startActivity(Intent(requireContext(),PayOutActivity::class.java))
        }
        // dummy data
        val cartList = mutableListOf<CartModel>()

        cartList.add(CartModel(R.drawable.icecream_banner_b,"Burger",1,120))
        cartList.add(CartModel(R.drawable.icecream_banner_b,"Burger",2,59))
        cartList.add(CartModel(R.drawable.icecream_banner_b,"Burger",1,220))
        cartList.add(CartModel(R.drawable.icecream_banner_a,"Burger",5,350))
        cartList.add(CartModel(R.drawable.pizza_banner_a,"Burger",3,99))
        cartList.add(CartModel(R.drawable.icecream_banner_b,"Burger",7,159))
        if(cartList.isEmpty()) {
            binding.proceedButton.visibility = View.INVISIBLE
            binding.noCartText.visibility=View.VISIBLE
        }
        else {
            val adapter = CartAdaptor(cartList)
            binding.cartRecycleView.layoutManager = LinearLayoutManager(requireContext())
            binding.cartRecycleView.adapter = adapter

            // handle button

        }

    }

    companion object {


    }
}