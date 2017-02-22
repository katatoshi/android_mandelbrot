package com.katatoshi.androidmandelbrot.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.Bitmap

import com.katatoshi.androidmandelbrot.BR
import java8.util.concurrent.CompletableFuture

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

    var completableFuture: CompletableFuture<Bitmap?>? = null

    fun loadBitmap(w: Int, h: Int) {
        if (loading) {
            completableFuture?.cancel(true)
        }

        loading = true

        val mandelbrot = MandelbrotBitmapCreator(w, h)
        completableFuture = mandelbrot.createBitmapFuture()

        completableFuture?.exceptionally {
            bitmap = null
            loading = false
            null
        }

        completableFuture?.thenAccept {
            bitmap = it
            loading = false
        }

    }
}
