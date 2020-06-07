package com.hfad.parkingfinder.ui.main.dagger

import com.hfad.parkingfinder.apicalls.dagger.RetrofitAuthorizationClient
import com.hfad.parkingfinder.apicalls.dagger.RetrofitBasicClient
import com.hfad.parkingfinder.apicalls.fb.FbCalls
import com.hfad.parkingfinder.apicalls.parking.ParkingCalls
import com.hfad.parkingfinder.database.room.ParkingDb
import com.hfad.parkingfinder.ui.main.MainActivity
import com.hfad.parkingfinder.ui.main.MainPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule(private val activity: MainActivity) {
    @MainScope
    @Provides
    fun providePresenter(@RetrofitBasicClient retrofitBasicClient: Retrofit,
                         @RetrofitAuthorizationClient retrofitAuthorizationClient: Retrofit): MainPresenter {
        val parkingCalls = retrofitBasicClient.create(ParkingCalls::class.java)
        val fbCalls = retrofitAuthorizationClient.create(FbCalls::class.java)
        val favoriteParkingDao = ParkingDb.getInstance(activity).favoriteParkingDao()
        return MainPresenter(activity, parkingCalls, fbCalls, favoriteParkingDao)
    }
}
