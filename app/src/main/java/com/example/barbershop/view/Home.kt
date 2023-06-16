package com.example.barbershop.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.barbershop.R
import com.example.barbershop.models.ViewModelApp
import com.google.firebase.auth.FirebaseAuth

class Home : Fragment(R.layout.fragment_home) {
    private val viewModel: ViewModelApp by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.parseColor("#2C3E50")

        view.findViewById<TextView>(R.id.txtNomeUsuario).text = "Bem vindo, ${viewModel.userName.value}"

        view.findViewById<Button>(R.id.btAgendar).setOnClickListener {
            val action = HomeDirections.actionHome3ToAgendamento()
            findNavController().navigate(action)
        }
    }
}