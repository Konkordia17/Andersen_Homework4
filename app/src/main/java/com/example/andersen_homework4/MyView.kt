package com.example.andersen_homework4

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.properties.Delegates

class MyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var secondLineColor by Delegates.notNull<Int>()
    private var minuteLineColor by Delegates.notNull<Int>()
    private var hourLineColor by Delegates.notNull<Int>()
    private var secondLineWidth: Float = 0.0f
    private var minuteLineWidth: Float = 0.0f
    private var hourLineWidth: Float = 0.0f

    init {
        setupAttributes(attrs)
    }

    private val paint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }
    private val paintCenter = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = 10f
        style = Paint.Style.FILL_AND_STROKE
    }
    private val paintHours = Paint().apply {
        color = hourLineColor
        isAntiAlias = true
        strokeWidth = hourLineWidth
        style = Paint.Style.STROKE
    }
    private val paintMinutes = Paint().apply {
        color = minuteLineColor
        isAntiAlias = true
        strokeWidth = minuteLineWidth
        style = Paint.Style.STROKE
    }
    private val paintSeconds = Paint().apply {
        color = secondLineColor
        strokeWidth = secondLineWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //циферблат
        canvas.drawCircle(width / 2f, height / 2f, width / 2.5f, paint)
        //центр круга
        canvas.drawCircle(width / 2f, height / 2f, 4f, paintCenter)
        //часовая шкала
        for (i in 1..12) {
            canvas.save()
            canvas.rotate(
                360 / 12f * (i + 1),
                width / 2f,
                height / 2f
            )
            canvas.drawLine(
                width / 2f,
                height / 2f - width / 3f,
                width / 2f,
                height / 2f - width / 2.5f,
                paint
            )
            canvas.restore()
        }
        //часовая стрелка
        canvas.save()
        val calendar = Calendar.getInstance()
        var hours = calendar.get(Calendar.HOUR)
        hours = if (hours > 12) {
            hours - 12
        } else {
            hours
        }
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)

        canvas.rotate(360 / 12 * hours + minutes * 0.5f, width / 2f, height / 2f)
        canvas.drawLine(width / 2f, height / 2f, width / 2f, height / 2.6f, paintHours)
        canvas.restore()
        //минутная стрелка
        canvas.save()
        canvas.rotate(360 / 60 * minutes + seconds * 0.1f, width / 2f, height / 2f)
        canvas.drawLine(width / 2f, height / 2f, width / 2f, height / 3.1f, paintMinutes)
        canvas.restore()
        //секундная стрелка
        canvas.save()
        canvas.rotate(360 / 60f * seconds, width / 2f, height / 2f)
        canvas.drawLine(width / 2f, height / 2f, width / 2f, height / 3.4f, paintSeconds)
        canvas.restore()
        postInvalidateDelayed(1000)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.MyView,
            0, 0
        )
        secondLineColor = typedArray.getColor(R.styleable.MyView_secondLineColor, Color.GREEN)
        minuteLineColor = typedArray.getColor(R.styleable.MyView_minuteLineColor, Color.BLUE)
        hourLineColor = typedArray.getColor(R.styleable.MyView_hourLineColor, Color.BLUE)
        secondLineWidth = typedArray.getFloat(R.styleable.MyView_secondLineWidth, 3.1f)
        minuteLineWidth = typedArray.getFloat(R.styleable.MyView_minuteLineWidth, 8.1f)
        hourLineWidth = typedArray.getFloat(R.styleable.MyView_hourLineWidth, 12.1f)
        typedArray.recycle()
    }
}






