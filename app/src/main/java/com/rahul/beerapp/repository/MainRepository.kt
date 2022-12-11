package com.rahul.beerapp.repository

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.rahul.beerapp.data.api.ApiService
import com.rahul.beerapp.data.local.BeerDao
import com.rahul.beerapp.data.local.BeerRoomDB
import com.rahul.beerapp.ui.landing.model.BeersResponse
import com.rahul.beerapp.ui.landing.model.BeersResponseDB
import com.rahul.beerapp.ui.landing.model.BeersResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRepository(
    private val lifeCycleOwner: LifecycleOwner,
    private val apiService: ApiService,
    private val roomDB: BeerRoomDB,
    private val internet: Boolean = false
) {
    private val beersListDB: MutableLiveData<List<BeersResponseDB>> = MutableLiveData()
    val beersDB: LiveData<List<BeersResponseDB>>
        get() = beersListDB

    private val beer: MutableLiveData<List<BeersResponseItem>> = MutableLiveData()
    val singleBeer: LiveData<List<BeersResponseItem>>
        get() = beer

    private val dao: BeerDao
        get() = roomDB.getDao()

    fun getBeersResponse() {
        CoroutineScope(Dispatchers.IO).launch {
            if (internet) {
                val response: BeersResponse = apiService.getBeers()
                val list = saveToDb(response)
                beersListDB.postValue(list)
            }
        }
        getLiveData()
    }

    fun getSingleBeer(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response: BeersResponse = apiService.getSingleBeer(id)
            beer.postValue(response)
        }
    }

    private fun getLiveData() {
        dao.getBeers().observe(lifeCycleOwner, Observer {
            beersListDB.postValue(it)
        })
    }

    private fun saveToDb(response: BeersResponse): List<BeersResponseDB> {
        val list = arrayListOf<BeersResponseDB>()
        response.forEach {
            val data =
                BeersResponseDB(it.id, it.name, it.image_url, it.tagline, it.first_brewed, it.abv)
            dao.addToDB(data)
            list.add(data)
        }
        return list
    }
}