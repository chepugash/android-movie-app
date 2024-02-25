package com.practice.feature.home_impl.presentation.mvi

import com.practice.feature.home_impl.presentation.model.HomePresentationEntity
import kotlinx.collections.immutable.ImmutableList

data class HomeState(
    val films: ImmutableList<HomePresentationEntity>? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
