package com.katatoshi.androidmandelbrot.view

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.katatoshi.androidmandelbrot.R
import com.katatoshi.androidmandelbrot.databinding.ActivityMainBinding
import com.katatoshi.androidmandelbrot.viewmodel.MainViewModel

/**
 * メインの Activity
 */
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()

        viewModel.addPropertyChangedCallbackToModel()

        viewModel.refresh()
    }

    override fun onPause() {
        viewModel.removePropertyChangedCallbackFromModel()

        super.onPause()
    }
}
