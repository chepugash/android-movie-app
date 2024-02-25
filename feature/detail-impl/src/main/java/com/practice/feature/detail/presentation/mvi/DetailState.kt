package com.practice.feature.detail.presentation.mvi

import com.practice.feature.detail.presentation.model.DetailPresentationEntity

data class DetailState(
    val film: DetailPresentationEntity? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
