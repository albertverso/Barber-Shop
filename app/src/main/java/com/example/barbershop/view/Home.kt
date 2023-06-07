package com.example.barbershop.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.barbershop.R
import com.example.barbershop.databinding.FragmentHomeBinding
import com.example.barbershop.models.User

class Home : Fragment(R.layout.fragment_home) {
    private val args: HomeArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.parseColor("#2C3E50")


        val NameUser = args.user.Nome

        view.findViewById<TextView>(R.id.txtNomeUsuario).text = "Bem-vindo, $NameUser"

        view.findViewById<Button>(R.id.btAgendar).setOnClickListener {
            val action = HomeDirections.actionHome3ToAgendamento(
                user = User (
                    Nome = NameUser
                )
            )
            findNavController().navigate(action)
        }
    }
}