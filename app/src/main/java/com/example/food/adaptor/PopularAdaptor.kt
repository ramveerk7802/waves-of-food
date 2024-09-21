package com.example.food.adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.activity.FoodNameActivity
import com.example.food.Data.PopularModel
import com.example.food.databinding.ItemLayoutBinding

class PopularAdaptor(private val list:MutableList<PopularModel>) : RecyclerView.Adapter<PopularAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val obj=list[position]
        holder.bind(obj)


    }

    override fun getItemCount(): Int {
        return this.list.size
    }
    class ViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(model: PopularModel){
                var price="$"
                price += model.price.toString()
                binding.price.text=price
                binding.itemNameText.text = model.itemName
                binding.itemImage.setImageResource(model.image)
                binding.restaurantName.text="Apna Restaurant"

                binding.root.setOnClickListener{
                    val context = binding.root.context
                    val intent = Intent(context,FoodNameActivity::class.java)
                        .apply {
                            putExtra("name",model.itemName)
                            putExtra("image",model.image)
                        }
                    context.startActivity(intent)
                }
            }

    }
}