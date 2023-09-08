package com.banklannister.diwithkoin.di


import com.banklannister.diwithkoin.repository.ApiRepository
import com.banklannister.diwithkoin.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val photoModule = module {
    factory { ApiRepository(get()) }
    viewModel() { MainViewModel(get()) }
}