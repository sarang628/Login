package com.sarang.torang.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.uistate.LoginTitleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TorangLogoViewModel @Inject constructor() : ViewModel() {

    private val _loginTitleuiState = MutableStateFlow(LoginTitleUiState())
    val loginTitleUiState = _loginTitleuiState.asStateFlow()

    private val delay = 50L

    init {
        viewModelScope.launch {
            writeTitle("T O R A N G", delay)
            writeSubTitle("Hit the spot", delay)
        }
    }

    private suspend fun writeTitle(title: String, delay: Long) {
        var cursor = 0

        while (cursor < title.length) {
            _loginTitleuiState.update { it.copy(title = title.substring(0, cursor) + "_") }
            cursor++
            delay(delay)
        }
        _loginTitleuiState.update { it.copy(title = title) }
    }

    private suspend fun writeSubTitle(title: String, delay: Long) {
        var cursor = 0

        while (cursor < title.length) {
            _loginTitleuiState.update { it.copy(subtitle = title.substring(0, cursor) + "_") }
            cursor++
            delay(delay)
        }
        _loginTitleuiState.update { it.copy(subtitle = title) }
    }
}