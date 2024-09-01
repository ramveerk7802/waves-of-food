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
import com.example.food.Data.ItemModel
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.databinding.FragmentSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding

    private lateinit var filterMenuList : MutableList<ItemModel>
    private lateinit var originalMenuList:MutableList<ItemModel>
    private lateinit var adaptor: MenuAdaptor
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize firebase and database

        auth=Firebase.auth
        database = Firebase.database.reference
        originalMenuList = mutableListOf()
        filterMenuList = mutableListOf()

        val menuReference = database.child("AllMenu")

        menuReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(items in snapshot.children){
                    val model = items.getValue(ItemModel::class.java)
                    if(model!=null)
                        originalMenuList.add(model)
                }
                adaptor.updateList(originalMenuList)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)



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
            if(item.foodName?.contains(query, ignoreCase = true) == true){
                filterMenuList.add(item)
            }
        }
        adaptor.updateList(filterMenuList)
    }

    companion object {

    }
}