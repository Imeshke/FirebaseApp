package com.imeshke.firebaselogin.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imeshke.firebaselogin.repositories.UserRepository
import com.imeshke.firebaselogin.ui.main.HotelsViewModel


@Suppress("UNCHECKED_CAST")
class HotelsViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HotelsViewModel(repository) as T
    }

}