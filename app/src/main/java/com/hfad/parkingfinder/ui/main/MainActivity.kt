package com.hfad.parkingfinder.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.animation.AnimationUtils
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.ParkingByLocationDto
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.findbylocation.FindByLocationActivity
import com.hfad.parkingfinder.ui.findnearest.FindNearestActivity
import com.hfad.parkingfinder.ui.findprovider.FindProviderActivity
import com.hfad.parkingfinder.ui.main.dagger.MainInjector
import com.hfad.parkingfinder.ui.reportinconsistency.select.SelectInconsistencyTypeActivity
import com.hfad.parkingfinder.ui.reportparkingstate.ReportParkingStateActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_main.view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var presenter: MainPresenter
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainInjector().inject(this)
        initButtonListeners()
        initStarBt()
        initRV()
        setUserPhoto()
        initSwipeRefresh()
        handlePermissions(requiredPermissions.toMutableSet())
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    fun updateParkingRV(parkingData: List<ParkingByLocationDto>) {
        favoriteAdapter.apply {
            this.parkingData.clear()
            this.parkingData.addAll(parkingData)
            notifyDataSetChanged()
        }
    }

    fun updateUserPhoto(bitmap: Bitmap) {
        aboutUserIV.setImageBitmap(bitmap)
    }

    fun setLoading(isLoading: Boolean) {
        navView.swipeRefresh.isRefreshing = isLoading
    }

    private fun initButtonListeners() {
        nearestBt.setOnClickListener {
            startActivity<FindNearestActivity>()
        }
        byLocationBt.setOnClickListener {
            startActivity<FindByLocationActivity>()
        }
        nearToProviderBt.setOnClickListener {
            startActivity<FindProviderActivity>()
        }
        parkingStateBt.setOnClickListener {
            startActivity<ReportParkingStateActivity>()
        }
        inconsistencyBt.setOnClickListener {
            startActivity<SelectInconsistencyTypeActivity>()
        }
    }

    private fun initRV() {
        favoriteAdapter = FavoriteAdapter()
        navView.favoriteRV.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        presenter.getFavoriteParkingState()
    }

    private fun initSwipeRefresh() {
        navView.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.swipe_refresh_color_1),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_2),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_3),
                ContextCompat.getColor(this, R.color.swipe_refresh_color_4))
        navView.swipeRefresh.setOnRefreshListener {
            presenter.getFavoriteParkingState()
        }
    }

    private fun initStarBt() {
        favouriteFilledIV.startAnimation(AnimationUtils.loadAnimation(this, R.anim.star_anim))
        favouriteBt.setOnClickListener {
            drawerLayout.openDrawer(Gravity.START, true)
        }
    }

    private fun setUserPhoto() {
        presenter.getUserPhoto()
    }

    private fun handlePermissions(permissions: MutableSet<String>) {
        val notGrantedPermissions = permissions.filter { ContextCompat.checkSelfPermission(this@MainActivity, it) != PackageManager.PERMISSION_GRANTED }.toTypedArray()
        if (notGrantedPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, notGrantedPermissions, PERMISSIONS_REQUEST_CODE);
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissionsList: Array<out String>, grantResults: IntArray) {
        val notGrantedPermissions = mutableSetOf<String>()
        permissionsList.forEachIndexed { index, permission ->
            if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                notGrantedPermissions.add(permission)
            }
        }
        if (notGrantedPermissions.isNotEmpty()) {
            handlePermissions(notGrantedPermissions)
        }
    }

    companion object {
        private val requiredPermissions = setOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        private const val PERMISSIONS_REQUEST_CODE = 0
    }
}