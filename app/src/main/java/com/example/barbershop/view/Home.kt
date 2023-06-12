package com.example.barbershop.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.barbershop.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore


class Home : Fragment(R.layout.fragment_home) {
    private val auth = FirebaseAuth.getInstance()
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private var UserId : String = ""

    @SuppressLint("SetTextI18n", "CommitTransaction")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.parseColor("#2C3E50")


        view.findViewById<Button>(R.id.btAgendar).setOnClickListener {
            val action = HomeDirections.actionHome3ToAgendamento()
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        UserId = auth.currentUser!!.uid
        db.collection("Usuarios").document(UserId).addSnapshotListener(EventListener { value, error ->
            if(value!= null) {
                view?.findViewById<TextView>(R.id.txtNomeUsuario)?.text = value.getString("Nome")
            }
        })
    }
}