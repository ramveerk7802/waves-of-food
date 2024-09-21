package com.example.food.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.adaptor.BuyAgainAdaptor
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
   private lateinit var  binding : FragmentHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderList = mutableListOf<PopularModel>()
        orderList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",220))
        orderList.add(PopularModel(R.drawable.icecream_banner_a,"Ice Cream",220))
        orderList.add(PopularModel(R.drawable.buger_banner_a,"Burger",210))
        orderList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",220))
        orderList.add(PopularModel(R.drawable.pizza_banner_b,"Pizza",220))
        val adaptor = BuyAgainAdaptor(orderList)

        binding.historyRecycleView.layoutManager=LinearLayoutManager(context)
        binding.historyRecycleView.adapter=adaptor
    }

    companion object {

    }
}