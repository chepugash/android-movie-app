package com.practice.feature.auth_impl.presentation.signin.mvi

import androidx.compose.runtime.Immutable

@Immutable
data class SignInState(
    val phone: String = "",
    val error: String? = null,
    val isLoading: Boolean = false
)
