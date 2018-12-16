package com.binfan.interviewtest.plexureinterviewtest.main

import android.app.Activity
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.binfan.interviewtest.plexureinterviewtest.R
import com.binfan.interviewtest.plexureinterviewtest.databinding.ActivityMainBinding

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = MainScreenViewModel()
    }
}