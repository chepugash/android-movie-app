package com.practice.feature.home_impl.presentation.mvi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.feature.home_api.usecase.GetFilmsUseCase
import com.practice.feature.home_api.usecase.GetUserUseCase
import com.practice.feature.home_api.usecase.SignOutUseCase
import com.practice.feature.home_impl.presentation.mapper.HomeEntityToHomePresentationMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val mapper: HomeEntityToHomePresentationMapper,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect?>()
    val effect: SharedFlow<HomeEffect?>
        get() = _effect.asSharedFlow()

    fun event(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.OnFilmClick -> onFilmClick(homeEvent.id)
            HomeEvent.OnProfileClick -> onProfileClick()
            HomeEvent.OnBackCLick -> onBackClick()
            is HomeEvent.OnSignOut -> onSignOut(homeEvent.phone)
        }
    }

    private fun onSignOut(phone: String) {
        viewModelScope.launch {
            try {
                signOutUseCase(phone)
                _effect.emit(HomeEffect.NavigateBack)
            } catch (e: Throwable) {
                _state.emit(_state.value.copy(error = e.message))
            }
        }
    }

    init {
        getUser()
        loadFilms()
    }

    private fun getUser() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(userPhone = getUserUseCase()))
        }
    }

    private fun onBackClick() {
        viewModelScope.launch {
            _effect.emit(
                HomeEffect.NavigateBack
            )
        }
    }

    private fun onProfileClick() {

    }

    private fun onFilmClick(id: Int) {
        viewModelScope.launch {
            _effect.emit(
                HomeEffect.NavigateToDetail(id)
            )
        }
    }

    private fun loadFilms() {
        viewModelScope.launch {
            runCatching {
                getFilmsUseCase()
            }.fold(
                onSuccess = {
                    val films = mapper(it)
                    val newState = _state.value.copy(
                        films = films
                    )
                    _state.emit(newState)
                },
                onFailure = {
                    Log.e("EEEEEE", "$it")
                }
            )
        }
    }
}
