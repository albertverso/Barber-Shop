package com.example.barbershop.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.barbershop.R


class Register : Fragment(R.layout.fragment_register) {
    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            requireActivity().onBackPressed()
            setStatusBarColor(color = Color.parseColor("#2C3E50"))
        }

        view.findViewById<TextView>(R.id.txtActionBar).text = "Registrar"
    }

    private fun setStatusBarColor(color: Int) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = color
    }
}