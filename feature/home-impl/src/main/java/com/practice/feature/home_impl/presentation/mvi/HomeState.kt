package com.practice.feature.home_impl.presentation.mvi

import androidx.compose.runtime.Immutable
import com.practice.feature.home_impl.presentation.model.HomePresentationEntity
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class HomeState(
    val userPhone: String? = null,
    val films: ImmutableList<HomePresentationEntity>? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)
