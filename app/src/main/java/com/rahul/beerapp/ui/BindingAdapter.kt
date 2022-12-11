package com.rahul.beerapp.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rahul.beerapp.R
import com.rahul.beerapp.ui.landing.model.BeersResponseItem


@BindingAdapter("app:imageUrl")
fun loadImage(imageView: ImageView, image : String?){
    image.let {
        Glide.with(imageView.context).load(it).placeholder(R.drawable.beer_place_holder).into(imageView)
    }
}

@BindingAdapter("app:abv")
fun setAbv(textView: TextView, abv: Double){
    textView.text =  String.format(textView.context.resources.getString(R.string.abv_txt), abv.toString())
}

@BindingAdapter( "app:details")
fun setValues(textView: TextView, beer: BeersResponseItem){
    val maltIngredients =  beer.ingredients.malt.joinToString {
        it.name.toString()
    }

    val hops =  beer.ingredients?.hops?.joinToString {
        it.name.toString()
    }

    val foodPairings = beer.food_pairing?.joinToString {
        it
    }
    textView.text = textView.context.getString(R.string.detail_info_txt, maltIngredients, hops, foodPairings)
}
