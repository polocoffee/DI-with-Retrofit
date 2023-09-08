package com.banklannister.diwithkoin.api


import com.banklannister.diwithkoin.BuildConfig
import com.banklannister.diwithkoin.response.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getPhoto(
        @Query("q") searchQuery: String,
        @Query("image_type") imgType: String = "photo",
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<PhotoResponse>
}