package com.example.food.Adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Activity.FoodNameActivity
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.databinding.ItemLayoutBinding
import com.example.food.databinding.MenuItemLayoutBinding

class MenuAdaptor (private var menuItemList:MutableList<PopularModel>):RecyclerView.Adapter<MenuAdaptor.MenuViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MenuItemLayoutBinding.inflate(inflater,parent,false)
        return MenuViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return menuItemList.size
    }


    inner class MenuViewHolder(private val binding:MenuItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {



         fun bind(position: Int){
             binding.apply {
                 val obj = menuItemList[position]
                 price.text= obj.price.toString()
                 itemImage.setImageResource(obj.image)
                 itemNameText.text=obj.itemName
             }
             binding.root.setOnClickListener{
                 val obj = menuItemList[position]
                 val context = binding.root.context
                 val intent = Intent(context,FoodNameActivity::class.java)
                     .apply {
                         putExtra("name",obj.itemName)
                         putExtra("image",obj.image)
                     }
                 context.startActivity(intent)
             }
//             notifyItemChanged(position)

        }
    }
     fun updateList(filterList : MutableList<PopularModel>){
        this.menuItemList=filterList
        notifyDataSetChanged()
    }



}