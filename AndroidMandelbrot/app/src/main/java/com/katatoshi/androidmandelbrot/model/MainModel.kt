package com.katatoshi.androidmandelbrot.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.Bitmap
import android.graphics.Color

import com.katatoshi.androidmandelbrot.BR
import com.katatoshi.androidmandelbrot.extension.JDeferredExtensions.of
import org.jdeferred.Promise
import org.jdeferred.android.AndroidDeferredManager
import java.util.*

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

    fun createSampleBitmap(w: Int, h: Int) {
        SampleBitmap.createSampleBitmapPromise(w, h).done {
            sampleBitmap = it
        }
    }
}


/**
 * お試し Bitmap
 */
object SampleBitmap {

    private val random = Random()

    fun createSampleBitmap(w: Int, h: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

        (0..w - 1).forEach { x ->
            (0..h - 1).forEach { y ->
                bitmap.setPixel(x, y, Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)))
            }
        }

        return bitmap
    }

    fun createSampleBitmapPromise(w: Int, h: Int): Promise<Bitmap, Throwable, Void> {
        return AndroidDeferredManager().of { createSampleBitmap(w, h) }
    }
}
