package com.rahul.beerapp.utils

import android.content.Context
import android.widget.Toast

object Extensions {
    fun Context.showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}