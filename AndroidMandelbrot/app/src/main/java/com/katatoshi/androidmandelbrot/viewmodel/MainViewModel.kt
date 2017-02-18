package com.katatoshi.androidmandelbrot.viewmodel

import android.databinding.Observable
import android.databinding.ObservableField

import com.katatoshi.androidmandelbrot.BR
import com.katatoshi.androidmandelbrot.extension.addOnPropertyChangedCallback
import com.katatoshi.androidmandelbrot.extension.removeOnPropertyChangedCallback
import com.katatoshi.androidmandelbrot.model.MainModel

/**
 * メインの view model。
 */
class MainViewModel {

    val sampleText = ObservableField<String>()

    /**
     * Model から同期します。
     */
    fun refresh() {
        sampleText.set(MainModel.sampleText)
    }

    fun clearCounter() {
        MainModel.counter = 0
    }

    fun incrementCounter() {
        MainModel.counter += 1
    }


    //region OnPropertyChangedCallback
    fun addPropertyChangedCallbackToModel() {
        addMainModelPropertyChangedCallbackToModel()
    }

    fun removePropertyChangedCallbackToModel() {
        removeMainModelPropertyChangedCallbackToModel()
    }


    //region
    private val onMainModelPropertyChangedCallback = { sender: Observable, propertyId: Int ->
        when (propertyId) {
            BR.sampleText -> sampleText.set(MainModel.sampleText)
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
