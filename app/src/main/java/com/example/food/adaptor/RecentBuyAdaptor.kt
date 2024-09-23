package com.example.food.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.databinding.RecentBuyItemLayoutBinding
import com.squareup.picasso.Picasso

class RecentBuyAdaptor(
    val itemName:ArrayList<String>,
    val itemImage:ArrayList<String>,
    val itemQuantity:ArrayList<Int>,
    val itemPrice:ArrayList<Int>):RecyclerView.Adapter<RecentBuyAdaptor.ViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val binding = RecentBuyItemLayoutBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemQuantity.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolder(private val binding:RecentBuyItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(position: Int) {
                if (position != RecyclerView.NO_POSITION) {
                    binding.apply {
                        binding.itemNameText.text = itemName[position]
                        quantity.text = "Quantity : "+itemQuantity[position].toString()
                        price.text = buildString {
                            append("₹ ")
                            append((itemPrice[position] * itemQuantity[position]).toString())
                        }
                        Picasso.get().load(itemImage[position]).into(itemImageShapable)


                    }
                }
            }
    }
}