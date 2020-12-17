package com.iua.agustinpereyra.controller

import android.graphics.Rect
import android.graphics.RectF

class CoordinatesTranslator{

    companion object {
        private var _scaleY: Float = 1.0.toFloat()
        private var _scaleX: Float = 1.0.toFloat()

        private fun translateX(x: Float): Float = x * _scaleX
        private fun translateY(y: Float): Float = y * _scaleY

        fun setScaleY(overlayHeight: Float, imageHeight: Float) {
            _scaleY = overlayHeight / imageHeight
        }

        fun setScaleX(overlayWidth: Float, imageWidth: Float) {
            _scaleY = overlayWidth / imageWidth
        }

        fun translateRect(rect: Rect) = RectF(
            translateX(rect.left.toFloat()),
            translateY(rect.top.toFloat()),
            translateX(rect.right.toFloat()),
            translateY(rect.bottom.toFloat())
        )
    }
}