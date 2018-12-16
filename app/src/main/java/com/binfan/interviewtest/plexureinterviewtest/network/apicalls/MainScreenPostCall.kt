package com.binfan.interviewtest.plexureinterviewtest.network.apicalls

import com.binfan.interviewtest.plexureinterviewtest.network.APIConstants
import com.binfan.interviewtest.plexureinterviewtest.network.data.request.BasePostData
import com.binfan.interviewtest.plexureinterviewtest.network.data.response.MainScreenResponseData
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

class MainScreenPostCall(private val postString: String):
        BaseRxApiCall<MainScreenResponseData>() {

    interface CallService {
        @POST(APIConstants.API_POST)
        fun apiCall(@Body postData: BasePostData): Single<MainScreenResponseData>
    }

    override fun makeApiCall(): Single<MainScreenResponseData> {
        return retrofit.create(CallService::class.java)
                .apiCall(BasePostData(postString))
    }
}