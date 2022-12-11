package com.rahul.beerapp.ui.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahul.beerapp.databinding.BeerItemLayoutBinding
import com.rahul.beerapp.ui.landing.model.BeersResponseDB
import com.rahul.beerapp.ui.landing.model.BeersResponseItem

class BeerAdapter(
    private val listDB: List<BeersResponseDB>,
    private val clickListener: ClickListener,
    private val internet: Boolean
) :
    RecyclerView.Adapter<BeerAdapter.VH>() {

    inner class VH(private val view: BeerItemLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(beer: BeersResponseDB) {
            view.apply {
                beerName.text = beer.name
                tagline.text = beer.tagline
                abv.text = beer.abv.toString()
                if(internet) Glide.with(beerImage).load(beer.image_url).into(beerImage)
                root.setOnClickListener {
                    clickListener.onBeerClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(BeerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), null, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(listDB[position])
    }

    override fun getItemCount(): Int = listDB.size

}

interface ClickListener {
    fun onBeerClick(position: Int)
}