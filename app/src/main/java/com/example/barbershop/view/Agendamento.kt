package com.example.barbershop.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.barbershop.R
import com.example.barbershop.databinding.FragmentAgendamentoBinding
import com.example.barbershop.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class Agendamento : Fragment(R.layout.fragment_agendamento) {
    private val calendar: Calendar = Calendar.getInstance()
    private var data: String = ""
    private var hora: String = ""
    private val args: AgendamentoArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = Color.parseColor("#E74C3C")

        val nome = args.user.Nome

        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
        datePicker?.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            var dia = dayOfMonth.toString()
            val mes: String

            if (dayOfMonth < 10) {
                dia = "0$dayOfMonth"
            }
            if (monthOfYear < 10){
                mes = "" + (monthOfYear+1)
            }
            else{
                mes = (monthOfYear + 1).toString()
            }

            data = "$dia / $mes / $year"
        }

        view.findViewById<TimePicker>(R.id.timePicker)?.setOnTimeChangedListener { _, hourOfDay, minute ->
            val minuto: String

            if(minute < 10){
                minuto = "0$minute"
            }else{
                minuto = minute.toString()
            }

            hora = "$hourOfDay: $minuto"
        }

        view.findViewById<TimePicker>(R.id.timePicker)?.setIs24HourView(true )

        view.findViewById<Button>(R.id.btMarcarAgendamento)?.setOnClickListener {
            val barbeiro1 = view.findViewById<CheckBox>(R.id.barbeiro1)
            val barbeiro2 = view.findViewById<CheckBox>(R.id.barbeiro2)

            when{
                hora.isEmpty() -> {
                    mensagem(it, "Preencha o horário")
                }
                hora < "8:00" && hora > "19:00" -> {
                    mensagem(it, "Barbearia esta fechada - hórario de atendimento das 08:00 ás 19:00 horas!")
                }
                data.isEmpty() -> {
                    mensagem(it, "Escolha uma data!")
                }
                barbeiro1.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it, nome.toString(),"barbeiro1",data,hora )
                }
                barbeiro2.isChecked && data.isNotEmpty() && hora.isNotEmpty() -> {
                    salvarAgendamento(it, nome.toString(),"barbeiro2",data,hora )
                }
                else -> {
                    mensagem(it, "Escolha um barbeiro!")
                }
            }
        }

        view.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        view.findViewById<TextView>(R.id.txtActionBar).text = "Agendamento"
    }
    private fun mensagem(view: View, mensagem: String){
        val snackbar = Snackbar.make(view, mensagem, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.RED)
        snackbar.show()
    }

    private fun salvarAgendamento(view: View, cliente: String, barbeiro: String, data: String, hora: String) {

        val db = FirebaseFirestore.getInstance()

        val dadosUsuarios = hashMapOf(
            "cliente" to cliente,
            "barbeiro" to barbeiro,
            "data" to data,
            "hora" to hora,
        )

        db.collection("agendamento").document(cliente).set(dadosUsuarios).addOnCompleteListener {
            mensagem(view, "Agendamento realizado com sucesso!")
        }.addOnFailureListener {
            mensagem(view,"Erro no servidor!")
        }
    }
}