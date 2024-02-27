package com.practice.feature.auth_impl.presentation.confirm.mvi

import androidx.compose.runtime.Immutable

@Immutable
data class ConfirmState(
    val phone: String = "",
    val code: String = "",
    val time: Int = -1,
    val error: String? = null,
    val isLoading: Boolean = false
)
