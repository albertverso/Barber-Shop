package com.example.barbershop.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.barbershop.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RecoveryPassword : Fragment(R.layout.fragment_password_recovery) {
    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.parseColor("#E74C3C")

        view.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            requireActivity().onBackPressed()
        }
        view.findViewById<TextView>(R.id.txtActionBar).text = context?.resources?.getString(R.string.Recuperando)

        val email = view.findViewById<EditText>(R.id.editEmailRecovery)

        view.findViewById<Button>(R.id.btRecovery).setOnClickListener {
            Submit(view, email)
        }
    }

    private fun Submit(view: View, email: EditText){
        when{
            email.length() < 1 -> {
                mensagemError(view, "Preencha seu Email")
            }
            else -> {
                auth.sendPasswordResetEmail(email.text.toString())
                    .addOnSuccessListener {
                        mensagemSuccessful(view, "Email de Recuperação foi enviada!")
                        findNavController().navigate(R.id.action_recoveryPassword_to_login)
                    }
                    .addOnFailureListener {
                        mensagemError(view, "Falha ao enviar email de recuperaçao")
                    }
            }
        }
    }

    private fun mensagemError(view : View, mensagemError : String) {
        val snackbar = Snackbar.make(view,mensagemError, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun mensagemSuccessful(view : View, message : String) {
        val snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#10B519"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }
}