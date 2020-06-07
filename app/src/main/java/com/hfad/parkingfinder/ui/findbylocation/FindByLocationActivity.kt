package com.hfad.parkingfinder.ui.findbylocation

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.DataVeracity
import com.hfad.parkingfinder.apicalls.parking.dto.FreeSpaces
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import com.hfad.parkingfinder.apicalls.report.dto.Cost
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.dialog.ParkingSearchFilterDialog
import com.hfad.parkingfinder.ui.findbylocation.dagger.FindByLocationInjector
import com.hfad.parkingfinder.ui.findnearest.rv.FindNearestAdapter
import com.hfad.parkingfinder.utils.edittext.onSubmit
import kotlinx.android.synthetic.main.activity_find_by_location.*
import kotlinx.android.synthetic.main.layout_normal_action_bar.view.*
import kotlinx.android.synthetic.main.layout_search_action_bar.view.*
import javax.inject.Inject

class FindByLocationActivity : BaseActivity() {

    @Inject
    lateinit var presenter: FindByLocationPresenter
    private var handler: Handler? = null
    private var freeSpaces: FreeSpaces? = null
    private var dataVeracity: DataVeracity? = null
    private var cost: Cost? = null
    private var maxDistance: Int? = null
    private var parkingNameOrAddress: String? = null
    private lateinit var findByLocationAdapter: FindNearestAdapter
    private lateinit var filtersDialog: ParkingSearchFilterDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_by_location)
        FindByLocationInjector().inject(this)
        initActionBar()
        initRV()
        initSwipeRefresh()
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    fun setLoading(isLoading: Boolean) {
        swipeRefresh.isRefreshing = isLoading
    }

    fun updateParkingRV(parkingData: List<ParkingByLocationDto>) {
        findByLocationAdapter.parkingData.apply {
            clear()
            addAll(parkingData)
        }
        findByLocationAdapter.notifyDataSetChanged()
    }

    private fun initRV() {
        findByLocationAdapter = FindNearestAdapter()
        parkingRV.adapter = findByLocationAdapter
        parkingRV.layoutManager = LinearLayoutManager(this)
        parkingRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val lastVisibleItem =
                            (rv.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    val totalItemCount = rv.layoutManager.itemCount
                    val visibleItemCount = (rv.layoutManager as LinearLayoutManager).childCount

                    if (totalItemCount <= (lastVisibleItem + visibleItemCount)) {
                        presenter.findParking(findByLocationAdapter.itemCount / PAGE_SIZE, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, parkingNameOrAddress, true)
                    }
                }
            }
        })
        presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, parkingNameOrAddress)
    }

    private fun initActionBar() {
        filtersDialog = ParkingSearchFilterDialog()
        initActionBarBasicBehavior(normalActionBar, searchActionBar, R.color.green)
        normalActionBar.titleTV.text = "Find parking"
        searchActionBar.searchET.hint = "Type name or address"
        searchActionBar.searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                handler?.removeCallbacksAndMessages(null)
                handler = Handler()
                handler!!.postDelayed({
                    parkingNameOrAddress = s.toString()
                    presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, parkingNameOrAddress)
                }, DEBOUNCE)
            }
        })
        searchActionBar.clearIV.setOnClickListener {
            searchActionBar.searchET.text.clear()
        }
        searchActionBar.adjustIV.setOnClickListener {
            filtersDialog.onApplyListener = {
                freeSpaces = filtersDialog.freeSpaces
                dataVeracity = filtersDialog.dataVeracity
                cost = filtersDialog.cost
                maxDistance = filtersDialog.maxDistance
                presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, parkingNameOrAddress)
            }
            filtersDialog.show(fragmentManager, null)
        }
        searchActionBar.searchET.onSubmit {
            presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, parkingNameOrAddress)
            hideKeyboard()
        }
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.swipe_refresh_color_1),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_2),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_3),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_4))
        swipeRefresh.setOnRefreshListener {
            presenter.findParking(0, PAGE_SIZE, freeSpaces, dataVeracity, cost, maxDistance, parkingNameOrAddress)
        }
    }

    fun appendToParkingRV(parkingData: List<ParkingByLocationDto>) {
        findByLocationAdapter.parkingData.addAll(parkingData)
        findByLocationAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val DEBOUNCE = 1500L
        private const val PAGE_SIZE = 20
    }
}