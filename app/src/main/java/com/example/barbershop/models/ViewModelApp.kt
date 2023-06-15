package com.example.barbershop.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelApp : ViewModel(){

    var onSetValue = MutableLiveData<String>("")
    var userName: LiveData<String> = onSetValue


    fun setNameUser(name: String) {
        Log.i("ViewModelApp", name )
        onSetValue.value = name
        Log.i("ViewModelApp",  userName.value.toString() )
    }
}
