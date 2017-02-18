package com.katatoshi.androidmandelbrot.viewmodel

import android.databinding.Observable
import android.databinding.ObservableField

import com.katatoshi.androidmandelbrot.BR
import com.katatoshi.androidmandelbrot.model.MainModel

/**
 * メインの view model。
 */
class MainViewModel {

    val sampleText = ObservableField<String>()

    private var counter = 0

    /**
     * Model から同期します。
     */
    fun refresh() {
        sampleText.set(MainModel.sampleText)
    }

    fun updateSampleText() {
        counter++
        MainModel.sampleText = "Hello World! ($counter)"
    }


    //region OnPropertyChangedCallback
    fun addPropertyChangedCallbackToModel() {
        addMainModelPropertyChangedCallbackToModel()
    }

    fun removePropertyChangedCallbackToModel() {
        removeMainModelPropertyChangedCallbackToModel()
    }


    //region
    private val onMainModelPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            when (propertyId) {
                BR.sampleText -> sampleText.set(MainModel.sampleText)
            }
        }
    }

    private fun addMainModelPropertyChangedCallbackToModel() {
        MainModel.addOnPropertyChangedCallback(onMainModelPropertyChangedCallback)
    }

    private fun removeMainModelPropertyChangedCallbackToModel() {
        MainModel.removeOnPropertyChangedCallback(onMainModelPropertyChangedCallback)
    }
    //endregion
    //endregion
}
