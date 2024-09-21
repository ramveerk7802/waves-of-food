package com.example.food.adaptor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.food.Data.CartModel
import com.example.food.R
import com.example.food.databinding.CartLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso

class CartAdaptor(private var cartList: MutableList<CartModel>,private val auth :FirebaseAuth,private val database:DatabaseReference) :
    RecyclerView.Adapter<CartAdaptor.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(CartLayoutBinding.inflate(inflater,parent,false),this,auth,database)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cartList[position],position)
    }


    class ViewHolder (private val binding: CartLayoutBinding,private val adaptor: CartAdaptor,private val auth: FirebaseAuth,private val database: DatabaseReference): RecyclerView.ViewHolder(binding.root){


        fun bind(obj: CartModel, position: Int){

            binding.apply {
                var price ="₹ "
                binding.itemNameText.text=obj.itemName
                Picasso.get().load(obj.image).into(binding.itemImage)
                price+=obj.price.toString()
                binding.price.text=price
                binding.noOfItemText.text= obj.quantity.toString()


                //handle the button
                deleteButton.setOnClickListener{
                    val dialog = AlertDialog.Builder(binding.root.context)
                    dialog.setIcon(R.drawable.delete)
                    dialog.setTitle("Delete")
                    dialog.setCancelable(false)
                    dialog.setMessage("Are you sure you want to delete this item?")
                    dialog.setPositiveButton("Yes"){_,_ ->
                        val itemPosition = adapterPosition
                        if(itemPosition!=RecyclerView.NO_POSITION){
                            deleteItem(itemPosition)

                        }}
                    dialog.setNegativeButton("No"){ dialogInterface,_ ->
                        dialogInterface.dismiss()
                    }
                    dialog.show()


                }

                // handle increase and decrease quantity
                plusButton.setOnClickListener{
                    increaseQuantity(position)
                }
                minusButton.setOnClickListener{
                    decreaseQuantity(position)
                }
            }

        }

         private fun deleteItem(position: Int){
            val obj =  adaptor.cartList[position]
             val userId = auth.currentUser?.uid
             val cartReference = database.child("User").child(userId!!).child("Cart")
             cartReference.child(obj.itemId!!).removeValue().addOnSuccessListener {
                 adaptor.notifyItemRemoved(position)
                 adaptor.cartList.removeAt(position)

             }


        }
        private fun increaseQuantity(position: Int){
            val obj = adaptor.cartList[position]
            val userId = auth.currentUser?.uid
            obj.apply {
                if(this.quantity!!<10) {
                    val newQuantity = this.quantity!! + 1
                    quantity = newQuantity
                    binding.noOfItemText.text = newQuantity.toString()
                    val  cartReference = database.child("User").child(userId!!).child("Cart")
                    cartReference.child(this.itemId!!)
                        .child("quantity")
                        .setValue(newQuantity)

                }



            }
        }

        private fun decreaseQuantity(position: Int){
            val obj = adaptor.cartList[position]
            val userId = auth.currentUser?.uid
            obj.apply {
                if(this.quantity!!>1){
                    val newQuantity = this.quantity!! - 1
                    quantity = newQuantity
                    binding.noOfItemText.text = newQuantity.toString()

                    val cartReference = database.child("User").child(userId!!).child("Cart")
                    cartReference.child(this.itemId!!)
                        .child("quantity")
                        .setValue(newQuantity)
                }
            }
//            if(obj.quantity!!>1){
//                obj.quantity=(obj.quantity!!-1)
//                binding.noOfItemText.text=obj.quantity.toString()
//                adaptor.notifyItemChanged(position)
//            }
        }

    }
     @SuppressLint("NotifyDataSetChanged")
     fun updateList(newList:MutableList<CartModel>){
        this.cartList=newList
       this.notifyDataSetChanged()
    }
}