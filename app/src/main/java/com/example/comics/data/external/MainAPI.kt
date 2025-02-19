package com.example.comics.data.external

import com.example.comics.data.models.ComicsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI {
    @GET("comics")
    suspend fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ) : ComicsModel
}