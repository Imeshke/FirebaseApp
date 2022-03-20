package com.imeshke.firebaselogin.ui.main

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imeshke.firebaselogin.api.ApiClient
import com.imeshke.firebaselogin.api.ApiResult
import com.imeshke.firebaselogin.api.model.Hotel
import com.imeshke.firebaselogin.repositories.UserRepository
import com.imeshke.firebaselogin.utils.startLoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HotelsViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val hotels: MutableLiveData<List<Hotel>> = MutableLiveData()

    val user by lazy {
        repository.currentUser()
    }
    
    fun logout(view: View){
        repository.logout()
        view.context.startLoginActivity()
    }
    fun getHotels(): MutableLiveData<List<Hotel>> {
        refreshHotels()
        return  hotels
    }
    private fun refreshHotels() {
        viewModelScope.launch(Dispatchers.Default) {
            val apiResult = ApiClient.refreshHotelList()

            when (apiResult) {
                is ApiResult.Success -> hotels.postValue(apiResult.data.data)
                is ApiResult.UnknownError -> Log.d("HotelsViewModel","ApiError: ${apiResult.errorMessage}")
            }
        }
    }

}