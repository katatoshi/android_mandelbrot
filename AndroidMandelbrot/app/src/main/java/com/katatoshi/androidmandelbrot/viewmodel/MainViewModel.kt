package com.katatoshi.androidmandelbrot.viewmodel

import android.databinding.Observable
import android.databinding.ObservableField
import android.graphics.Bitmap

import com.katatoshi.androidmandelbrot.BR
import com.katatoshi.androidmandelbrot.extension.DataBindingExtensions.addOnPropertyChangedCallback
import com.katatoshi.androidmandelbrot.extension.DataBindingExtensions.removeOnPropertyChangedCallback
import com.katatoshi.androidmandelbrot.model.MainModel

/**
 * メインの view model。
 */
class MainViewModel {

    var sampleBitmap = ObservableField<Bitmap>()

    val onSizeChanged = { w: Int, h: Int -> MainModel.createSampleBitmap(w, h) }

    /**
     * Model から同期します。
     */
    fun refresh() {
        sampleBitmap.set(MainModel.sampleBitmap)
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
            BR.sampleBitmap -> sampleBitmap.set(MainModel.sampleBitmap)
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
