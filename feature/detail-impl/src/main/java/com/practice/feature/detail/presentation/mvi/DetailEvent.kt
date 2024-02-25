package com.practice.feature.detail.presentation.mvi

sealed interface DetailEvent {

    class OnOpenKinopoiskClick(val id: Int) : DetailEvent

    object OnBackClick : DetailEvent

    class OnFilmIdDelivered(val id: Int) : DetailEvent
}
