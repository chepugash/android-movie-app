package com.practice.feature.detail.presentation.mvi

sealed interface DetailEffect {

    object NavigateBack: DetailEffect
}
