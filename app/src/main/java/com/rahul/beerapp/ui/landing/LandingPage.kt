package com.rahul.beerapp.ui.landing

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahul.beerapp.data.api.ApiService
import com.rahul.beerapp.data.api.RetrofitHelper
import com.rahul.beerapp.data.local.BeerRoomDB
import com.rahul.beerapp.databinding.ActivityMainBinding
import com.rahul.beerapp.repository.MainRepository
import com.rahul.beerapp.ui.detail.BeerDetails
import com.rahul.beerapp.ui.landing.model.BeersResponseDB
import com.rahul.beerapp.ui.landing.viewmodel.LandingVM
import com.rahul.beerapp.ui.landing.viewmodel.LandingVMFactory
import com.rahul.beerapp.utils.ConnectionLiveData
import com.rahul.beerapp.utils.Constants.MOBILE
import com.rahul.beerapp.utils.Constants.WIFI
import com.rahul.beerapp.utils.Extensions.showToast


open class LandingPage : AppCompatActivity(), ClickListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var i = 0
    private lateinit var viewModel: LandingVM
    private lateinit var beerAdapter: BeerAdapter
    private lateinit var beerListDB: ArrayList<BeersResponseDB>

    private lateinit var repository: MainRepository
    private lateinit var dbRoom: BeerRoomDB
    private val connectionLiveData by lazy { ConnectionLiveData(this) }

    private var isConnected = false
    private val internet: Boolean
        get() = isConnected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        connectionLiveData.observe(this) { connection ->
            if (connection.isConnected) {
                when (connection.type) {
                    WIFI, MOBILE -> isConnected = true
                }
            } else {
                isConnected = false
            }
        }
        setRecyclerView()
        initViewModel()
        handler?.postDelayed({ getData() }, 500)
    }


    private val handler by lazy { Looper.myLooper()?.let { Handler(it) } }

    private fun setRecyclerView() {
        beerListDB = ArrayList()
        beerAdapter = BeerAdapter(beerListDB, this, true)
        binding.rcvBeers.adapter = beerAdapter
        binding.rcvBeers.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModel() {
        dbRoom = BeerRoomDB.getDBInstance(this)
        if (internet) {
            dbRoom.clearTables()
        }
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        repository = MainRepository(this, apiService, dbRoom, internet)
        viewModel = ViewModelProvider(this, LandingVMFactory(repository))[LandingVM::class.java]
    }

    private fun getData() {
        viewModel.beersDb.observe(this, Observer {
            if (it != null) {
                Log.d("List", it.toString())
                beerListDB.addAll(it)
                beerAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onBeerClick(position: Int) {
        if (internet) {
            val intent = Intent(this, BeerDetails::class.java)
            intent.putExtra("id", beerListDB[position].id)
            startActivity(intent)
        } else {
            showToast("Internet not connected!")
        }
    }
}