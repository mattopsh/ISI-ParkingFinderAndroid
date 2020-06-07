package com.hfad.parkingfinder.apicalls

import com.hfad.parkingfinder.apicalls.auth.AuthCalls
import com.hfad.parkingfinder.apicalls.auth.dto.AccessTokenResponseDto
import com.hfad.parkingfinder.database.sharedpreferences.PreferencesManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://parking-finder-spring.herokuapp.com/api/"
    private const val API_VERSION = "1"
    private const val REFRESH_TOKEN_RETRY_NUMBER = 2
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit === null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build()
        }
        return retrofit!!
    }

    fun getAuthorizationClient(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getAuthorizationOkHttpClient())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request()
                    request = request.newBuilder()
                            .addHeader("apiVersion", API_VERSION)
                            .build()
                    chain.proceed(request)
                }
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
    }


    private fun getAuthorizationOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    authInterceptor(chain)
                }
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
    }

    private fun setAuthorizationHeader(request: Request, accessToken: String): Request {
        return request.newBuilder()
                .header("apiVersion", API_VERSION)
                .header("Authorization", accessToken)
                .build()
    }

    private fun getNewAccessToken(): Response<AccessTokenResponseDto> {
        return getClient().create(AuthCalls::class.java)
                .getNewAccessToken(PreferencesManager.getRefreshToken())
                .blockingGet()
    }

    private fun authInterceptor(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = PreferencesManager.getAccessToken()
        var request = setAuthorizationHeader(chain.request(), accessToken)
        val response = chain.proceed(request)
        if (response.code() == 401) {
            synchronized(this) {
                val currentToken = PreferencesManager.getAccessToken()
                if (currentToken.isNotEmpty() && currentToken == accessToken) {
                    var newAccessTokenResponse: Response<AccessTokenResponseDto>?
                    for (tryNumber in 1..REFRESH_TOKEN_RETRY_NUMBER) {
                        newAccessTokenResponse = getNewAccessToken()
                        if (newAccessTokenResponse.isSuccessful) {
                            PreferencesManager.saveAccessToken(newAccessTokenResponse.body()!!.accessToken)
                            break
                        }
                    }
                }

                if (PreferencesManager.getAccessToken().isNotEmpty()) {
                    request = setAuthorizationHeader(request, PreferencesManager.getAccessToken())
                    return chain.proceed(request)
                }
            }
        }
        return response
    }
}