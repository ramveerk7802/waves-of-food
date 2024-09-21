package com.example.food.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.food.Data.UserModel
import com.example.food.databinding.FragmentProfileBinding
import com.example.food.otherClass.MobileNumberValid
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        database = Firebase.database.reference

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Set the view Visibility
        binding.saveBtn.visibility= GONE
        binding.enterDetailText.visibility= GONE
        binding.customerName.isEnabled= false
        binding.edtAddress.isEnabled= false
        binding.edtEmail.isEnabled= false
        binding.phoneNo.isEnabled= false

//        val userId = auth.currentUser?.uid
//        showUserData(userId!!)






        // fetch user data
        lifecycleScope.launch(Dispatchers.IO) {
            val userId = auth.currentUser?.uid
            userId?.let {
                val userReference = database.child("User").child(it)
                userReference.addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val model = snapshot.getValue(UserModel::class.java)
                        model?.let {
                            lifecycleScope.launch(Dispatchers.Main) {
                                binding.apply {
                                    customerName.setText(it.name)
                                    edtEmail.setText(it.email)
                                    edtAddress.setText(it.address)
                                    phoneNo.setText(it.mobile)
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }


        // click on Edit button
        binding.editBtn.setOnClickListener {
            binding.editBtn.visibility= INVISIBLE
            binding.edtEmail.visibility= GONE
            binding.saveBtn.visibility= VISIBLE
            binding.customerName.isEnabled=true
            binding.edtAddress.isEnabled=true
            binding.emailLayout.visibility= GONE
            binding.phoneNo.isEnabled=true
            binding.enterDetailText.visibility= VISIBLE
        }

        // click on save button

        binding.saveBtn.setOnClickListener {
            val numberValid = MobileNumberValid()
            val name  = binding.customerName.text.toString().trim()
            val address = binding.edtAddress.text.toString().trim()
            val number = binding.phoneNo.text.toString().trim()
            if(name.isBlank() || address.isEmpty() || number.isEmpty())
                Toast.makeText(requireContext(),"All fields are mandatory.",Toast.LENGTH_SHORT).show()
            else if (!numberValid.numberCheck(number)) {
                Toast.makeText(
                    requireContext(),
                    "Enter the Valid Mobile number",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                showDialog(name,address,number)
            }
        }
    }

    private fun showDialog(name: String,address: String,number:String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirm save")
            .setMessage("Are you sure you want to save the changes?")
            .setCancelable(false)
            .setPositiveButton("Yes"){dialog,which->
                saveChanges(name,address,number)
                dialog.dismiss()

            }
            .setNegativeButton("No"){dialog,_->
                dialog.dismiss()
            }
        val alertDialog = builder.create()
        alertDialog.show()

    }

    private fun saveChanges(name: String, address: String, number: String){
            val userId = auth.currentUser?.uid
            val profileRef = database.child("User").child(userId!!)
            profileRef.child("name").setValue(name)
            profileRef.child("address").setValue(address)
            profileRef.child("mobile").setValue(number).addOnSuccessListener {
                Toast.makeText(requireContext(),"Changes successfully",Toast.LENGTH_SHORT).show()
                binding.saveBtn.visibility= GONE
                binding.enterDetailText.visibility= GONE
                binding.editBtn.visibility= VISIBLE
                binding.customerName.isEnabled=false
                binding.edtAddress.isEnabled=false
                binding.phoneNo.isEnabled=false
            }

    }


}