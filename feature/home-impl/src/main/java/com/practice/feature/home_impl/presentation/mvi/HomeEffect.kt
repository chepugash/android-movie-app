package com.practice.feature.home_impl.presentation.mvi

sealed interface HomeEffect {

    class NavigateToDetail(val id: Int) : HomeEffect

    object NavigateToProfile : HomeEffect

    object NavigateBack : HomeEffect
}
