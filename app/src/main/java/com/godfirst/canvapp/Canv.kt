package com.godfirst.canvapp

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import kotlin.math.abs

class Canv(context: Context) : View(context) {
    private lateinit var firstFrame: Rect
    private lateinit var secondFrame: Rect
    private lateinit var newCanvas: Canvas
    private lateinit var newBitmap: Bitmap

    private var currentX = 0f
    private var currentY = 0f
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private var path = Path()
    private val drawing  = Path()
    private val currentPath = Path()

    private val paint = Paint().apply {
        color = ResourcesCompat.getColor(resources, R.color.black, null)
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 12f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (::newBitmap.isInitialized) newBitmap.recycle()
        newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        newCanvas = Canvas(newBitmap)
        newCanvas.drawColor(ResourcesCompat.getColor(resources, R.color.white, null))

        val firstInset = 50
        val secondInset = 100
        firstFrame = Rect(firstInset, firstInset, w - firstInset, h - firstInset)
        secondFrame = Rect(secondInset, secondInset, w - secondInset, h - secondInset)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(newBitmap, 0f, 0f, null)
        canvas?.drawRect(firstFrame, paint)
        canvas?.drawRect(secondFrame, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            motionTouchEventX = event.x
            motionTouchEventY = event.y
        }


        when(event?.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }

        return true
    }

    private fun touchUp() {
        drawing.addPath(currentPath)
        currentPath.reset()
    }

    private fun touchMove() {
        val dx = abs(motionTouchEventX - currentX)
        val dy = abs(motionTouchEventY - currentY)

        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY

            newCanvas.drawPath(path, paint)
        }

        invalidate()
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX,motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

}