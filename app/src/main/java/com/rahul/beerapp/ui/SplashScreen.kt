package com.rahul.beerapp.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.rahul.beerapp.databinding.ActivitySplashScreenBinding
import com.rahul.beerapp.ui.landing.LandingPage
import com.rahul.beerapp.utils.Constants
import com.rahul.beerapp.utils.Extensions.showToast
import com.rahul.beerapp.utils.NetworkConnection

@RequiresApi(Build.VERSION_CODES.M)
class SplashScreen() : AppCompatActivity() {
    private val binding by lazy { ActivitySplashScreenBinding.inflate(layoutInflater) }
    private val handler by lazy { Looper.myLooper()?.let { Handler(it) } }
    private val internet: Boolean
        get() = NetworkConnection.hasInternetConnection(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handler?.postDelayed({ openMainActivity() }, 1000)
    }

    private fun openMainActivity() {
        showToast("opening Landing Page")
        val intent = Intent(this@SplashScreen, LandingPage::class.java)
        intent.putExtra(Constants.INTERNET_CONNECTION, internet)
        startActivity(intent)
        this.finish()
    }
}