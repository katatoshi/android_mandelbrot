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
    var bitmap: Bitmap? = null
        set(bitmap) {
            if (this.bitmap == bitmap) {
                return
            }

            field = bitmap
            notifyPropertyChanged(BR.bitmap)
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
                .done { bitmap = it }
                .fail { bitmap = null }
                .always { state, bitmap, throwable -> loading = false }
    }
}
