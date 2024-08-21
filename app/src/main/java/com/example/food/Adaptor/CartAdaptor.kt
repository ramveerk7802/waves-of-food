package com.example.food.Adaptor

import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Data.CartModel
import com.example.food.R
import com.example.food.databinding.CartLayoutBinding

class CartAdaptor (private val cartList :MutableList<CartModel> ) :
    RecyclerView.Adapter<CartAdaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(CartLayoutBinding.inflate(inflater,parent,false),this)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartList[position],position)
    }


    class ViewHolder (private val binding: CartLayoutBinding,private val adaptor: CartAdaptor): RecyclerView.ViewHolder(binding.root){
        fun bind(obj: CartModel, position: Int){
            var price ="$"
            binding.itemNameText.text=obj.itemName
            binding.itemImage.setImageResource(obj.image)
            price+=obj.price.toString()
            binding.price.text=price
            binding.noOfItemText.text= obj.quantity.toString()

            //handle the button

            binding.deleteButton.setOnClickListener{
                val dialog = AlertDialog.Builder(binding.root.context)
                dialog.setIcon(R.drawable.delete)
                dialog.setTitle("Delete")
                dialog.setCancelable(false)
                dialog.setMessage("Are you sure you want to delete this item?")
                dialog.setPositiveButton("Yes"){dialogInterface,listner ->
                    val itemPosition = adapterPosition
                    if(itemPosition!=RecyclerView.NO_POSITION){
                        deleteItem(itemPosition)

                }}
                dialog.setNegativeButton("No"){ dialogInterface,listner ->
                    dialogInterface.dismiss()
                }
                dialog.show()


            }
            binding.minusButton.setOnClickListener{
                decreaseQuantity(position)
            }
            binding.plusButton.setOnClickListener{
                increaseQuantity(position)
            }
        }

         private fun deleteItem(position: Int){
             adaptor.notifyItemRemoved(position)
             adaptor.cartList.removeAt(position)
        }
        private fun increaseQuantity(position: Int){
            val obj = adaptor.cartList[position]
            if(obj.quantity<10){
                obj.quantity=(obj.quantity+1)
                binding.noOfItemText.text=obj.quantity.toString()
                adaptor.notifyItemChanged(position)
            }
        }

        private fun decreaseQuantity(position: Int){
            val obj = adaptor.cartList[position]
            if(obj.quantity>1){
                obj.quantity=(obj.quantity-1)
                binding.noOfItemText.text=obj.quantity.toString()
                adaptor.notifyItemChanged(position)
            }
        }

    }
}