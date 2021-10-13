package com.fredrueda.pruebaandroid.ui.user

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel


class MyViewModel : ViewModel() {
    private var stringMutableLiveData: MutableLiveData<Int>? = null
    fun init() {
        stringMutableLiveData = MutableLiveData()
    }

    fun sendData(msg: Int) {
        stringMutableLiveData!!.value = msg
    }

    val message: LiveData<Int>?
        get() = stringMutableLiveData
}