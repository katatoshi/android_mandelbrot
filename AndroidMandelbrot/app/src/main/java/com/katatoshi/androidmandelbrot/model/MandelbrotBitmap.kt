package com.katatoshi.androidmandelbrot.model

import android.graphics.Bitmap
import android.graphics.Color
import android.support.v4.graphics.ColorUtils
import java8.util.concurrent.CompletableFuture
import java.util.*

/**
 * マンデルブロ集合の Bitmap
 */
object MandelbrotBitmap {

    private val maxIndex = 200

    private val width = 3.0

    private var height = 0.0

    private val center = Pair(-0.5, 0.0)

    val color1 = Color.WHITE

    val color2 = Color.argb(255, 0x4a, 0x14, 0x8c)

    val hsl1 = FloatArray(3)

    val hsl2 = FloatArray(3)

    init {
        ColorUtils.colorToHSL(color1, hsl1)
        ColorUtils.colorToHSL(color2, hsl2)
    }

    private fun divergenceIndex(c: Pair<Double, Double>): Int? {
        var z = Pair(0.0, 0.0)
        for (k in 1..maxIndex) {
            z = z * z + c

            if (2 < z.abs()) {
                return k
            }
        }

        return null
    }

    private fun divergenceIndex(w: Int, h: Int, x: Int, y: Int): Int? {
        val c = Pair(center.first - width / 2.0 + (x.toDouble() * width) / w.toDouble(), center.second - height / 2.0 + (height - (y.toDouble() * height) / h.toDouble()))
        return divergenceIndex(c)
    }

    private fun pixelColor1(w: Int, h: Int, x: Int, y: Int): Int {
        return divergenceIndex(w, h, x, y)?.let { Color.GREEN } ?: Color.BLACK
    }

    private fun pixelColor2(w: Int, h: Int, x: Int, y: Int): Int {
        return divergenceIndex(w, h, x, y)?.let {
            val ratio = Math.max(Math.log(it.toDouble()), 1.0)
            val hsl = FloatArray(3)
            ColorUtils.blendHSL(hsl1, hsl2, ratio.toFloat(), hsl)
            ColorUtils.HSLToColor(hsl)
        } ?: Color.WHITE
    }

    fun createBitmap(w: Int, h: Int): Bitmap {
        height = (h.toDouble() * width) / w.toDouble()

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

        (0..w - 1).forEach { x ->
            (0..h - 1).forEach { y ->
                bitmap.setPixel(x, y, pixelColor2(w, h, x, y))
            }
        }

        return bitmap
    }

    fun createBitmapFuture(w: Int, h: Int): CompletableFuture<Bitmap?> {
        return CompletableFuture.supplyAsync { createBitmap(w, h) }
    }

    private fun Pair<Double, Double>.abs(): Double {
        return Math.sqrt(this.first * this.first + this.second * this.second)
    }

    private operator fun Pair<Double, Double>.plus(that: Pair<Double, Double>): Pair<Double, Double> {
        return Pair(this.first + that.first, this.second + that.second)
    }

    private operator fun Pair<Double, Double>.times(that: Pair<Double, Double>): Pair<Double, Double> {
        return Pair(this.first * that.first - this.second * that.second, this.first * that.second + this.second * that.first)
    }
}
