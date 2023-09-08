package com.banklannister.diwithkoin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banklannister.diwithkoin.repository.ApiRepository
import com.banklannister.diwithkoin.response.PhotoResponse
import com.banklannister.diwithkoin.utils.DataStatus
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _photoList = MutableLiveData<DataStatus<List<PhotoResponse.Hit>>>()
    val photoList: LiveData<DataStatus<List<PhotoResponse.Hit>>>
        get() = _photoList

    fun getPhoto(srtSearchPhoto: String) = viewModelScope.launch {
        repository.getPhoto(srtSearchPhoto).collect {
            _photoList.value = it
        }
    }

}