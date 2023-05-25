package com.example.barbershop.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.barbershop.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.btLogin.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val senha = binding.editSenha.text.toString()

            when{
                nome.isEmpty() -> {
                    mensagem(it, "Coloque o seu nome!")
                }
                senha.isEmpty() -> {
                    mensagem(it, "Preencha a senha!")
                }
                senha.length <= 5 -> {
                    mensagem(it, "A senha precisa ter pelo menos 6 caracteres!")
                }
                else -> {
                    navegarProHome(nome)
                }
            }
        }

    }

    private fun mensagem(view : View, mensagem : String) {
        val snackbar = Snackbar.make(view,mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun navegarProHome(nome: String) {
        val intent = Intent(this, Home::class.java)
        intent.putExtra("nome",nome)
        startActivity(intent)
    }

}
