package com.example.comics.domain

import com.example.comics.data.MainRepository
import com.example.comics.data.models.ComicsModel
import com.example.comics.util.safeRunDispatcher

class GetComicsUseCase(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(): Result<ComicsModel> =
        safeRunDispatcher { mainRepository.getComics() }
}