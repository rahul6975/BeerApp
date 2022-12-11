package com.rahul.beerapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rahul.beerapp.R
import com.rahul.beerapp.data.api.ApiService
import com.rahul.beerapp.data.api.RetrofitHelper
import com.rahul.beerapp.data.local.BeerRoomDB
import com.rahul.beerapp.databinding.ActivityBeerDetailsBinding
import com.rahul.beerapp.repository.MainRepository
import com.rahul.beerapp.ui.landing.viewmodel.LandingVM
import com.rahul.beerapp.ui.landing.viewmodel.LandingVMFactory
import com.rahul.beerapp.utils.ConnectionLiveData
import com.rahul.beerapp.utils.Constants

class BeerDetails : AppCompatActivity() {
    private val binding by lazy { ActivityBeerDetailsBinding.inflate(layoutInflater) }
    private val id: Int?
        get() = intent?.getIntExtra("id", 1)

    private lateinit var viewModel: LandingVM
    private lateinit var repository: MainRepository
    private lateinit var dbRoom: BeerRoomDB

    private val connectionLiveData by lazy { ConnectionLiveData(this) }
    private var isConnected = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        connectionLiveData.observe(this) { connection ->
            if (connection.isConnected) {
                when (connection.type) {
                    Constants.WIFI, Constants.MOBILE -> isConnected = true
                }
            } else {
                isConnected = false
            }
        }
        initViewModel()
        setBeerDetailsBinding()
    }

    private fun initViewModel() {
        dbRoom = BeerRoomDB.getDBInstance(this)
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        repository = MainRepository(this,apiService, dbRoom)
        viewModel = ViewModelProvider(this, LandingVMFactory(repository, id ?: 1))[LandingVM::class.java]
    }

    private fun setBeerDetailsBinding() {
        viewModel.singleBeer.observe(this, Observer {
            it[0].let { beer ->
                if(isConnected) Glide.with(binding.detailsBearImage).load(beer.image_url).into(binding.detailsBearImage)
                binding.beer = beer
                val maltIngredients = beer.ingredients.malt.joinToString { malt -> malt.name.toString() }
                val hops = beer.ingredients?.hops?.joinToString { hop -> hop.name.toString() }
                val foodPairings = beer.food_pairing?.joinToString { foodPairing -> foodPairing }
                binding.infoTxt.text = resources.getString(
                    R.string.detail_info_txt,
                    maltIngredients,
                    hops,
                    foodPairings
                )
            }
        })
    }
}