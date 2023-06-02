package com.example.barbershop.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.barbershop.R
import com.example.barbershop.models.User
import com.google.android.material.snackbar.Snackbar

class Login : Fragment(R.layout.fragment_login) {

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btLogin).setOnClickListener {
            val nome = view.findViewById<EditText>(R.id.editNome)
            val senha = view.findViewById<EditText>(R.id.editSenha)

            when{
                nome.length() < 1 -> {
                    mensagem(it, "Coloque o seu nome!")
                }
                senha.length() < 1 -> {
                    mensagem(it, "Preencha a senha!")
                }
                senha.length() <= 5 -> {
                    mensagem(it, "A senha precisa ter pelo menos 6 caracteres!")
                }
                else -> {
                    val action = LoginDirections.actionLoginToHome3(
                       user = User (
                                    "$nome"
                               )
                    )

                    findNavController().navigate(action)
                }
            }
        }

        view.findViewById<Button>(R.id.btRegister).setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
            setStatusBarColor(color = Color.parseColor("#E74C3C"))
        }

    }

    private fun mensagem(view : View, mensagem : String) {
        val snackbar = Snackbar.make(view,mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun setStatusBarColor(color: Int) {
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = color
    }
}
