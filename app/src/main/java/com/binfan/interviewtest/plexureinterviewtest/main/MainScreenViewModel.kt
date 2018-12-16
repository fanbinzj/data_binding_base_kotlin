package com.binfan.interviewtest.plexureinterviewtest.main

import android.databinding.Bindable
import android.util.Log
import com.binfan.interviewtest.plexureinterviewtest.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.binfan.interviewtest.plexureinterviewtest.base.BaseViewModel
import com.binfan.interviewtest.plexureinterviewtest.network.apicalls.MainScreenPostCall
import com.binfan.interviewtest.plexureinterviewtest.network.data.response.MainScreenResponseData
import com.binfan.interviewtest.plexureinterviewtest.persistence.User
import com.binfan.interviewtest.plexureinterviewtest.persistence.UsersDatabase
import com.binfan.interviewtest.plexureinterviewtest.BR

class MainScreenViewModel: BaseViewModel() {

    companion object {
        const val DEFAULT_NAME_ARRAY = 600L
    }

    var messageForShowing: String = "Initial message"
        @Bindable get() = field
        set(value) {
            field = value
            notifyPropertyChanged(BR.messageForShowing)
        }

    fun onFirstButtonClick() {
        messageForShowing = "loading..."
        MainScreenPostCall("Test data")
                .apiCallWithCheck()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { responseData -> onPostSuccess(responseData) },
                        { error -> handleError(error) })
    }

    fun onSecondButtonClick() {
        messageForShowing = "loading..."
        val userDao = UsersDatabase.getInstance(App.instance).userDao()
        userDao.getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { resultData -> onDBFetchSuccess(resultData) },
                        { error -> handleError(error) })
    }

    fun onPostSuccess(responseData: MainScreenResponseData) {
        var responseString = "Empty response data"
        responseData?.let { responseString = it.data }
        Log.d(TAG, "onSuccess(): data = " +responseString)
        messageForShowing = responseString
    }

    fun onDBFetchSuccess(userList: List<User>) {
        messageForShowing = "All users from DB: "
        userList.forEach {
            messageForShowing = messageForShowing + "${it.userName} "
        }
    }
}