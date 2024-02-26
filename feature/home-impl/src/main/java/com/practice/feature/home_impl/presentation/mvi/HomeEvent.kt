package com.practice.feature.home_impl.presentation.mvi

sealed interface HomeEvent {

    class OnFilmClick(val id: Int) : HomeEvent

    object OnProfileClick : HomeEvent
}
