package com.practice.feature.auth_impl.presentation.confirm.mvi

sealed interface ConfirmEvent {

    object OnConfirmClick : ConfirmEvent

    object OnBackClick : ConfirmEvent

    object OnSendCodeAgainClick : ConfirmEvent

    object OnTimerStart : ConfirmEvent

    object OnTimerStop : ConfirmEvent

    class OnCodeChanged(val code: String) : ConfirmEvent

    class OnPhoneDelivered(val phone: String) : ConfirmEvent
}
