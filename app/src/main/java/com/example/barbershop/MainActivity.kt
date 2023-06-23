package com.example.barbershop

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.barbershop.databinding.ActivityMainBinding
import com.example.barbershop.databinding.FragmentHomeBinding
import com.example.barbershop.models.ViewModelApp
import com.example.barbershop.view.Agendamento
import com.example.barbershop.view.Home
import com.example.barbershop.view.Register
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ContaPagaApplication : Application()
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: ViewModelApp by viewModels()
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val userName = intent.extras
        val nome = userName?.getString("Name")
        viewModel.setNameUser(nome.toString())

        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.menu_home -> replaceFragment(Home())
                R.id.menu_agendamento -> replaceFragment(Agendamento())
                R.id.menu_user -> replaceFragment(Register())
                else -> {

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.Layout_nav_menu, fragment)
        fragmentTransaction.commit()
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

