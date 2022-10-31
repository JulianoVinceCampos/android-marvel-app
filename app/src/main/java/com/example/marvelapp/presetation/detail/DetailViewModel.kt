package com.example.marvelapp.presetation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.Comic
import com.example.core.usecase.GetComicsUseCase
import com.example.core.usecase.base.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getComicsUseCase: GetComicsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getComics(characterId: Int) = viewModelScope.launch {
        //Obs: dentrod a classe GetComicsUseCase estamos usando a palavra reservada operator
        //Por esse motivo podemos esconder a chamada desse metodo, sendo que ele já é executado de forma implicida
        getComicsUseCase.invoke(GetComicsUseCase.GetCommicsParams(characterId)).watStatus()
    }

    private fun Flow<ResultStatus<List<Comic>>>.watStatus() = viewModelScope.launch {
        collect { status ->
            _uiState.value = when (status) {
                ResultStatus.Loading -> UiState.Loading
                is ResultStatus.Sucess -> UiState.Sucess(status.data)
                is ResultStatus.Error -> UiState.Error
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Sucess(val comics: List<Comic>) : UiState()
        object Error : UiState()
    }
}