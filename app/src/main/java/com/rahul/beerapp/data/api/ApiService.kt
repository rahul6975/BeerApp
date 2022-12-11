package com.rahul.beerapp.data.api

import com.rahul.beerapp.ui.landing.model.BeersResponse
import com.rahul.beerapp.ui.landing.model.BeersResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("beers")
    suspend fun getBeers(): BeersResponse

    @GET("beers/{id}")
    suspend fun getSingleBeer(@Path("id") id: Int) : BeersResponse
}