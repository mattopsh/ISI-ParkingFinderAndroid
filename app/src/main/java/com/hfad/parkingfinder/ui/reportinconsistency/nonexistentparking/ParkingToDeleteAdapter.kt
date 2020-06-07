package com.hfad.parkingfinder.ui.reportinconsistency.nonexistentparking

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import kotlinx.android.synthetic.main.card_view_parking_to_delete.view.*

class ParkingToDeleteAdapter(val parkingData: ArrayList<ParkingByLocationDto> = arrayListOf()) : RecyclerView.Adapter<ParkingToDeleteAdapter.ParkingToDeleteVH>() {

    var onNonExistentParkingSelectListener: ((parkingNodeId: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingToDeleteVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_parking_to_delete, parent, false)
        return ParkingToDeleteVH(view)
    }

    override fun onBindViewHolder(holder: ParkingToDeleteVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return parkingData.size
    }

    inner class ParkingToDeleteVH(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            val parking = parkingData[position]
            view.parkingNameTV.text = parking.parkingName
            view.parkingAddressTV.text = parking.parkingAddress
            view.setOnClickListener {
                onNonExistentParkingSelectListener?.invoke(parking.parkingNodeId)
            }
        }
    }
}