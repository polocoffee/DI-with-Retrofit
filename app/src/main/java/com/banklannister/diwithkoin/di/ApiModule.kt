package com.banklannister.diwithkoin.di

import com.banklannister.diwithkoin.BuildConfig
import com.banklannister.diwithkoin.api.ApiService
import com.banklannister.diwithkoin.utils.BASE_URL
import com.banklannister.diwithkoin.utils.NETWORK_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = BASE_URL
const val networkTimeout = NETWORK_TIMEOUT

fun provideGson(): Gson = GsonBuilder().setLenient().create()

fun provideOkhttpClient() = if (BuildConfig.DEBUG) {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    val requestInterceptor = Interceptor { chain ->
        val urls = chain.request()
            .url
            .newBuilder()
            .build()

        val request = chain.request()
            .newBuilder()
            .url(urls)
            .build()

        return@Interceptor chain.proceed(request)
    }

    OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(requestInterceptor)
        .build()

} else {
    OkHttpClient
        .Builder()
        .build()
}

fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): ApiService =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)

val apiModule = module {
    single { baseUrl }
    single { networkTimeout }
    single { provideGson() }
    single { provideOkhttpClient() }
    single { provideRetrofit(get(), get(), get()) }
}
