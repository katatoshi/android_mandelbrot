package com.katatoshi.androidmandelbrot.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

/**
 * Bitmap と Canvas のお試し View
 */
class SampleView : View {

    private var w = 0;

    private var h = 0;

    private val paint = Paint()

    private val random = Random()

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        w = widthSize

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        h = heightSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(createSampleBitmap(w, h), 0f, 0f, paint)
    }

    private fun createSampleBitmap(w: Int, h: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

        for (x in 0..(w - 1)) {
            for (y in 0..(h - 1)) {
                bitmap.setPixel(x, y, Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)))
            }
        }

        return bitmap
    }
}
