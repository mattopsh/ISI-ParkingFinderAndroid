package com.hfad.parkingfinder.ui.findneartoprovider

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingNearToProviderDto
import com.hfad.parkingfinder.ui.parkingdetails.ParkingDetailsActivity
import com.hfad.parkingfinder.ui.parkingdetails.ParkingDetailsActivity.Companion.PARKING_NEAR_TO_PROVIDER
import com.hfad.parkingfinder.utils.resource.toDistance
import kotlinx.android.synthetic.main.card_view_near_to_provider.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.textColor

class NearToProviderAdapter(val parkingData: ArrayList<ParkingNearToProviderDto> = arrayListOf()) : RecyclerView.Adapter<NearToProviderAdapter.ParkingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_near_to_provider, parent, false)
        return ParkingVH(view)
    }

    override fun onBindViewHolder(holder: ParkingVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return parkingData.size
    }

    inner class ParkingVH(val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val parking = parkingData[position]
            view.parkingName2TV.text = parking.parkingName
            view.parkingAddress2TV.text = parking.parkingAddress
            view.distanceToParkingTV.text = parking.distanceToParking.toDistance()
            view.distanceToProviderTV.text = parking.distanceToProvider.toDistance()
            val textColor = when (parking.freeSpaces) {
                0 -> ContextCompat.getColor(view.context, R.color.red)
                in 1..5 -> ContextCompat.getColor(view.context, R.color.orange)
                in 6..15 -> ContextCompat.getColor(view.context, R.color.yellow)
                else -> ContextCompat.getColor(view.context, R.color.green)
            }
            view.freeSpaces2TV.textColor = textColor
            view.freeSpacesText2TV.textColor = textColor
            view.freeSpaces2TV.text = when {
                parking.freeSpaces == 0 -> "About 0 "
                parking.freeSpaces == -1 -> "No data"
                else -> "Above " + parking.freeSpaces + " "
            }

            if (parking.dataVeracity == -1) {
                view.veracity2PB.visibility = View.INVISIBLE
                view.veracity2TV.visibility = View.VISIBLE
                view.veracity2TV.text = "0%"
                view.freeSpacesText2TV.visibility = View.INVISIBLE

            } else {
                view.freeSpacesText2TV.visibility = View.VISIBLE
                view.veracity2PB.visibility = View.VISIBLE
                view.veracity2TV.visibility = View.VISIBLE
                view.veracity2TV.text = "${parking.dataVeracity}%"
                view.veracity2PB.progress = parking.dataVeracity
            }
            view.setOnClickListener {
                view.context.startActivity(view.context.intentFor<ParkingDetailsActivity>().putExtra(PARKING_NEAR_TO_PROVIDER, parking))
            }
        }
    }
}