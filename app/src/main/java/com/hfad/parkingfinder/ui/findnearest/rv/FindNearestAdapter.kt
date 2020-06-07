package com.hfad.parkingfinder.ui.findnearest.rv

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import com.hfad.parkingfinder.ui.parkingdetails.ParkingDetailsActivity
import com.hfad.parkingfinder.ui.parkingdetails.ParkingDetailsActivity.Companion.PARKING_BY_LOCATION
import com.hfad.parkingfinder.utils.resource.toDistance
import kotlinx.android.synthetic.main.card_view_parking_data.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.textColor
import java.util.*


class FindNearestAdapter(val parkingData: ArrayList<ParkingByLocationDto> = arrayListOf()) : RecyclerView.Adapter<FindNearestAdapter.ParkingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_parking_data, parent, false)
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
            view.parkingNameTV.text = parking.parkingName
            view.parkingAddressTV.text = parking.parkingAddress
            view.distanceTV.text = parking.distance.toDistance()
            val textColor = when (parking.freeSpaces) {
                -1 -> ContextCompat.getColor(view.context, R.color.gray)
                0 -> ContextCompat.getColor(view.context, R.color.red)
                in 1..5 -> ContextCompat.getColor(view.context, R.color.orange)
                in 6..15 -> ContextCompat.getColor(view.context, R.color.yellow)
                else -> ContextCompat.getColor(view.context, R.color.green)
            }
            view.freeSpacesTV.textColor = textColor
            view.freeSpacesTextTV.textColor = textColor
            view.freeSpacesTV.text = when {
                parking.freeSpaces == 0 -> "About 0 "
                parking.freeSpaces == -1 -> "No data"
                else -> "Above " + parking.freeSpaces + " "
            }

            if (parking.dataVeracity == -1) {
                view.veracityPB.visibility = View.INVISIBLE
                view.veracityTV.visibility = View.VISIBLE
                view.veracityTV.text = "0%"
                view.freeSpacesTextTV.visibility = View.INVISIBLE

            } else {
                view.freeSpacesTextTV.visibility = View.VISIBLE
                view.veracityPB.visibility = View.VISIBLE
                view.veracityTV.visibility = View.VISIBLE
                view.veracityTV.text = "${parking.dataVeracity}%"
                view.veracityPB.progress = parking.dataVeracity
            }

            view.setOnClickListener {
                view.context.startActivity(view.context.intentFor<ParkingDetailsActivity>().putExtra(PARKING_BY_LOCATION, parking))
            }
        }
    }
}