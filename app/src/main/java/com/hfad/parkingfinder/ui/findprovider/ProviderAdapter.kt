package com.hfad.parkingfinder.ui.findprovider

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.provider.dto.ProviderDto
import com.hfad.parkingfinder.ui.findneartoprovider.FindNearToProviderActivity
import com.hfad.parkingfinder.ui.findneartoprovider.FindNearToProviderActivity.Companion.PROVIDER
import com.hfad.parkingfinder.ui.findneartoprovider.dto.ProviderLocationDto
import com.hfad.parkingfinder.utils.resource.toDistance
import kotlinx.android.synthetic.main.card_view_provider.view.*
import org.jetbrains.anko.intentFor


class ProviderAdapter(val providers: ArrayList<ProviderDto> = arrayListOf()) : RecyclerView.Adapter<ProviderAdapter.ParkingVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_provider, parent, false)
        return ParkingVH(view)
    }

    override fun onBindViewHolder(holder: ParkingVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return providers.size
    }

    inner class ParkingVH(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            val provider = providers[position]
            view.providerTV.text = provider.providerName.capitalize()
            view.providerDetailsTV.text = provider.providerDetails
            view.distanceTV.text = provider.distance.toDistance()
            view.setOnClickListener {
                view.context.startActivity(view.context.intentFor<FindNearToProviderActivity>().putExtra(PROVIDER, ProviderLocationDto(provider.providerName, provider.attitude, provider.longitude)))
            }
            view.providerTypeTV.text = provider.exactProviderType?.capitalize()?.replace("_", " ")
            view.providerUrlTV.text = if (provider.url?.last() == '/') provider.url.substring(0, provider.url.lastIndex) else provider.url
        }
    }
}