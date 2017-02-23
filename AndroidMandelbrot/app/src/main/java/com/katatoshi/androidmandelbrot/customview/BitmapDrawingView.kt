package com.katatoshi.androidmandelbrot.customview

import android.content.Context
import android.databinding.BindingAdapter
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

/**
 * Bitmap 描画用 View
 *
 * フォーカスを意識したズームイン／アウトは次のページを参考にした：
 *
 * https://blog.keiji.io/2015/12/mincomi-adventcalendar-19.html
 */
class BitmapDrawingView : View {

    var bitmap: Bitmap? = null
        set(value) {
            field = value

            //region ScaleGestureDetector 関連の処理
            value?.let {
                srcRect = Rect(0, 0, it.width, it.height)
            } ?: {
                srcRect = null
                dstRect = null
            }()
            //endregion
        }

    var onSizeChanged: ((Int, Int) -> Unit)? = null

    private val paint = Paint()


    //region ScaleGestureDetector 関連のプロパティ、関数
    private var lastScaleFactor = 0.0f

    private var focusX = 0.0f

    private var focusY = 0.0f

    private var srcRect: Rect? = null

    private var dstRect: RectF? = null

    private fun setScale(scale: Float, focusX: Float, focusY: Float) {
        this.focusX = focusX
        this.focusY = focusY

        dstRect?.let {
            val newWidth = it.width() * scale
            val newHeight = it.height() * scale

            val scrollX = it.width() - newWidth
            val scrollY = it.height() - newHeight

            it.right = it.left + newWidth
            it.bottom = it.top + newHeight

            it.offset(scrollX * (focusX / width), scrollY * (focusY / height))

            invalidate()
        }
    }
    //endregion


    //region ScaleGestureDetector
    private val onScaleGestureListener = object : ScaleGestureDetector.OnScaleGestureListener {

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            return detector?.let {
                setScale(1f + it.scaleFactor - lastScaleFactor, it.focusX, it.focusY)
                true
            } ?: false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            return detector?.let {
                lastScaleFactor = it.scaleFactor
                true
            } ?: false
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }
    }

    private val scaleGestureDetector: ScaleGestureDetector by lazy {
        ScaleGestureDetector(context, onScaleGestureListener)
    }
    //endregion


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

        bitmap = null
        onSizeChanged?.invoke(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let {
            //region ScaleGestureDetector 関連の処理
            if (dstRect == null) {
                dstRect = it.rectF(canvas.width, canvas.height)
            }
            //endregion

            canvas.drawBitmap(it, srcRect, dstRect, paint)
        } ?: {
            canvas.drawColor(Color.WHITE)
        }()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (scaleGestureDetector.onTouchEvent(event)) {
            return true
        }

        return super.onTouchEvent(event)
    }


    //region Extensions
    private fun Bitmap.rectF(w: Int, h: Int): RectF {
        val bitmapWidth = this.width.toFloat()
        val bitmapHeight = this.height.toFloat()

        val ratio = Math.min(w.toFloat() / bitmapWidth, h.toFloat() / bitmapHeight)

        return RectF(0f, 0f, ratio * bitmapWidth, ratio * bitmapHeight)
    }
    //endregion
}


//region BindingAdapters
@BindingAdapter("bitmap")
fun BitmapDrawingView.setBitmap(bitmap: Bitmap?) {
    this.bitmap = bitmap
    this.invalidate()
}

@BindingAdapter("onSizeChanged")
fun BitmapDrawingView.setOnSizeChanged(onSizeChanged: ((Int, Int) -> Unit)?) {
    onSizeChanged?.let { this.onSizeChanged = it }
}
//endregion
