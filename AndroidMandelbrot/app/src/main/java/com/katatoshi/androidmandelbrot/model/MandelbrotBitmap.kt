package com.katatoshi.androidmandelbrot.model

import android.graphics.Bitmap
import android.graphics.Color
import java8.util.concurrent.CompletableFuture
import java8.util.stream.IntStreams

/**
 * マンデルブロ集合の Bitmap
 */
object MandelbrotBitmap {

    private val maxIndex = 200

    private val width = 3.0

    private var height = 0.0
        set(value) {
            field = value
            const1 = center.first - width / 2.0
            const2 = center.second + height / 2.0
        }

    private val center = Pair(-0.5, 0.0)

    private var const1 = 0.0

    private var const2 = 0.0

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

    private fun divergenceIndex(w: Double, h: Double, x: Double, y: Double): Int? {
        val c = Pair(const1 + (x * width) / w, const2 - (y * height) / h)
        return divergenceIndex(c)
    }

    private fun pixelColor(w: Int, h: Int, x: Int, y: Int): Int {
        return divergenceIndex(w.toDouble(), h.toDouble(), x.toDouble(), y.toDouble())?.let { Color.BLUE } ?: Color.BLACK
    }

    fun createBitmap(w: Int, h: Int): Bitmap {
        return measure {
            height = (h.toDouble() * width) / w.toDouble()

            val colors = IntArray(w * h)
            IntStreams.range(0, h).parallel().forEach { y ->
                IntStreams.range(0, w).parallel().forEach { x ->
                    colors[y * w + x] = pixelColor(w, h, x, y)
                }
            }
            Bitmap.createBitmap(colors, w, h, Bitmap.Config.ARGB_8888)
        }
    }

    fun createBitmapFuture(w: Int, h: Int): CompletableFuture<Bitmap?> {
        return CompletableFuture.supplyAsync { createBitmap(w, h) }
    }

    /** parallel 版との比較用。 */
    @Deprecated("各ピクセルの色の計算を直列に行うのでパフォーマンス上問題があります。createBitmap を使用してください。")
    fun createBitmapSerial(w: Int, h: Int): Bitmap {
        return measure {
            height = (h.toDouble() * width) / w.toDouble()

            val colors = IntArray(w * h)
            (0..w - 1).forEach { y ->
                (0..h - 1).forEach { x ->
                    colors[y * w + x] = pixelColor(w, h, x, y)
                }
            }
            Bitmap.createBitmap(colors, w, h, Bitmap.Config.ARGB_8888)
        }
    }

    /**
     * 処理時間計測関数
     */
    private fun <T> measure(func: () -> T): T {
        val t = System.currentTimeMillis()
        val result = func()
        println("measure: ${System.currentTimeMillis() - t}ms")
        return result
    }


    //region Extensions
    private fun Pair<Double, Double>.abs(): Double {
        return Math.sqrt(this.first * this.first + this.second * this.second)
    }

    private operator fun Pair<Double, Double>.plus(that: Pair<Double, Double>): Pair<Double, Double> {
        return Pair(this.first + that.first, this.second + that.second)
    }

    private operator fun Pair<Double, Double>.times(that: Pair<Double, Double>): Pair<Double, Double> {
        return Pair(this.first * that.first - this.second * that.second, this.first * that.second + this.second * that.first)
    }
    //endregion
}
