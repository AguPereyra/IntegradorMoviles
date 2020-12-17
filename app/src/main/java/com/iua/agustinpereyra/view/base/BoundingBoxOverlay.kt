package com.iua.agustinpereyra.view.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.iua.agustinpereyra.R

class BoundingBoxOverlay(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val cornerRadius = context.resources.getDimension(R.dimen.bounding_box_corner_radius)
    private val borderPaint = Paint().apply {
        strokeWidth = context.resources.getDimension(R.dimen.bounding_box_stroke_width)
        style = Paint.Style.STROKE
        isAntiAlias = true
    }
    private var boundingBox: RectF = RectF()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BoundingBoxOverlay,
            0, 0).apply {
            try {
                boundingBox.left = getFloat(R.styleable.BoundingBoxOverlay_leftPos, 0.toFloat())
                boundingBox.right = getFloat(R.styleable.BoundingBoxOverlay_rightPos, 0.toFloat())
                boundingBox.top = getFloat(R.styleable.BoundingBoxOverlay_topPos, 0.toFloat())
                boundingBox.bottom = getFloat(R.styleable.BoundingBoxOverlay_bottomPos, 0.toFloat())
            } finally {
                recycle()
            }
        }
    }

    fun setBoundingBox(bounds: RectF) {
        boundingBox.left = bounds.left
        boundingBox.right = bounds.right
        boundingBox.top = bounds.top
        boundingBox.bottom = bounds.bottom

        // Necessary for update of view
        invalidate()
        requestLayout()
    }

    fun clearBoundingBox() {
        boundingBox.left = 0.toFloat()
        boundingBox.right = 0.toFloat()
        boundingBox.top = 0.toFloat()
        boundingBox.bottom = 0.toFloat()

        // Necessary for update of view
        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        // Draw bounding box
        canvas!!.drawRoundRect(boundingBox, cornerRadius, cornerRadius, borderPaint)
    }

}