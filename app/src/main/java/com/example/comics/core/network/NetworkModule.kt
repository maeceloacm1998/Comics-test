package com.example.comics.core.network

import com.example.comics.BuildConfig
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object NetworkModule {
    val modules = module {
        single { provideRetrofit() }
    }

    private fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, SECONDS)
            .readTimeout(30, SECONDS)
            .writeTimeout(30, SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}