package com.hfad.parkingfinder.ui.parkingdetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.ui.parkingdetails.model.Day
import com.hfad.parkingfinder.ui.parkingdetails.model.DaySelectDto
import kotlinx.android.synthetic.main.card_view_day.view.*


class CalendarDayAdapter(val days: List<DaySelectDto> = arrayListOf()) : RecyclerView.Adapter<CalendarDayAdapter.DayVH>() {

    var onSelectedDayChanged: ((day: Day) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_day, parent, false)
        return DayVH(view)
    }

    override fun onBindViewHolder(holder: DayVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    inner class DayVH(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {
            view.dayTBt.isChecked = days[position].isSelected
            view.dayTBt.apply {
                when (days[position].day) {
                    Day.MONDAY -> {
                        textOff = "Mon"; textOn = "Mon"; text = "Mon"
                    }
                    Day.TUESDAY -> {
                        textOff = "Tue"; textOn = "Tue"; text = "Tue"
                    }
                    Day.WEDNESDAY -> {
                        textOff = "Wed"; textOn = "Wed"; text = "Wed"
                    }
                    Day.THURSDAY -> {
                        textOff = "Thu"; textOn = "Thu"; text = "Thu"
                    }
                    Day.FRIDAY -> {
                        textOff = "Fri"; textOn = "Fri"; text = "Fri"
                    }
                    Day.SATURDAY -> {
                        textOff = "Sat"; textOn = "Sat"; text = "Sat"
                    }
                    Day.SUNDAY -> {
                        textOff = "Sun"; textOn = "Sun"; text = "Sun"
                    }
                }
            }
            view.dayTBt.setOnClickListener {
                onSelectedDayChanged?.invoke(days[position].day)
                days.forEach { it.isSelected = false }
                days[position].isSelected = true
                notifyDataSetChanged()
            }
        }
    }
}