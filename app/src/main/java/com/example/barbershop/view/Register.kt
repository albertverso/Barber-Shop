package com.example.barbershop.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.barbershop.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class Register : Fragment(R.layout.fragment_register) {

    private val auth = FirebaseAuth.getInstance()
    private var UserId : String = ""

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.parseColor("#E74C3C")

        val nome = view.findViewById<EditText>(R.id.editNomeRegister)
        val email  = view.findViewById<EditText>(R.id.editEmailRegister)
        val senha  = view.findViewById<EditText>(R.id.editSenhaRegister)

        view.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        view.findViewById<TextView>(R.id.txtActionBar).text = "Cadastro"


        view.findViewById<Button>(R.id.btLogin).setOnClickListener {

            when{
                nome.length() < 1 -> {
                    mensagemError(it, "Coloque o seu nome!")
                }
                email.length() < 1 -> {
                    mensagemError(it, "Preencha seu Email")
                }
                senha.length() < 1 -> {
                    mensagemError(it, "Preencha a senha!")
                }
                senha.length() < 6 -> {
                    mensagemError(it, "A senha precisa ter pelo menos 6 caracteres!")
                }
                senha.length() >= 12 -> {
                    mensagemError(it, "A senha precisa ter no máximo 12 caracteres!")
                }
                else -> {
                    auth.createUserWithEmailAndPassword(email.text.toString(), senha.text.toString())
                        .addOnCompleteListener {
                                cadastro ->
                            if(cadastro.isSuccessful){
                                SaveUserData(nome.text.toString(), email.text.toString())
                                mensagemSuccessful(it, "Cadastro realizado com sucesso")
                                requireActivity().onBackPressed()
                            }
                        }
                        .addOnFailureListener {exeception ->
                            val mensagemAuth = when(exeception){
                                is FirebaseAuthInvalidCredentialsException -> "Digite um email válido!"
                                is FirebaseAuthUserCollisionException -> "Esta conta ja foi cadastrada!"
                                is FirebaseNetworkException -> "Sem conexão com a internet!"
                                else -> "Erro ao cadastrar usuário"
                            }
                            mensagemError(it, mensagemAuth)
                    }
                }
            }
        }
    }

    private fun SaveUserData(nome: String, email: String) {
        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        val dadosUsuarios = hashMapOf(
            "Nome" to nome,
            "Email" to email,
        )
        UserId = auth.currentUser!!.uid

        db.collection("Usuarios").document(UserId).set(dadosUsuarios)
    }


    private fun mensagemError(view : View, mensagemError : String) {
        val snackbar = Snackbar.make(view,mensagemError, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#FF0000"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun mensagemSuccessful(view : View, mensagemSuccessful : String) {
        val snackbar = Snackbar.make(view,mensagemSuccessful, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor("#10B519"))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }
}