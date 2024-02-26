package com.practice.feature.detail.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.feature.detail.presentation.mapper.DetailEntityToDetailPresentationMapper
import com.practice.feature.detail_api.usecase.GetFilmByIdUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val mapper: DetailEntityToDetailPresentationMapper
) : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState())
    val state: StateFlow<DetailState>
        get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DetailEffect?>()
    val effect: SharedFlow<DetailEffect?>
        get() = _effect.asSharedFlow()

    fun event(detailEvent: DetailEvent) {
        when (detailEvent) {
            DetailEvent.OnBackClick -> onBackClick()
            is DetailEvent.OnOpenKinopoiskClick -> onOpenKinopoiskClick(detailEvent.id)
            is DetailEvent.OnFilmIdDelivered -> loadFilmInfo(detailEvent.id)
        }
    }

    private fun onOpenKinopoiskClick(id: Int) {

    }

    private fun onBackClick() {

    }

    private fun loadFilmInfo(id: Int) {
        viewModelScope.launch {
            runCatching {
                getFilmByIdUseCase(id)
            }.fold(
                onSuccess = {
                    val film = mapper(it)
                    val newState = _state.value.copy(
                        film = film
                    )
                    _state.emit(newState)
                },
                onFailure = { }
            )
        }
    }
}
