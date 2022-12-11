package com.rahul.beerapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahul.beerapp.ui.landing.model.BeersResponseDB

@Dao
interface BeerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToDB(beer: BeersResponseDB)

    @Query("SELECT * FROM BEERS")
    fun getBeers(): LiveData<List<BeersResponseDB>>

}