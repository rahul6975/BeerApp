package com.rahul.beerapp.ui.landing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahul.beerapp.repository.MainRepository

class LandingVMFactory(private val repository: MainRepository, private val id: Int = 1): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LandingVM(repository, id) as T
    }
}