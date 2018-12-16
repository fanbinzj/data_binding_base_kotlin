package com.binfan.interviewtest.plexureinterviewtest.network.apicalls

import com.binfan.interviewtest.plexureinterviewtest.network.APIConstants
import com.binfan.interviewtest.plexureinterviewtest.network.data.BaseData
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open abstract class BaseRxApiCall<T: BaseData> {

    companion object {

        val retrofit: Retrofit = getNewRetrofitWithUrl(APIConstants.BASE_URL)

        private fun getNewRetrofitWithUrl(url: String): Retrofit {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }

    abstract fun makeApiCall(): Single<T>

    fun apiCallWithCheck(): Single<T> {
        return makeApiCall()
                .doOnSuccess { basicData -> if (basicData == null) throw Error()}
                .subscribeOn(Schedulers.io())
    }
}
