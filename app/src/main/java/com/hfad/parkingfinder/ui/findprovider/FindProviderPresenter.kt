package com.hfad.parkingfinder.ui.findprovider

import com.hfad.parkingfinder.apicalls.provider.ProviderCalls
import com.hfad.parkingfinder.apicalls.provider.dto.ProviderType
import com.hfad.parkingfinder.utils.location.LocationProvider
import com.hfad.parkingfinder.utils.mvp.BasePresenter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FindProviderPresenter(private val activity: FindProviderActivity, private val providerCalls: ProviderCalls) : BasePresenter {

    override val compositeDisposable = CompositeDisposable()

    fun findProviders(pageNumber: Int, pageSize: Int, maxDistance: Int?, providerNameOrAddress: String?, providerTypes: List<ProviderType>, append: Boolean = false) {
        activity.setLoading(true)
        compositeDisposable.addAll(
                Single.fromCallable { LocationProvider.getLocation(activity) }
                        .flatMap {
                            providerCalls.findProviders(pageNumber, pageSize, maxDistance, providerNameOrAddress, providerTypes, it.latitude, it.longitude)
                        }
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.isSuccessful) {
                                        if (append) {
                                            activity.appendToProviderRV(it.body()!!)
                                        } else {
                                            activity.updateProviderRV(it.body()!!)
                                        }
                                    } else {
                                        activity.showToast("No internet connection")
                                    }
                                    activity.setLoading(false)
                                },
                                {
                                    if (it is NullPointerException) {
                                        activity.showToast("Cannot retrieve location")
                                    } else {
                                        activity.showToast("No internet connection")
                                    }
                                    activity.setLoading(false)
                                }
                        )
        )
    }
}
