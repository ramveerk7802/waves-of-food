package com.example.food.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adaptor.MenuAdaptor
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)
        val menuList = mutableListOf<PopularModel>()
        menuList.add(PopularModel(R.drawable.buger_banner_a,"First",100))
        menuList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        menuList.add(PopularModel(R.drawable.buger_banner_a,"First burger",100))
        menuList.add(PopularModel(R.drawable.icecream_banner_a,"Ice Cream",160))
        menuList.add(PopularModel(R.drawable.pizza_banner_b,"Pizza seccond",120))
        menuList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        menuList.add(PopularModel(R.drawable.icecream_banner_b," ice cream",100))
        menuList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        val adapter = MenuAdaptor(menuList)
        binding.menuRecycleView.layoutManager=LinearLayoutManager(requireContext())
        binding.menuRecycleView.adapter=adapter
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//
//
//    }
    companion object {

    }
}