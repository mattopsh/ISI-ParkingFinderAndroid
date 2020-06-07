package com.hfad.parkingfinder.ui.dialog

import android.app.Dialog
import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.SeekBar
import com.hfad.parkingfinder.R
import com.hfad.parkingfinder.apicalls.provider.dto.ProviderType
import com.hfad.parkingfinder.utils.resource.getColorId
import com.hfad.parkingfinder.utils.resource.toDistance
import com.hfad.parkingfinder.utils.scaleProgress
import kotlinx.android.synthetic.main.dialog_provider_filters.view.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange

class ProviderSearchFilterDialog : DialogFragment() {
    var onApplyListener: (() -> Unit)? = null
    var onCancelListener: (() -> Unit)? = null
    var providerTypes = mutableSetOf<ProviderType>()
        private set
    var maxDistance: Int? = null
    var notScaledDistance: Int = 2100

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity.layoutInflater.inflate(R.layout.dialog_provider_filters, null)
        builder.setView(view)
        initListeners(view)
        val dialog = builder.create()
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun invalidateDialog(providerTypes: MutableSet<ProviderType>, maxDistance: Int?, notScaledDistance: Int) {
        this.providerTypes = providerTypes
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
        view.distanceTV.text = scaleProgress(2100, MIN_DISTANCE, MAX_DISTANCE).toDistance()
        if (notScaledDistance == 2100) view.distanceTV.setTextColor(resources.getColorId(R.color.gray))
        view.distanceSB.progress = notScaledDistance
        view.distanceSB.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {

                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        val scaledProgress = scaleProgress(progress, MIN_DISTANCE, MAX_DISTANCE)
                        maxDistance = scaledProgress
                        view.distanceTV.text = scaledProgress.toDistance()
                        view.distanceTV.setTextColor(resources.getColorId(R.color.colorAccent))
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                }
        )
        initShopTypeListeners(view)
        setFilterButtonsState(view)
        initClearButton(view)
    }

    private fun initShopTypeListeners(view: View) {
        view.foodTBt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.FOOD) else providerTypes.remove(ProviderType.FOOD)
        }
        view.diyTBt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.DIY) else providerTypes.remove(ProviderType.DIY)
        }
        view.clothesTbt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.CLOTHES) else providerTypes.remove(ProviderType.CLOTHES)
        }
        view.beautyTbt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.BEAUTY) else providerTypes.remove(ProviderType.BEAUTY)
        }
        view.interiorTbt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.INTERIOR) else providerTypes.remove(ProviderType.INTERIOR)
        }
        view.rtvAgdTbt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.ELECTRONICS) else providerTypes.remove(ProviderType.ELECTRONICS)
        }
        view.sportTbt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.SPORT) else providerTypes.remove(ProviderType.SPORT)
        }
        view.carTBt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.CAR) else providerTypes.remove(ProviderType.CAR)
        }
        view.entmtTBt.onCheckedChange { _, isChecked ->
            if (isChecked) providerTypes.add(ProviderType.ENTMT) else providerTypes.remove(ProviderType.ENTMT)
        }
    }

    private fun setFilterButtonsState(view: View) {
        view.foodTBt.isChecked = providerTypes.contains(ProviderType.FOOD)
        view.diyTBt.isChecked = providerTypes.contains(ProviderType.DIY)
        view.clothesTbt.isChecked = providerTypes.contains(ProviderType.CLOTHES)
        view.beautyTbt.isChecked = providerTypes.contains(ProviderType.BEAUTY)
        view.interiorTbt.isChecked = providerTypes.contains(ProviderType.INTERIOR)
        view.rtvAgdTbt.isChecked = providerTypes.contains(ProviderType.ELECTRONICS)
        view.sportTbt.isChecked = providerTypes.contains(ProviderType.SPORT)
        view.carTBt.isChecked = providerTypes.contains(ProviderType.CAR)
        view.entmtTBt.isChecked = providerTypes.contains(ProviderType.ENTMT)
    }

    private fun initClearButton(view: View) {
        view.clearTV.setOnClickListener {
            view.distanceTV.text = scaleProgress(2100, MIN_DISTANCE, MAX_DISTANCE).toDistance()
            view.distanceSB.progress = 2100
            notScaledDistance = 2100
            maxDistance = null
            view.distanceTV.setTextColor(resources.getColorId(R.color.gray))
            view.foodTBt.isChecked = false
            view.diyTBt.isChecked = false
            view.clothesTbt.isChecked = false
            view.beautyTbt.isChecked = false
            view.interiorTbt.isChecked = false
            view.rtvAgdTbt.isChecked = false
            view.sportTbt.isChecked = false
            view.carTBt.isChecked = false
            view.entmtTBt.isChecked = false
        }
    }

    companion object {
        private const val MIN_DISTANCE = 100
        private const val MAX_DISTANCE = 5000
    }

}