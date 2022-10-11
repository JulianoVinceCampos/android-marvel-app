package com.example.marvelapp.presetation.teste

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _myLiveData = MutableLiveData<Int>()
    val myLiveData: LiveData<Int> get() = _myLiveData

    fun soma(number: Int) {
        _myLiveData.postValue(number + 1)
    }

    fun subtrai(number: Int) {
        _myLiveData.postValue(number - 1)
    }
}