package com.example.myapplication.ui.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.text.DateFormatSymbols
import java.util.*

class CustomCalendarView : View {

    private val daysPaint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
    }

    private val backgroundPaint = Paint().apply {
        color = Color.WHITE
    }

    private val todayBackgroundPaint = Paint().apply {
        color = Color.YELLOW
    }

    private var todayDate: Int = -1
    private var currentDate: Int = -1

    private val calendar: Calendar = Calendar.getInstance()

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val widthUnit = width / 7f
        val heightUnit = height / 6f

        // Draw background
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

        val monthText = DateFormatSymbols().months[calendar.get(Calendar.MONTH)]
        val monthTextWidth = daysPaint.measureText(monthText)
        val monthTextHeight = daysPaint.fontMetrics.descent - daysPaint.fontMetrics.ascent
        canvas.drawText(monthText, (width - monthTextWidth) / 2, monthTextHeight, daysPaint)

        // Draw days
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        var currentDay = 1
        var row = 1

        while (currentDay <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            for (col in 1..7) {
                if ((row == 1 && col < firstDayOfWeek) || currentDay > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    break
                }

                val textWidth = daysPaint.measureText(currentDay.toString())
                val textHeight = daysPaint.fontMetrics.descent - daysPaint.fontMetrics.ascent

                if (currentDay == todayDate) {
                    canvas.drawRect(
                        (col - 1) * widthUnit,
                        (row - 1) * heightUnit,
                        col * widthUnit,
                        row * heightUnit,
                        todayBackgroundPaint
                    )
                }

                canvas.drawText(
                    currentDay.toString(),
                    (col - 1) * widthUnit + widthUnit / 2 - textWidth / 2,
                    (row - 1) * heightUnit + heightUnit / 2 + textHeight / 2,
                    daysPaint
                )

                currentDay++
            }
            row++
        }
    }


    fun setDate(date: Date) {
        calendar.time = date
        currentDate = calendar.get(Calendar.DAY_OF_MONTH)
        val todayCalendar = Calendar.getInstance()
        if (calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH) && calendar.get(
                Calendar.YEAR
            ) == todayCalendar.get(Calendar.YEAR)
        ) {
            todayDate = todayCalendar.get(Calendar.DAY_OF_MONTH)
        }

        invalidate()
    }
}
