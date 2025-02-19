package com.example.comics.data

import com.example.comics.BuildConfig
import com.example.comics.data.external.MainAPI
import com.example.comics.data.models.ComicsModel
import com.example.comics.util.safeRunDispatcher

class MainRepositoryImpl(
    private val mainAPI: MainAPI
): MainRepository {
    override suspend fun getComics(): Result<ComicsModel> {
        return safeRunDispatcher {
            mainAPI.getComics(
                ts = BuildConfig.MARVEL_TS,
                apikey = BuildConfig.MARVEL_API_KEY,
                hash = BuildConfig.MARVEL_HASH
            )
        }
    }
}