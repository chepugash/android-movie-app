package com.practice.feature.auth_impl.presentation.confirm.mvi

sealed interface ConfirmEffect {

    object NavigateBack : ConfirmEffect

    object NavigateToHome : ConfirmEffect
}
