@file:OptIn(KoinReflectAPI::class)

package moziotest2.adapter.di

import moziotest2.network.API
import moziotest2.network.RetrofitFactory
import moziotest2.repository.ShopRepository
import moziotest2.repository.ShopRepositoryImpl
import moziotest2.service.FlavorService
import moziotest2.ui.shop.ShopViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.annotation.KoinReflectAPI
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModule {

    private const val BASE_URL = "https://static.mozio.com/"

    fun main(): Module = module {
        single { createHttpClient() }
        single<ShopRepository> { ShopRepositoryImpl(get()) }
        single<FlavorService> { FlavorService(get()) }
        viewModel { ShopViewModel(get(), get()) }
    }

    private fun createHttpClient(): API {
        return RetrofitFactory(BASE_URL).createRetrofit(API::class.java)
    }
}