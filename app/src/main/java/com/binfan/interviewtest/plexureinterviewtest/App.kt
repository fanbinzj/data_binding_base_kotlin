package com.binfan.interviewtest.plexureinterviewtest

import android.app.Application
import com.binfan.interviewtest.plexureinterviewtest.persistence.User
import com.binfan.interviewtest.plexureinterviewtest.persistence.UserDao
import com.binfan.interviewtest.plexureinterviewtest.persistence.UsersDatabase
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class App : Application() {

    companion object {
        lateinit var instance: App
            private set

        val DEFAULT_NAME_ARRAY = arrayOf("James", "Emily", "Ben", "Alex", "Peter")
    }

    lateinit var userDao: UserDao

    override fun onCreate() {
        super.onCreate()
        instance = this

        userDao = UsersDatabase.getInstance(App.instance).userDao()

        checkAndInitialDemoData()
    }

    fun checkAndInitialDemoData() {
        val userDao = UsersDatabase.getInstance(this).userDao()
        userDao.getAllUsers()
                .flatMap {
                    if (it == null || it.size == 0) {
                        App.DEFAULT_NAME_ARRAY.forEach {
                            userDao.insertUser(User(it))
                        }
                        userDao.getAllUsers()
                    } else {
                        Single.just(it);
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}
