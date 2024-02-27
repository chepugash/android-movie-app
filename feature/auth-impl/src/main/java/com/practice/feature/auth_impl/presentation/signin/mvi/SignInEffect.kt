package com.practice.feature.auth_impl.presentation.signin.mvi

sealed interface SignInEffect {

    class NavigateToConfirm(val phone: String): SignInEffect

    object NavigateToHome: SignInEffect
}
