package com.katatoshi.androidmandelbrot.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import org.jdeferred.Promise
import org.jdeferred.android.AndroidDeferredManager
import java.util.*

/**
 * Bitmap と Canvas のお試し View
 */
class SampleView : View {

    private val paint = Paint()

    private var bitmap: Bitmap? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        SampleBitmap.createSampleBitmapPromise(w, h).done {
            bitmap = it
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, paint)
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
        val callable = { createSampleBitmap(w, h) }
        return AndroidDeferredManager().`when`(callable)
    }
}
