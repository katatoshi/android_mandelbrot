package com.katatoshi.androidmandelbrot.model

import android.graphics.Bitmap
import android.graphics.Color
import org.jdeferred.Promise
import org.jdeferred.android.AndroidDeferredManager
import com.katatoshi.androidmandelbrot.extension.JDeferredExtensions.of
import java.util.*

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
