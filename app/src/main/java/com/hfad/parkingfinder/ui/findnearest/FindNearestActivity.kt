package com.hfad.parkingfinder.ui.findnearest

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.DataVeracity
import com.hfad.parkingfinder.apicalls.parking.dto.FreeSpaces
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import com.hfad.parkingfinder.apicalls.report.dto.Cost
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.dialog.ParkingSearchFilterDialog
import com.hfad.parkingfinder.ui.findnearest.dagger.FindNearestInjector
import com.hfad.parkingfinder.ui.findnearest.rv.FindNearestAdapter
import kotlinx.android.synthetic.main.activity_find_nearest.*
import kotlinx.android.synthetic.main.layout_nearest_action_bar.view.*
import javax.inject.Inject


class FindNearestActivity : BaseActivity() {

    @Inject
    lateinit var presenter: FindNearestPresenter
    private lateinit var findNearestAdapter: FindNearestAdapter
    private var freeSpaces: FreeSpaces? = null
    private var dataVeracity: DataVeracity? = null
    private var cost: Cost? = null
    private var maxDistance: Int? = null
    private var filtersDialog: ParkingSearchFilterDialog? = null
    private var notScaledDistance: Int = 2100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_nearest)
        FindNearestInjector().inject(this)
        initActionBar()
        initRV()
        initSwipeRefresh()
        initSelectAutomaticallyBt()
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    fun setLoading(isLoading: Boolean) {
        swipeRefresh.isRefreshing = isLoading
    }

    fun updateParkingRV(parkingData: List<ParkingByLocationDto>) {
        findNearestAdapter.parkingData.apply {
            clear()
            addAll(parkingData)
        }
        findNearestAdapter.notifyDataSetChanged()
    }

    fun appendParkingRV(parkingData: List<ParkingByLocationDto>) {
        findNearestAdapter.parkingData.addAll(parkingData)
        findNearestAdapter.notifyDataSetChanged()
    }

    private fun initRV() {
        findNearestAdapter = FindNearestAdapter()
        parkingRV.adapter = findNearestAdapter
        parkingRV.layoutManager = LinearLayoutManager(this)
        parkingRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val lastVisibleItem =
                            (rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val totalItemCount = rv.layoutManager.itemCount
                    val visibleItemCount = (rv.layoutManager as LinearLayoutManager).childCount

                    if (totalItemCount <= (lastVisibleItem + visibleItemCount)) {
                        presenter.findParking(findNearestAdapter.itemCount / PAGE_SIZE, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, true)
                    }
                }
            }
        })
        presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance)
    }

    private fun initActionBar() {
        initBackArrowButtonListener(nearestActionBar.backArrow1IV)
        nearestActionBar.titleTV.text = "Find parking"
        nearestActionBar.adjustIV.setOnClickListener {
            filtersDialog?.dismiss()
            filtersDialog = ParkingSearchFilterDialog()
            filtersDialog?.onApplyListener = {
                freeSpaces = filtersDialog?.freeSpaces
                dataVeracity = filtersDialog?.dataVeracity
                cost = filtersDialog?.cost
                maxDistance = filtersDialog?.maxDistance
                notScaledDistance = filtersDialog?.notScaledDistance ?: 2100
                presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance)
            }
            filtersDialog?.invalidateDialog(freeSpaces, dataVeracity, cost, maxDistance, notScaledDistance)
            filtersDialog?.show(fragmentManager, null)
        }
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.swipe_refresh_color_1),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_2),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_3),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_4))
        swipeRefresh.setOnRefreshListener {
            presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance)
        }
    }

    private fun initSelectAutomaticallyBt() {
        autoSelectBt.setOnClickListener {
            presenter.selectAutomatically(findNearestAdapter.parkingData)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}
