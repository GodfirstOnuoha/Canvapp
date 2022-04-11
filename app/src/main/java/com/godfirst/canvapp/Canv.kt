package com.godfirst.canvapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.res.ResourcesCompat

class Canv(context: Context) : View(context) {
    private lateinit var firstFrame: Rect
    private lateinit var secondFrame: Rect
    private lateinit var newCanvas: Canvas
    private lateinit var newBitmap: Bitmap
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

}