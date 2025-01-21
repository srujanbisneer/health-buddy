package com.healthbuddy.model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.Path
import com.healthbuddy.R

class CurvedBackgroundDrawable: Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#F49811") // Example color
        style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        val width = bounds.width().toFloat()
        val height = bounds.height().toFloat()

        val path = Path()


        path.moveTo(0f, 0f)
        path.quadTo(width / 2, height /2, width, 0f)


        path.moveTo(0f, height)
        path.quadTo(width / 2, height * 2/3, width, height)

        path.close()


        canvas.drawPath(path, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return android.graphics.PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) {
        paint.colorFilter = colorFilter
    }
}