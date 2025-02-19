package com.example.comics.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.data.models.ResultModel
import com.example.comics.domain.GetComicsUseCase
import com.example.comics.core.util.State
import kotlinx.coroutines.launch

class MainViewModel(
    private val getComicsUseCase: GetComicsUseCase
) : ViewModel() {
    private val _comics = MutableLiveData<State<List<ResultModel>>>()
    val comics: LiveData<State<List<ResultModel>>> get() = _comics

    fun fetchComics() {
        _comics.value = State.Loading()

        viewModelScope.launch {
            getComicsUseCase()
                .onSuccess {
                    _comics.value = State.Success(it.data.results)
                }.onFailure {
                    _comics.value = State.Error(it.message ?: ERROR_MESSAGE)
                }
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "Erro ao buscar os quadrinhos"
    }
}