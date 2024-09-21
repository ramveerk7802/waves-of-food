package com.example.food.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.food.adaptor.MenuAdaptor
import com.example.food.Data.ItemModel
import com.example.food.R
import com.example.food.databinding.FragmentHomeBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private  lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth;
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initialize firebase
        auth = Firebase.auth
        database = Firebase.database.reference

        // open for bottom sheetDialog
        binding.viewMenuButton.setOnClickListener{
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(childFragmentManager,"Menu Bottom sheet")
        }

        val imageList =ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.buger_banner_a,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.pizza_banner_a,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.pizza_banner_b,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.icecream_banner_b,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.salad_banner_a,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.salad_banner_b,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.icecream_banner_a,ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList)

        binding.imageSlider.setItemClickListener(object :ItemClickListener{
            override fun doubleClick(position: Int) {
                return
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList.get(position)
                val itemMessage = "Select item : $position"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_SHORT).show()

            }
        })
        val popularList= mutableListOf<ItemModel>()


        // fetch data from database

        val menuReference = database.child("AllMenu")
        menuReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snapshot1 in snapshot.children){
                    val itemModel = snapshot1.getValue(ItemModel::class.java)
                    itemModel?.let { popularList.add(it) }
                }
                // display popular adaptor
                showPopularAdaptor(popularList,auth,database)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })




    }

    private fun showPopularAdaptor(popularList: MutableList<ItemModel>,auth: FirebaseAuth,database: DatabaseReference) {

        val index = popularList.indices.toList().shuffled()
        val noOfItemShow =6
        val subSetItem = index.take(noOfItemShow).map { popularList[it] }.toMutableList()

        binding.popularRecycleView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = MenuAdaptor(subSetItem,auth,database)

        }

    }

}