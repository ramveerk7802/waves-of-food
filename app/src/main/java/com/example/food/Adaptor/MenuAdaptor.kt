package com.example.food.Adaptor

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Activity.FoodNameActivity
import com.example.food.Data.ItemModel
import com.example.food.Data.PopularModel
import com.example.food.R
import com.example.food.databinding.ItemLayoutBinding
import com.example.food.databinding.MenuItemLayoutBinding
import com.squareup.picasso.Picasso

class MenuAdaptor (private var menuItemList:MutableList<ItemModel>):RecyclerView.Adapter<MenuAdaptor.MenuViewHolder>() {



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


        init{
            binding.root.setOnClickListener{
                val position = adapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    val obj = menuItemList[position]
                   val context = binding.root.context
                   val intent = Intent(context, FoodNameActivity::class.java)
                       .apply {
                           putExtra("name", obj.foodName)
                           putExtra("image", obj.foodImage)
                           putExtra("desc", obj.foodDesc)
                           putExtra("price",obj.foodPrice)
                       }
                    context.startActivity(intent)
                }
            }
        }

         fun bind(position: Int){
             binding.apply {
                 val obj = menuItemList[position]
                 val money  = "₹ "+obj.foodPrice.toString()
                 price.text=money
                 Picasso.get().load(obj.foodImage).into(itemImage)
                 itemNameText.text=obj.foodName
             }




        }
    }
     @SuppressLint("NotifyDataSetChanged")
     fun updateList(filterList : MutableList<ItemModel>){
        this.menuItemList=filterList
        this.notifyDataSetChanged()
    }



}