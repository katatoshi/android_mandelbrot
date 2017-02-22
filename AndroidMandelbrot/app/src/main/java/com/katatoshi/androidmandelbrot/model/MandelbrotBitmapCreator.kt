package com.katatoshi.androidmandelbrot.model

import android.graphics.Bitmap
import android.graphics.Color
import java8.util.concurrent.CompletableFuture
import java8.util.stream.IntStreams

/**
 * マンデルブロ集合 Bitmap を生成するクラス。
 */
class MandelbrotBitmapCreator(
        /** 幅のピクセル数。 */
        val wPixels: Int,

        /** 高さのピクセル数。 */
        val hPixels: Int,

        /** 描画領域の理論的な中心座標（複素数）。 */
        val center: Pair<Double, Double> = Pair(-0.5, 0.0),

        /** 描画領域の理論的な幅。 */
        val width: Double = 3.0,

        /** 繰り返し回数の上限。 */
        val maxIteration: Int = 100) {

    /** 描画領域の理論的な高さ。 */
    val height = (hPixels.toDouble() * width) / wPixels.toDouble()

    /** ピクセルのインデックスを理論的な座標に変換する際に使用する定数。 */
    private val const1 = center.first - width / 2.0

    /** ピクセルのインデックスを理論的な座標に変換する際に使用する定数。 */
    private val const2 = center.second + height / 2.0

    /**
     * 与えられた座標（複素数）をパラメータとする漸化式が初めて発散と判定された繰り返し回数を求めます。
     * @param c パラメータの座標（複素数）
     * @return 漸化式が初めて発散と判定された繰り返し回数
     */
    private fun divergentIteration(c: Pair<Double, Double>): Int? {
        var z = Pair(0.0, 0.0)
        for (k in 1..maxIteration) {
            z = z * z + c

            if (2 < z.abs()) {
                return k
            }
        }

        return null
    }

    /**
     * 与えられたピクセルをパラメータとする漸化式が初めて発散と判定された繰り返し回数を求めます。
     * @param xPixel ピクセルの x インデックス
     * @param yPixel ピクセルの y インデックス
     * @return 漸化式が初めて発散と判定された繰り返し回数
     */
    private fun divergentIteration(xPixel: Double, yPixel: Double): Int? {
        val c = Pair(const1 + (xPixel * width) / wPixels, const2 - (yPixel * height) / hPixels)
        return divergentIteration(c)
    }

    /**
     * 与えられたピクセルの色を求めます。
     * @param xPixel ピクセルの x インデックス
     * @param yPixel ピクセルの y インデックス
     * @return ピクセルの色
     */
    private fun pixelColor(xPixel: Double, yPixel: Double): Int {
        return divergentIteration(xPixel, yPixel)?.let { Color.BLUE } ?: Color.BLACK
    }

    /**
     * 与えられたピクセルの色を求めます。
     * @param xPixel ピクセルの x インデックス（整数）
     * @param yPixel ピクセルの y インデックス（整数）
     * @return ピクセルの色
     */
    private fun pixelColor(xPixel: Int, yPixel: Int): Int {
        return pixelColor(xPixel.toDouble(), yPixel.toDouble())
    }

    /**
     * マンデルブロ集合 Bitmap を生成します。
     * @return マンデルブロ集合 Bitmap
     */
    fun createBitmap(): Bitmap {
        return measure {
            val colors = IntArray(wPixels * hPixels)
            IntStreams.range(0, hPixels).parallel().forEach { y ->
                IntStreams.range(0, wPixels).parallel().forEach { x ->
                    colors[y * wPixels + x] = pixelColor(x, y)
                }
            }
            Bitmap.createBitmap(colors, wPixels, hPixels, Bitmap.Config.ARGB_8888)
        }
    }

    /**
     * マンデルブロ集合 Bitmap を生成する Promise を生成します。
     * @return Bitmap を生成する Promise
     */
    fun createBitmapFuture(): CompletableFuture<Bitmap?> {
        return CompletableFuture.supplyAsync { createBitmap() }
    }

    /**
     * マンデルブロ集合 Bitmap を生成します（parallel 版との比較用）。
     * @return マンデルブロ集合 Bitmap
     */
    @Deprecated("各ピクセルの色の計算を直列に行うのでパフォーマンス上問題があります。createBitmap を使用してください。")
    fun createBitmapSerial(): Bitmap {
        return measure {

            val colors = IntArray(wPixels * hPixels)
            (0..hPixels - 1).forEach { y ->
                (0..wPixels - 1).forEach { x ->
                    colors[y * wPixels + x] = pixelColor(x, y)
                }
            }
            Bitmap.createBitmap(colors, wPixels, hPixels, Bitmap.Config.ARGB_8888)
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
