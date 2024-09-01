package com.example.food.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.Adaptor.MenuAdaptor
import com.example.food.Data.ItemModel
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding:FragmentMenuBottomSheetBinding
    private lateinit var adaptor: MenuAdaptor
    private lateinit var menuList:MutableList<ItemModel>
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialized firebase and database
        auth =Firebase.auth
        database = Firebase.database.reference

        menuList = mutableListOf<ItemModel>()

        // fetch the data

        val menuReference = database.child("AllMenu")

        menuReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(items in snapshot.children){
                    val model = items.getValue(ItemModel::class.java)
                    if(model!=null)
                        menuList.add(model)
                }
                adaptor.updateList(menuList)

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

        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

        adaptor = MenuAdaptor(menuList)
        binding.menuRecycleView.layoutManager=LinearLayoutManager(requireContext())
        binding.menuRecycleView.adapter=adaptor
        return binding.root
    }

    companion object {

    }
}