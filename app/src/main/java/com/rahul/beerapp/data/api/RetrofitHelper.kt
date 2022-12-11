package com.rahul.beerapp.data.api

import com.rahul.beerapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getInstance(): Retrofit {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        val okHttpClient: OkHttpClient = builder.build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}