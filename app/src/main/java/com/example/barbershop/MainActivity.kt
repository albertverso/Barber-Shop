package com.example.barbershop

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.barbershop.models.ViewModelApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ContaPagaApplication : Application()
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: ViewModelApp by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userName = intent.extras
        val nome = userName?.getString("Name")
        viewModel.setNameUser(nome.toString())
        setContentView(R.layout.activity_main)
    }
}
@AndroidEntryPoint
class LoginToRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
    }
}

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    private val usuarioAtual = FirebaseAuth.getInstance().currentUser
    private val auth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var UserId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            if (usuarioAtual != null) {
                val intentMain = Intent(this, MainActivity::class.java)

                setContentView(R.layout.progress_bar)

                UserId = auth.currentUser!!.uid
                db.collection("Usuarios").document(UserId).get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.exists()) {
                            val dados = document.data
                            intentMain.putExtra("Name", dados?.get("Nome").toString())
                            startActivity(intentMain)
                            finish()
                        }
                    }
                }
            } else  {
                val intentLoginToRegister = Intent(this, LoginToRegisterActivity::class.java)
                startActivity(intentLoginToRegister)
                finish()
            }
    }
}

