package com.hfad.parkingfinder.ui.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.SeekBar
import android.widget.ToggleButton
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.parking.dto.DataVeracity
import com.hfad.parkingfinder.apicalls.parking.dto.FreeSpaces
import com.hfad.parkingfinder.apicalls.report.dto.Cost
import com.hfad.parkingfinder.utils.resource.getColorId
import com.hfad.parkingfinder.utils.resource.toDistance
import com.hfad.parkingfinder.utils.scaleProgress
import kotlinx.android.synthetic.main.dialog_parking_filters.view.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange

class ParkingSearchFilterDialog : DialogFragment() {
    var onApplyListener: (() -> Unit)? = null
    var onCancelListener: (() -> Unit)? = null
    var freeSpaces: FreeSpaces? = null
    var dataVeracity: DataVeracity? = null
    var cost: Cost? = null
    var maxDistance: Int? = null
    var notScaledDistance: Int = 2100

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_parking_filters, null)
        builder.setView(view)
        initListeners(view)
        val dialog = builder.create()
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun invalidateDialog(freeSpaces: FreeSpaces?, dataVeracity: DataVeracity?, cost: Cost?, maxDistance: Int?, notScaledDistance: Int) {
        this.freeSpaces = freeSpaces
        this.dataVeracity = dataVeracity
        this.cost = cost
        this.maxDistance = maxDistance
        this.notScaledDistance = notScaledDistance
    }

    private fun initListeners(view: View) {
        view.applyTV.setOnClickListener {
            onApplyListener?.invoke()
            dismiss()
        }
        view.cancelTV.setOnClickListener {
            onCancelListener?.invoke()
            dismiss()
        }
        view.distanceTV.text = scaleProgress(notScaledDistance, MIN_DISTANCE, MAX_DISTANCE).toDistance()
        if (notScaledDistance == 2100) view.distanceTV.setTextColor(resources.getColorId(R.color.gray))
        view.distanceSB.progress = notScaledDistance
        view.distanceSB.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        val scaledProgress = scaleProgress(progress, MIN_DISTANCE, MAX_DISTANCE)
                        maxDistance = scaledProgress
                        notScaledDistance = progress
                        view.distanceTV.text = scaledProgress.toDistance()
                        view.distanceTV.setTextColor(resources.getColorId(R.color.colorAccent))
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
                }
        )
        setFilterButtonsState(view)
        initFilterButtonsListeners(view)
        initClearButton(view)
    }

    private fun setFilterButtonsState(view: View) {
        when (cost) {
            Cost.PAID -> view.paidParkingTBt.isChecked = true
            Cost.LIMITED -> view.limitedParkingTBt.isChecked = true
            Cost.FREE -> view.freeParkingTBt.isChecked = true
        }
        when (dataVeracity) {
            DataVeracity.LOW -> view.veracity30Bt.isChecked = true
            DataVeracity.MEDIUM -> view.veracity60Bt.isChecked = true
            DataVeracity.HIGH -> view.veracity90Bt.isChecked = true
        }

        when (freeSpaces) {
            FreeSpaces.LOW -> view.freeSpaces5TBt.isChecked = true
            FreeSpaces.MEDIUM -> view.freeSpaces15TBt.isChecked = true
            FreeSpaces.HIGH -> view.freeSpaces30TBt.isChecked = true
        }
    }

    private fun initFilterButtonsListeners(view: View) {
        val costButtons = listOf<ToggleButton>(view.paidParkingTBt, view.limitedParkingTBt, view.freeParkingTBt)
        costButtons.forEach { currTbt ->
            currTbt.onCheckedChange { _, isChecked ->
                if (isChecked) {
                    costButtons.forEach { if (it !== currTbt) it.isChecked = false }
                    when (currTbt) {
                        view.paidParkingTBt -> cost = Cost.FREE
                        view.limitedParkingTBt -> cost = Cost.LIMITED
                        view.freeParkingTBt -> cost = Cost.PAID
                    }
                } else {
                    cost = null
                }
            }
        }

        val dataVeracityButtons = listOf<ToggleButton>(view.veracity30Bt, view.veracity60Bt, view.veracity90Bt)
        dataVeracityButtons.forEach { currTbt ->
            currTbt.onCheckedChange { _, isChecked ->
                if (isChecked) {
                    dataVeracityButtons.forEach { if (it !== currTbt) it.isChecked = false }
                    when (currTbt) {
                        view.veracity30Bt -> dataVeracity = DataVeracity.LOW
                        view.veracity60Bt -> dataVeracity = DataVeracity.MEDIUM
                        view.veracity90Bt -> dataVeracity = DataVeracity.HIGH
                    }
                } else {
                    dataVeracity = null
                }
            }
        }

        val freeSpacesButtons = listOf<ToggleButton>(view.freeSpaces5TBt, view.freeSpaces15TBt, view.freeSpaces30TBt)
        freeSpacesButtons.forEach { currTbt ->
            currTbt.onCheckedChange { _, isChecked ->
                if (isChecked) {
                    freeSpacesButtons.forEach { if (it !== currTbt) it.isChecked = false }
                    when (currTbt) {
                        view.freeSpaces5TBt -> freeSpaces = FreeSpaces.LOW
                        view.freeSpaces15TBt -> freeSpaces = FreeSpaces.MEDIUM
                        view.freeSpaces30TBt -> freeSpaces = FreeSpaces.HIGH
                    }
                } else {
                    freeSpaces = null
                }
            }
        }
    }

    private fun initClearButton(view: View) {
        view.clearTV.setOnClickListener {
            view.distanceTV.text = scaleProgress(2100, MIN_DISTANCE, MAX_DISTANCE).toDistance()
            view.distanceSB.progress = 2100
            notScaledDistance = 2100
            maxDistance = null
            view.distanceTV.setTextColor(resources.getColorId(R.color.gray))
            val freeSpacesButtons = listOf<ToggleButton>(view.freeSpaces5TBt, view.freeSpaces15TBt, view.freeSpaces30TBt)
            freeSpacesButtons.forEach { it.isChecked = false }
            val dataVeracityButtons = listOf<ToggleButton>(view.veracity30Bt, view.veracity60Bt, view.veracity90Bt)
            dataVeracityButtons.forEach { it.isChecked = false }
            val costButtons = listOf<ToggleButton>(view.paidParkingTBt, view.limitedParkingTBt, view.freeParkingTBt)
            costButtons.forEach { it.isChecked = false }
        }
    }

    companion object {
        private const val MIN_DISTANCE = 100
        private const val MAX_DISTANCE = 5000
    }
}