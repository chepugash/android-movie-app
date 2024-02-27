package com.practice.feature.auth_impl.presentation.confirm.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practice.feature.auth_api.usecase.CheckIsAuthorizedUseCase
import com.practice.feature.auth_api.usecase.CheckIsCodeCorrectUseCase
import com.practice.feature.auth_api.usecase.SaveUserUseCase
import com.practice.feature.auth_api.usecase.SendCodeToUserUseCase
import com.practice.feature.auth_impl.presentation.confirm.util.PhoneMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ConfirmViewModel(
    private val checkIsCodeCorrectUseCase: CheckIsCodeCorrectUseCase,
    private val sendCodeToUserUseCase: SendCodeToUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val phoneMapper: PhoneMapper
) : ViewModel() {

    private var timerJob: Job? = null

    private val _state = MutableStateFlow<ConfirmState>(ConfirmState())
    val state: StateFlow<ConfirmState>
        get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ConfirmEffect?>()
    val effect: SharedFlow<ConfirmEffect?>
        get() = _effect.asSharedFlow()

    init {
        onTimerStart()
    }

    fun event(confirmEvent: ConfirmEvent) {
        when (confirmEvent) {
            ConfirmEvent.OnConfirmClick -> onConfirmClick()
            ConfirmEvent.OnBackClick -> onBackClick()
            ConfirmEvent.OnSendCodeAgainClick -> onSendCodeAgainClick()
            ConfirmEvent.OnTimerStart -> onTimerStart()
            ConfirmEvent.OnTimerStop -> onTimerStop()

            is ConfirmEvent.OnCodeChanged -> {
                onCodeChanged(confirmEvent.code)
            }

            is ConfirmEvent.OnPhoneDelivered -> {
                onPhoneDelivered(confirmEvent.phone)
            }
        }
    }

    private fun onPhoneDelivered(phone: String) {
        viewModelScope.launch {
            val newState = _state.value.copy(phone = phoneMapper.originalToMapped(phone))
            _state.emit(newState)
        }
    }

    private fun onSendCodeAgainClick() {
        viewModelScope.launch {
            runCatching {
                val phone = phoneMapper.mappedToOriginal(_state.value.phone)
                sendCodeToUserUseCase(phone)
                onTimerStart()
            }.onFailure {
                _state.emit(_state.value.copy(error = it.message))
            }
        }
    }

    private fun onBackClick() {
        viewModelScope.launch {
            _effect.emit(ConfirmEffect.NavigateBack)
        }
    }

    private fun onCodeChanged(code: String) {
        viewModelScope.launch {
            val newState = _state.value.copy(code = code)
            _state.emit(newState)
        }
    }

    private fun onConfirmClick() {
        viewModelScope.launch {
            try {
                val phone = phoneMapper.mappedToOriginal(_state.value.phone)
                val code = _state.value.code

                checkIsCodeCorrectUseCase(phone, code)

                if (checkIsCodeCorrectUseCase(phone, code)) {
                    saveUserUseCase(_state.value.phone)
                    _effect.emit(ConfirmEffect.NavigateToHome)
                    val newState = _state.value.copy(error = null)
                    _state.emit(newState)
                } else {
                    val newState = _state.value.copy(error = ERROR_CODE_MISMATCH)
                    _state.emit(newState)
                }

            } catch (e: Throwable) {
                val newState = _state.value.copy(error = e.message)
                _state.emit(newState)
            }
        }
    }


    private fun createTimerFlow(): Flow<Int> = flow {
        var time = COUNTDOWN_TIMER
        while (time >= 0) {
            emit(time)
            delay(COUNTDOWN_DELAY)
            time--
        }
    }

    private fun onTimerStart() {
        timerJob = viewModelScope.launch {
            createTimerFlow().collect { value ->
                val newState = _state.value.copy(time = value)
                _state.emit(newState)
            }
        }
    }

    private fun onTimerStop() {
        timerJob?.cancel()
        timerJob = null
    }

    companion object {
        private const val COUNTDOWN_TIMER = 30
        private const val COUNTDOWN_DELAY: Long = 1000
        private const val ERROR_CODE_MISMATCH = "code mismatch"
    }
}
