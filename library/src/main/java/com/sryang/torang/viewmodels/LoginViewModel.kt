package com.sryang.torang.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.uistate.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiStete = _uiState.asStateFlow()

    val delay = 50L
    init {
        viewModelScope.launch {
            _uiState.update { it.copy(title = "_") }
            delay(delay)
            _uiState.update { it.copy(title = "T_") }
            delay(delay)
            _uiState.update { it.copy(title = "T _") }
            delay(delay)
            _uiState.update { it.copy(title = "T O_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O _") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R _") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A _") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N _") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "H_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hi_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hit_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hit t_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hit th_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hit the_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hit the s_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hit the sp_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hit the spo_") }
            delay(delay)
            _uiState.update { it.copy(title = "T O R A N G", subtitle = "Hit the spot") }
        }
    }
}