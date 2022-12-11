package com.rahul.beerapp.ui.landing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahul.beerapp.repository.MainRepository
import com.rahul.beerapp.ui.landing.model.BeersResponseDB
import com.rahul.beerapp.ui.landing.model.BeersResponseItem
import kotlinx.coroutines.launch

class LandingVM(private val repository: MainRepository, private val id: Int = 1) : ViewModel() {

    init {
        repository.getBeersResponse()
        repository.getSingleBeer(id)
    }

    val beersDb: LiveData<List<BeersResponseDB>>
        get() = repository.beersDB

    val singleBeer: LiveData<List<BeersResponseItem>>
        get() = repository.singleBeer
}