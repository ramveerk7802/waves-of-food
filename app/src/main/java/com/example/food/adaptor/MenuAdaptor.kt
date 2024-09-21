package com.example.food.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.food.activity.FoodNameActivity
import com.example.food.Data.CartModel
import com.example.food.Data.ItemModel
import com.example.food.databinding.MenuItemLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class MenuAdaptor(private var menuItemList: MutableList<ItemModel>,private val auth: FirebaseAuth,private val database: DatabaseReference):RecyclerView.Adapter<MenuAdaptor.MenuViewHolder>() {



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
                           putExtra("foodId", obj.foodId)
                       }
                    context.startActivity(intent)
                }
            }
        }

         fun bind(position: Int){

             if(position!=RecyclerView.NO_POSITION) {
                 binding.apply {
                     val obj = menuItemList[position]
                     val money = "₹ " + obj.foodPrice.toString()
                     price.text = money
                     Picasso.get().load(obj.foodImage).into(itemImage)
                     itemNameText.text = obj.foodName

                     // handle the add button
                     obj.apply {
                         addCartButton.setOnClickListener {
                             val userId = auth.currentUser?.uid
                             val cartModel = CartModel(image = foodImage, itemName = foodName,1, price = foodPrice, itemId = foodId)
                             val cartReference = database.child("User").child(userId!!).child("Cart")
                             cartReference.child(foodId!!)
                                 .addListenerForSingleValueEvent(object : ValueEventListener{
                                     override fun onDataChange(snapshot: DataSnapshot) {
                                         if(snapshot.exists()){
                                             val model = snapshot.getValue(CartModel::class.java)
                                             var currentQuantity=0
                                             model?.let { currentQuantity=it.quantity!! }
                                             cartReference.child(foodId).child("quantity").setValue(currentQuantity + 1).addOnSuccessListener { showDialog(binding.root.context) }
                                         }else{
                                                cartReference.child(foodId).setValue(cartModel).addOnSuccessListener { showDialog(binding.root.context) }
                                         }
                                     }

                                     override fun onCancelled(error: DatabaseError) {
                                         TODO("Not yet implemented")
                                     }

                                 })

                         }
                     }


                 }
             }

        }
    }
     @SuppressLint("NotifyDataSetChanged")
     fun updateList(filterList : MutableList<ItemModel>){
        this.menuItemList=filterList
        this.notifyDataSetChanged()
    }

    fun showDialog(context:Context){
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("Add item successfully to Cart.")
            .setCancelable(false)
            .setPositiveButton("Ok"){dialog,_ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle("Success")
        alert.show()

    }



}