package com.katatoshi.androidmandelbrot.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.Bitmap

import com.katatoshi.androidmandelbrot.BR

/**
 * メインの model。
 */
object MainModel : BaseObservable() {

    @get:Bindable
    var sampleBitmap: Bitmap? = null
        set(sampleBitmap) {
            if (this.sampleBitmap == sampleBitmap) {
                return
            }

            field = sampleBitmap
            notifyPropertyChanged(BR.sampleBitmap)
        }

    @get:Bindable
    var loading = false
        private set(loading) {
            if (this.loading == loading) {
                return
            }

            field = loading
            notifyPropertyChanged(BR.loading)
        }

    fun createSampleBitmap(w: Int, h: Int) {
        if (loading) {
            return
        }

        loading = true

        RandomColorPixels.createBitmapPromise(w, h)
                .done { sampleBitmap = it }
                .fail { sampleBitmap = null }
                .always { state, bitmap, throwable -> loading = false }
    }
}
