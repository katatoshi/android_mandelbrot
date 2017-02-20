package com.katatoshi.androidmandelbrot.viewmodel

import android.databinding.Observable
import android.databinding.ObservableBoolean
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

    val bitmap = ObservableField<Bitmap>()

    val onSizeChanged = { w: Int, h: Int -> MainModel.createSampleBitmap(w, h) }

    val loading = ObservableBoolean(MainModel.loading)

    /**
     * Model から同期します。
     */
    fun refresh() {
        bitmap.set(MainModel.bitmap)
        loading.set(MainModel.loading)
    }


    //region OnPropertyChangedCallback
    fun addPropertyChangedCallbackToModel() {
        addMainModelPropertyChangedCallbackToModel()
    }

    fun removePropertyChangedCallbackFromModel() {
       removeMainModelPropertyChangedCallbackFromModel()
    }


    //region
    private val onMainModelPropertyChangedCallback = { sender: Observable, propertyId: Int ->
        when (propertyId) {
            BR.bitmap -> bitmap.set(MainModel.bitmap)
            BR.loading -> loading.set(MainModel.loading)
        }
    }

    private fun addMainModelPropertyChangedCallbackToModel() {
        MainModel.addOnPropertyChangedCallback(onMainModelPropertyChangedCallback)
    }

    private fun removeMainModelPropertyChangedCallbackFromModel() {
        MainModel.removeOnPropertyChangedCallback(onMainModelPropertyChangedCallback)
    }
    //endregion
    //endregion
}
