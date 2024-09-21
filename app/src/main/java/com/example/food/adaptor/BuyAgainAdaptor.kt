package com.example.food.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Data.PopularModel
import com.example.food.databinding.BuyLayoutBinding


class BuyAgainAdaptor(private val orderList:MutableList<PopularModel>) : RecyclerView.Adapter<BuyAgainAdaptor.ViewHolder>() {


     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val inflater = LayoutInflater.from(parent.context)
         val binding = BuyLayoutBinding.inflate(inflater,parent,false)
         return ViewHolder(binding)
     }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
         return orderList.size
     }




     inner class ViewHolder( private val binding: BuyLayoutBinding): RecyclerView.ViewHolder(binding.root){
          fun bind(position: Int){
            binding.apply {
                var obj = orderList[position]
                var price = "$ "
                price+= obj.price.toString()
                binding.price.text = price
                binding.itemImage.setImageResource(obj.image)
                binding.itemNameText.text=obj.itemName
            }

         }
     }

 }