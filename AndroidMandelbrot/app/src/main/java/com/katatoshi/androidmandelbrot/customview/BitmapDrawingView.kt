package com.katatoshi.androidmandelbrot.customview

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Bitmap 描画用 View
 */
class BitmapDrawingView : View {

    var bitmap: Bitmap? = null

    var onSizeChanged: ((Int, Int) -> Unit)? = null

    private val paint = Paint()

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

        onSizeChanged?.invoke(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let { canvas.drawBitmap(it, 0f, 0f, paint) }
    }
}

@BindingAdapter("bitmap")
fun BitmapDrawingView.setBitmap(bitmap: Bitmap?) {
    bitmap?.let {
        this.bitmap = it
        this.invalidate()
    }
}

@BindingAdapter("onSizeChanged")
fun BitmapDrawingView.setOnSizeChanged(onSizeChanged: ((Int, Int) -> Unit)?) {
    onSizeChanged?.let { this.onSizeChanged = it }
}
