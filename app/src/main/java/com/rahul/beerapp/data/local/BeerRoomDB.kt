package com.rahul.beerapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rahul.beerapp.ui.landing.model.BeersResponseDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [BeersResponseDB::class], version = 6)
abstract class BeerRoomDB : RoomDatabase() {
    abstract fun getDao(): BeerDao

    companion object {
        private var INSTANCE: BeerRoomDB? = null
        fun getDBInstance(context: Context): BeerRoomDB {
            if (INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext, BeerRoomDB::class.java, "BEERS"
                )
                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
            }
            return INSTANCE as BeerRoomDB
        }
    }

    fun clearTables() {
        GlobalScope.launch(Dispatchers.IO) {
            this@BeerRoomDB.clearAllTables()
        }
    }
}