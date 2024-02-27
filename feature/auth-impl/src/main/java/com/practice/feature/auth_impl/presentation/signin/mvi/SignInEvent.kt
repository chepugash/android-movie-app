package com.practice.feature.auth_impl.presentation.signin.mvi

sealed interface SignInEvent {

    object OnConfirmClick : SignInEvent

    class OnPhoneChanged(val phone: String) : SignInEvent
}
