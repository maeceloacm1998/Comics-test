package com.example.comics.data

import com.example.comics.data.models.ComicsModel

interface MainRepository {
    suspend fun getComics(): Result<ComicsModel>
}