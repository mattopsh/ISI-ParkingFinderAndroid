package com.hfad.parkingfinder.ui.parkingdetails

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingHistoryPointDto
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingNearToProviderDto
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.parkingdetails.dagger.ParkingDetailsInjector
import com.hfad.parkingfinder.ui.parkingdetails.model.Day
import com.hfad.parkingfinder.ui.parkingdetails.model.DaySelectDto
import com.hfad.parkingfinder.utils.resource.toDistance
import kotlinx.android.synthetic.main.activity_parking_details.*
import kotlinx.android.synthetic.main.card_view_near_to_provider.view.*
import kotlinx.android.synthetic.main.card_view_parking_data.view.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.textColor
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.experimental.buildSequence


class ParkingDetailsActivity : BaseActivity() {

    @Inject
    lateinit var presenter: ParkingDetailsPresenter
    private lateinit var calendarDayAdapter: CalendarDayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_details)
        ParkingDetailsInjector().inject(this)
        initBackArrowButtonListener(backArrowIV)
        loadBasicParkingData()
    }

    private fun loadBasicParkingData() {
        val parkingByLocationDto = intent.getParcelableExtra<ParkingByLocationDto>(PARKING_BY_LOCATION)
        val parkingNearToProviderDto = intent.getParcelableExtra<ParkingNearToProviderDto>(PARKING_NEAR_TO_PROVIDER)
        when {
            parkingByLocationDto !== null -> {
                parkingState.visibility = View.VISIBLE
                parkingWithProviderState.visibility = View.INVISIBLE
                initParkingStateView(parkingByLocationDto)
                presenter.requestChartData(parkingByLocationDto.parkingNodeId)
                initStartNavigationButtonListener(parkingByLocationDto.attitude, parkingByLocationDto.longitude)
                initFavoriteButtonListener(parkingByLocationDto.parkingNodeId, parkingByLocationDto.parkingName, parkingByLocationDto.parkingAddress, parkingByLocationDto.attitude, parkingByLocationDto.longitude)
            }
            parkingNearToProviderDto !== null -> {
                parkingState.visibility = View.INVISIBLE
                parkingWithProviderState.visibility = View.VISIBLE
                initParkingStateWithProviderView(parkingNearToProviderDto)
                presenter.requestChartData(parkingNearToProviderDto.parkingNodeId)
                initStartNavigationButtonListener(parkingNearToProviderDto.attitude, parkingNearToProviderDto.longitude)
                initFavoriteButtonListener(parkingNearToProviderDto.parkingNodeId, parkingNearToProviderDto.parkingName, parkingNearToProviderDto.parkingAddress, parkingNearToProviderDto.attitude, parkingNearToProviderDto.longitude)
            }
            else -> Log.e("ERROR", "Empty intent for ParkingDetailsActivity!")
        }
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    fun setLoading(isLoading: Boolean) {
        if (!isLoading) {
            chart.setNoDataText("No data available")
        }
    }

    fun updateChart(chartPoints: List<ParkingHistoryPointDto>) {
        chart.setNoDataText("Loading…")
        initDaysRV(chartPoints)
        setupChart(chartPoints.filter { it.parkingState >= 0 }.filter { it.dayOfWeek == Calendar.getInstance().apply { time = Date() }.get(Calendar.DAY_OF_WEEK) })
    }

    private fun setupChart(chartPoints: List<ParkingHistoryPointDto>) {
        val dataSet = LineDataSet(chartPoints.map { Entry(it.hour.toFloat(), it.parkingState.toFloat()) }, "")
        if (chartPoints.isEmpty()) {
            chart.clear()
            chart.setNoDataText("No data available")
        } else {
            dataSet.apply {
                setDrawVerticalHighlightIndicator(false)
                setDrawHorizontalHighlightIndicator(false)
                color = Color.WHITE
                valueTextColor = Color.WHITE
                valueTextSize = 12f
                valueFormatter = IValueFormatter { freeSpaces, _, _, _ -> freeSpaces.toInt().toString() }
            }
            chart.apply {
                data = LineData(dataSet)
                setVisibleXRangeMaximum(10f)
                description.isEnabled = false
                legend.isEnabled = false
                xAxis.apply {
                    setDrawGridLines(false)
                    setCenterAxisLabels(false)
                    textSize = 12f
                    textColor = Color.WHITE
                    granularity = 1f
                    labelCount = 10
                    position = XAxis.XAxisPosition.BOTTOM
                    valueFormatter = IAxisValueFormatter { value, _ -> value.toInt().toString() }
                }
                axisLeft.apply {
                    isEnabled = false
                }
                axisRight.apply {
                    isEnabled = false
                }
            }
            chart.invalidate()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initParkingStateWithProviderView(parkingNearToProviderDto: ParkingNearToProviderDto) {
        parkingWithProviderState.parkingName2TV.text = parkingNearToProviderDto.parkingName
        parkingWithProviderState.parkingAddress2TV.text = parkingNearToProviderDto.parkingAddress
        parkingWithProviderState.distanceToParkingTV.text = parkingNearToProviderDto.distanceToParking.toDistance()
        parkingWithProviderState.distanceToProviderTV.text = parkingNearToProviderDto.distanceToProvider.toDistance()
        val textColor = when (parkingNearToProviderDto.freeSpaces) {
            0 -> ContextCompat.getColor(parkingWithProviderState.context, R.color.red)
            in 1..5 -> ContextCompat.getColor(parkingWithProviderState.context, R.color.orange)
            in 6..15 -> ContextCompat.getColor(parkingWithProviderState.context, R.color.yellow)
            else -> ContextCompat.getColor(parkingWithProviderState.context, R.color.green)
        }
        parkingWithProviderState.freeSpaces2TV.textColor = textColor
        parkingWithProviderState.freeSpacesText2TV.textColor = textColor
        parkingWithProviderState.freeSpaces2TV.text = when {
            parkingNearToProviderDto.freeSpaces == 0 -> "About 0 "
            parkingNearToProviderDto.freeSpaces == -1 -> "No data"
            else -> "Above " + parkingNearToProviderDto.freeSpaces + " "
        }

        if (parkingNearToProviderDto.dataVeracity == -1) {
            parkingWithProviderState.veracity2PB.visibility = View.INVISIBLE
            parkingWithProviderState.veracity2TV.visibility = View.VISIBLE
            parkingWithProviderState.veracity2TV.text = "0%"
            parkingWithProviderState.freeSpacesText2TV.visibility = View.INVISIBLE

        } else {
            parkingWithProviderState.freeSpacesText2TV.visibility = View.VISIBLE
            parkingWithProviderState.veracity2PB.visibility = View.VISIBLE
            parkingWithProviderState.veracity2TV.visibility = View.VISIBLE
            parkingWithProviderState.veracity2TV.text = "${parkingNearToProviderDto.dataVeracity}%"
            parkingWithProviderState.veracity2PB.progress = parkingNearToProviderDto.dataVeracity
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initParkingStateView(parkingByLocationDto: ParkingByLocationDto) {
        parkingState.parkingNameTV.text = parkingByLocationDto.parkingName
        parkingState.parkingAddressTV.text = parkingByLocationDto.parkingAddress
        parkingState.distanceTV.text = parkingByLocationDto.distance?.toDistance() ?: ""
        val textColor = when (parkingByLocationDto.freeSpaces) {
            -1 -> ContextCompat.getColor(this, R.color.gray)
            0 -> ContextCompat.getColor(this, R.color.red)
            in 1..5 -> ContextCompat.getColor(this, R.color.orange)
            in 6..15 -> ContextCompat.getColor(this, R.color.yellow)
            else -> ContextCompat.getColor(this, R.color.green)
        }
        parkingState.freeSpacesTV.textColor = textColor
        parkingState.freeSpacesTextTV.textColor = textColor
        parkingState.freeSpacesTV.text = when {
            parkingByLocationDto.freeSpaces == 0 -> "About 0 "
            parkingByLocationDto.freeSpaces == -1 -> "No data"
            else -> "Above " + parkingByLocationDto.freeSpaces + " "
        }

        if (parkingByLocationDto.dataVeracity == -1) {
            parkingState.veracityPB.visibility = View.INVISIBLE
            parkingState.veracityTV.visibility = View.VISIBLE
            parkingState.veracityTV.text = "0%"
            parkingState.freeSpacesTextTV.visibility = View.INVISIBLE
        } else {
            parkingState.freeSpacesTextTV.visibility = View.VISIBLE
            parkingState.veracityPB.visibility = View.VISIBLE
            parkingState.veracityTV.visibility = View.VISIBLE
            parkingState.veracityTV.text = "${parkingByLocationDto.dataVeracity}%"
            parkingState.veracityPB.progress = parkingByLocationDto.dataVeracity
        }
    }

    private fun initDaysRV(chartPoints: List<ParkingHistoryPointDto>) {
        val days = buildSequence {
            val currDayOfWeek = Calendar.getInstance().apply { time = Date() }.get(Calendar.DAY_OF_WEEK) - 1
            yieldAll(currDayOfWeek..6)
            yieldAll(0..(currDayOfWeek - 1))
        }
        calendarDayAdapter = CalendarDayAdapter(days.toList().map { DaySelectDto(Day.fromDayOfWeekNumber(it), it == Calendar.getInstance().apply { time = Date() }.get(Calendar.DAY_OF_WEEK) - 1) })
        dayRV.adapter = calendarDayAdapter
        dayRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        calendarDayAdapter.notifyDataSetChanged()
        calendarDayAdapter.onSelectedDayChanged = { day ->
            setupChart(chartPoints.filter { it.dayOfWeek == day.dayOfWeek })
        }
    }

    private fun initStartNavigationButtonListener(attitude: Double, longitude: Double) {
        startNavigationBt.setOnClickListener {
            presenter.startNavigation(attitude, longitude)
        }
    }

    private fun initFavoriteButtonListener(parkingNodeId: Long, parkingName: String, parkingAddress: String, parkingAttitude: Double, parkingLongitude: Double) {
        presenter.getStarButtonState(parkingNodeId)
        favouriteTBt.onCheckedChange { _, isChecked ->
            if (isChecked) {
                presenter.addToFavorites(parkingNodeId, parkingName, parkingAddress, parkingAttitude, parkingLongitude)
            } else {
                presenter.removeFromFavorites(parkingNodeId, parkingName, parkingAddress, parkingAttitude, parkingLongitude)
            }
        }
    }

    fun setStarButtonEnabled(enabled: Boolean) {
        favouriteTBt.isEnabled = enabled
    }

    fun setStartButtonChecked(checked: Boolean) {
        favouriteTBt.isChecked = checked
    }

    companion object {
        const val PARKING_BY_LOCATION = "PARKING_BY_LOCATION"
        const val PARKING_NEAR_TO_PROVIDER = "PARKING_NEAR_TO_PROVIDER"
    }
}
