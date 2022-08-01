package com.sarang.toringlogin.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.torang_core.data.model.LoggedInUserData
import com.example.torang_core.data.model.User
import com.example.torang_core.repository.LoginRepository
import com.example.torang_core.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@SuppressWarnings("unchecked")
@HiltViewModel
class LoginViewModel @Inject constructor(private var loginRepository: LoginRepository) :
    ViewModel() {

    //로그인 완료 후 화면을 이동시키기 위한 플래그
    val isLoging = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun login(accessToken: String) {
        viewModelScope.launch {
            try {
                /*loginRepository.facebookLogin(accessToken)?.let {
                    isLoging.postValue(true)
                }*/
            } catch (e: Exception) {
                _errorMessage.postValue(e.toString())
            }
        }

    }
}