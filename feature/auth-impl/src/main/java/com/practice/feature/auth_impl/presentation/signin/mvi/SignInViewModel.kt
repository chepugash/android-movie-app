package com.practice.feature.auth_impl.presentation.signin.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.feature.auth_api.usecase.CheckIsAuthorizedUseCase
import com.practice.feature.auth_api.usecase.SendCodeToUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val sendCodeToUserUseCase: SendCodeToUserUseCase,
    private val checkIsAuthorizedUseCase: CheckIsAuthorizedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SignInState>(SignInState())
    val state: StateFlow<SignInState>
        get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SignInEffect?>()
    val effect: SharedFlow<SignInEffect?>
        get() = _effect.asSharedFlow()

    fun event(signInEvent: SignInEvent) {
        when (signInEvent) {
            SignInEvent.OnConfirmClick -> onConfirmClick()
            is SignInEvent.OnPhoneChanged -> {
                onPhoneChanged(signInEvent.phone)
            }
        }
    }

    init {
        checkIsAuthenticated()
    }

    private fun onPhoneChanged(phone: String) {
        viewModelScope.launch {
            val newState = _state.value.copy(phone = phone)
            _state.emit(newState)
        }
    }

    private fun onConfirmClick() {
        viewModelScope.launch {
            runCatching {
                sendCodeToUserUseCase(_state.value.phone)
            }.fold(
                onSuccess = {
                    _effect.emit(SignInEffect.NavigateToConfirm(_state.value.phone))
                },
                onFailure = {
                    val newState = _state.value.copy(error = it.message)
                    _state.emit(newState)
                }
            )
        }
    }

    private fun checkIsAuthenticated() {
        viewModelScope.launch {
            try {
                if (checkIsAuthorizedUseCase()) {
                    _effect.emit(SignInEffect.NavigateToHome)
                }
            } catch(e: Throwable) {
                _state.emit(_state.value.copy(error = e.message))
            }
        }
    }
}

