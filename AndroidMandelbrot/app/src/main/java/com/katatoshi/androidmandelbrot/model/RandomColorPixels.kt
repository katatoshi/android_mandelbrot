package com.katatoshi.androidmandelbrot.model

import android.graphics.Bitmap
import android.graphics.Color
import java8.util.concurrent.CompletableFuture
import java.util.*

/**
 * ランダムな色のピクセルからなる Bitmap
 */
object RandomColorPixels {

    private val random = Random()

    fun createBitmap(w: Int, h: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

        (0..w - 1).forEach { x ->
            (0..h - 1).forEach { y ->
                bitmap.setPixel(x, y, Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)))
            }
        }

        return bitmap
    }

    fun createBitmapFuture(w: Int, h: Int): CompletableFuture<Bitmap?> {
        return CompletableFuture.supplyAsync { createBitmap(w, h) }
    }
}
