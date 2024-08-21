package com.example.food.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.food.MainActivity
import com.example.food.R
import com.example.food.databinding.FragmentConfirmBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ConfirmBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding :FragmentConfirmBottomSheetBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goHome.setOnClickListener {
            dismiss()
            startActivity(Intent(requireContext(),MainActivity::class.java))


        }
    }

    companion object {

    }
}