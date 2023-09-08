package com.banklannister.diwithkoin.repository


import com.banklannister.diwithkoin.api.ApiService
import com.banklannister.diwithkoin.utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ApiRepository(private val apiService: ApiService) {

    suspend fun getPhoto(srtSearchPhoto: String) = flow {
        emit(DataStatus.loading())
        val result = apiService.getPhoto(srtSearchPhoto)
        when (result.code()) {
            200 -> {
                emit(DataStatus.success(result.body()?.hits))
            }

            400 -> {
                emit(DataStatus.error(result.message()))
            }

            500 -> emit(DataStatus.error(result.message()))
        }
    }.catch { emit(DataStatus.error(it.message.toString())) }
        .flowOn(Dispatchers.IO)
}