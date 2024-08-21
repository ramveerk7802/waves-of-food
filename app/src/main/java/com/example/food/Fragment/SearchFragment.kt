package com.example.food.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adaptor.MenuAdaptor
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding

    private val filterMenuList = mutableListOf<PopularModel>()
    private val originalMenuList = mutableListOf<PopularModel>()
    private lateinit var adaptor: MenuAdaptor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        originalMenuList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        originalMenuList.add(PopularModel(R.drawable.icecream_banner_b," ice cream",100))
        originalMenuList.add(PopularModel(R.drawable.buger_banner_a,"First",100))
        originalMenuList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        originalMenuList.add(PopularModel(R.drawable.buger_banner_a,"First burger",100))
        originalMenuList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        originalMenuList.add(PopularModel(R.drawable.icecream_banner_a,"Ice Cream",160))
        originalMenuList.add(PopularModel(R.drawable.pizza_banner_b,"Pizza seccond",120))
        setupSearchView()
        adaptor = MenuAdaptor(originalMenuList)
        binding.searchRecycleView.layoutManager= LinearLayoutManager(requireContext())
        binding.searchRecycleView.adapter=adaptor


        return binding.root
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
               filterMenuItem(newText)
                return true
            }
        })
    }


    private fun filterMenuItem(query: String) {
        filterMenuList.clear()
        originalMenuList.forEach { item ->
            if(item.itemName.contains(query, ignoreCase = true)){
                filterMenuList.add(item)
            }
        }
        adaptor.updateList(filterMenuList)
    }

    companion object {

    }
}