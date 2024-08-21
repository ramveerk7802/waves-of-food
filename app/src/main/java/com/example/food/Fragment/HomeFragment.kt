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
import com.example.food.Adaptor.PopularAdaptor
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private  lateinit var binding: FragmentHomeBinding

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
        val popularList= mutableListOf<PopularModel>()
        popularList.add(PopularModel(R.drawable.buger_banner_a,"First burghjklghjkgfhjkdgfh tyuhityghj dgfhghjger",100))
        popularList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        popularList.add(PopularModel(R.drawable.buger_banner_a,"First burger",100))
        popularList.add(PopularModel(R.drawable.icecream_banner_a,"Ice Cream",160))
        popularList.add(PopularModel(R.drawable.pizza_banner_b,"Pizza seccond",120))
        popularList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        popularList.add(PopularModel(R.drawable.icecream_banner_b," ice cream",100))
        popularList.add(PopularModel(R.drawable.pizza_banner_a,"Pizza",160))
        val adapter =PopularAdaptor(popularList)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecycleView.layoutManager=layoutManager
        binding.popularRecycleView.adapter=adapter

    }

    companion object {

    }
}