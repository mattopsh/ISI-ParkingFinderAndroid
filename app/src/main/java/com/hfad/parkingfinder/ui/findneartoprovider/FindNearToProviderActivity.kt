package com.hfad.parkingfinder.ui.findneartoprovider

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.DataVeracity
import com.hfad.parkingfinder.apicalls.parking.dto.FreeSpaces
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingNearToProviderDto
import com.hfad.parkingfinder.apicalls.report.dto.Cost
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.dialog.ParkingSearchFilterDialog
import com.hfad.parkingfinder.ui.findnearest.AutoSelectParking
import com.hfad.parkingfinder.ui.findneartoprovider.dagger.FindNearToProviderInjector
import com.hfad.parkingfinder.ui.findneartoprovider.dto.ProviderLocationDto
import kotlinx.android.synthetic.main.activity_find_near_to_provider.*
import kotlinx.android.synthetic.main.layout_nearest_action_bar.view.*
import javax.inject.Inject

class FindNearToProviderActivity : BaseActivity() {

    @Inject
    lateinit var presenter: FindNearToProviderPresenter
    private lateinit var nearToProviderAdapter: NearToProviderAdapter
    private var freeSpaces: FreeSpaces? = null
    private var dataVeracity: DataVeracity? = null
    private var cost: Cost? = null
    private var maxDistance: Int? = null
    private var filtersDialog: ParkingSearchFilterDialog? = null
    private var notScaledDistance: Int = 2100
    private var providerAttitude: Double? = null
    private var providerLongitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_near_to_provider)
        FindNearToProviderInjector().inject(this)
        val providerLocationDto = intent.getParcelableExtra<ProviderLocationDto>(PROVIDER)
        providerAttitude = providerLocationDto.attitude
        providerLongitude = providerLocationDto.longitude
        initActionBar(providerLocationDto.providerName)
        initRV()
        initSwipeRefresh()
        initSelectAutomaticallyButtonListener()
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    fun setLoading(isLoading: Boolean) {
        swipeRefresh.isRefreshing = isLoading
    }

    fun updateParkingRV(parkingData: List<ParkingNearToProviderDto>) {
        nearToProviderAdapter.parkingData.clear()
        nearToProviderAdapter.parkingData.addAll(parkingData)
        nearToProviderAdapter.notifyDataSetChanged()
    }

    fun appendParkingRV(parkingData: List<ParkingNearToProviderDto>) {
        nearToProviderAdapter.parkingData.addAll(parkingData)
        nearToProviderAdapter.notifyDataSetChanged()
    }

    private fun initRV() {
        nearToProviderAdapter = NearToProviderAdapter()
        parkingRV.adapter = nearToProviderAdapter
        parkingRV.layoutManager = LinearLayoutManager(this)
        parkingRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val lastVisibleItem =
                            (rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val totalItemCount = rv.layoutManager.itemCount
                    val visibleItemCount = (rv.layoutManager as LinearLayoutManager).childCount

                    if (totalItemCount <= (lastVisibleItem + visibleItemCount)) {
                        presenter.findParking(nearToProviderAdapter.itemCount / PAGE_SIZE, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, providerAttitude!!, providerLongitude!!)
                    }
                }
            }
        })
        presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, providerAttitude!!, providerLongitude!!)
    }

    @SuppressLint("SetTextI18n")
    private fun initActionBar(providerName: String) {
        initBackArrowButtonListener(nearestActionBar.backArrow1IV)
        nearestActionBar.titleTV.text = "Parking near $providerName"
        nearestActionBar.adjustIV.setOnClickListener {
            filtersDialog?.dismiss()
            filtersDialog = ParkingSearchFilterDialog()
            filtersDialog?.onApplyListener = {
                freeSpaces = filtersDialog?.freeSpaces
                dataVeracity = filtersDialog?.dataVeracity
                cost = filtersDialog?.cost
                maxDistance = filtersDialog?.maxDistance
                notScaledDistance = filtersDialog?.notScaledDistance ?: 2100
                presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, providerAttitude!!, providerLongitude!!)
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
            presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, providerAttitude!!, providerLongitude!!)
        }
    }

    private fun initSelectAutomaticallyButtonListener() {
        autoSelectBt.setOnClickListener {
            AutoSelectParking.selectAutomaticallyNearToProvider(nearToProviderAdapter.parkingData)
        }
    }

    companion object {
        const val PROVIDER = "PROVIDER"
        const val PAGE_SIZE = 20
    }
}