package com.example.barbershop.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val view = binding.root

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

        return view
    }
}