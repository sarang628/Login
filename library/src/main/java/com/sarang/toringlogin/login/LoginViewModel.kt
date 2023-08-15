package com.sarang.toringlogin.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@SuppressWarnings("unchecked")
@HiltViewModel
class LoginViewModel @Inject constructor() :
    ViewModel() {

    //로그인 완료 후 화면을 이동시키기 위한 플래그
    val isLoging = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(accessToken: String) {

    }
}