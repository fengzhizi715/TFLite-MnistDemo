package com.tflite.demo.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import java.util.*

/**
 *
 * @FileName:
 *          com.tflite.demo.ui.PaintView
 * @author: Tony Shen
 * @date: 2018-11-07 16:30
 * @version: V1.0 <描述当前版本功能>
 */
class PaintView constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()
    private lateinit var mPath: Path
    private val mPaint: Paint
    private val paths = ArrayList<FingerPath>()
    private var currentColor: Int = 0
    private var bgColor = DEFAULT_BG_COLOR
    private var strokeWidth: Int = 0

    lateinit var bitmap: Bitmap
        private set

    private lateinit var mCanvas: Canvas
    private val mBitmapPaint = Paint(Paint.DITHER_FLAG)

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = DEFAULT_COLOR
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.xfermode = null
        mPaint.alpha = 0xff
    }

    fun init(metrics: DisplayMetrics) {
        val height = metrics.widthPixels
        val width = metrics.widthPixels

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(bitmap)

        currentColor = DEFAULT_COLOR
        strokeWidth = DEFAULT_SIZE
    }

    fun clear() {
        bgColor = DEFAULT_BG_COLOR
        paths.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {

        canvas.save()
        mCanvas.drawColor(bgColor)

        for ((color,strokeWidth, path) in paths) {
            mPaint.color = color
            mPaint.strokeWidth = strokeWidth.toFloat()
            mCanvas.drawPath(path, mPaint)
        }

        canvas.drawBitmap(bitmap, 0f, 0f, mBitmapPaint)
        canvas.restore()
    }

    private fun touchStart(x: Float, y: Float) {

        mPath = Path()
        val fp = FingerPath(currentColor,strokeWidth, mPath)
        paths.add(fp)

        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {

        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() = mPath.lineTo(mX, mY)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }

        return true
    }

    companion object {

        var DEFAULT_SIZE = 100
        val DEFAULT_COLOR = Color.BLACK
        val DEFAULT_BG_COLOR = Color.WHITE
        private val TOUCH_TOLERANCE = 4f
    }

    data class FingerPath(var color: Int, var strokeWidth: Int, var path: Path)
}