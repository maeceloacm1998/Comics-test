package com.example.comics.domain

import com.example.comics.data.MainRepository

class GetComicsUseCase(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke() = mainRepository.getComics()
}