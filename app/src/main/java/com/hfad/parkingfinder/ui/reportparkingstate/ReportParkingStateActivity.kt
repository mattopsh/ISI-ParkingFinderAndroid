package com.hfad.parkingfinder.ui.reportparkingstate

import android.os.Bundle
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.ui.base.BaseActivity
import com.hfad.parkingfinder.ui.reportparkingstate.dagger.ReportParkingStateInjector
import com.hfad.parkingfinder.ui.reportparkingstate.mvp.ParkingStateEnum
import com.hfad.parkingfinder.ui.reportparkingstate.mvp.ReportParkingStatePresenter
import kotlinx.android.synthetic.main.activity_report_parking_state.*
import javax.inject.Inject

class ReportParkingStateActivity : BaseActivity() {

    @Inject
    lateinit var presenter: ReportParkingStatePresenter
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_parking_state)
        ReportParkingStateInjector().inject(this)
        initBackArrowButtonListener(backArrowIV)
        initButtonsListeners()
    }

    override fun onDestroy() {
        presenter.disposeRX()
        super.onDestroy()
    }

    fun showTryAgainDialog(parkingStateDto: ParkingStateDto) {
        // todo
    }

    fun setLoading(isLoading: Boolean) {
        // todo
    }

    private fun initButtonsListeners() {
        about0Bt.setOnClickListener {
            presenter.sendParkingState(ParkingStateEnum.ABOUT0)
        }

        above5Bt.setOnClickListener {
            presenter.sendParkingState(ParkingStateEnum.ABOVE5)
        }

        above15Bt.setOnClickListener {
            presenter.sendParkingState(ParkingStateEnum.ABOVE15)
        }

        above30Bt.setOnClickListener {
            presenter.sendParkingState(ParkingStateEnum.ABOVE30)
        }
    }
}