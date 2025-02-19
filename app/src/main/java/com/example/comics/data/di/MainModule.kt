package com.example.comics.data.di

import com.example.comics.data.MainRepository
import com.example.comics.data.MainRepositoryImpl
import com.example.comics.data.external.MainAPI
import com.example.comics.domain.GetComicsUseCase
import com.example.comics.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

object MainModule {

    val modules = module {
        single<MainAPI> { get<Retrofit>().create(MainAPI::class.java) }

        factory<MainRepository> {
            MainRepositoryImpl(
                mainAPI = get()
            )
        }

        factory { GetComicsUseCase(mainRepository = get()) }

        viewModel {
            MainViewModel(
                getComicsUseCase = get()
            )
        }
    }
}