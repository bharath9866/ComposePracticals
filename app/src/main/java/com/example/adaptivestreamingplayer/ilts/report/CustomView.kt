package com.example.adaptivestreamingplayer.ilts.report

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import com.example.adaptivestreamingplayer.R

class CustomView(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private val outerArc = Paint()
    private val innerArc = Paint()
    private val progressRectF = RectF()

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    private val DEFAULT_PROGRESSBAR_WIDTH = 39f
    private val DEFAULT_isRoundCorner = false
    private val DEFAULT_CLOCKWISE = true
    private var startAngle = 180f
    private var swipeAngle = 180f

    @ColorInt
    private var DEFAULT_FOREGROUND_PROGRESS_COLOR = Color.parseColor("#FBD93E")

    @ColorInt
    private var DEFAULT_BACKGROUND_PROGRESS_COLOR = Color.parseColor("#EFF0F6")

    // -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    private var foregroundProgressbarWidth = DEFAULT_PROGRESSBAR_WIDTH
    private var backgroundProgressbarWidth = DEFAULT_PROGRESSBAR_WIDTH
    @ColorInt
    private var foregroundProgressColor = DEFAULT_FOREGROUND_PROGRESS_COLOR

    @ColorInt
    private var backgroundProgressColor = DEFAULT_BACKGROUND_PROGRESS_COLOR

    private var DEFAULT_PROGRESS = 0f
    private var DEFAULT_MAXSCALE = 100f

    private var progressMaxScale = DEFAULT_MAXSCALE

    private var progress = DEFAULT_PROGRESS
    private val textPaint = Paint()
    private var width = 300f;
    private var height = 300f;

    private var isClockwise = DEFAULT_CLOCKWISE

    private var isRoundCorner = DEFAULT_isRoundCorner

    init {
        var typedArray = context.obtainStyledAttributes(attributes, R.styleable.ScoreProgress)
        initTypeArray(typedArray)
        progressValidation(progress)
        init()
    }


    constructor(context: Context, attributes: AttributeSet, defStyle: Int) : this(
        context,
        attributes
    ) {
        var typedArray = context.obtainStyledAttributes(attributes, R.styleable.ScoreProgress, defStyle, 0)
        initTypeArray(typedArray)
        init()
        progressValidation(progress)
    }


    private fun initTypeArray(typedArray: TypedArray) {

        foregroundProgressbarWidth = typedArray.getFloat(R.styleable.ScoreProgress_progressbar_width, DEFAULT_PROGRESSBAR_WIDTH);
        backgroundProgressbarWidth = typedArray.getFloat(R.styleable.ScoreProgress_progressbar_width, DEFAULT_PROGRESSBAR_WIDTH);
        foregroundProgressColor = typedArray.getColor(R.styleable.ScoreProgress_progress_color, DEFAULT_FOREGROUND_PROGRESS_COLOR)
        backgroundProgressColor = typedArray.getColor(R.styleable.ScoreProgress_progress_background_color, DEFAULT_BACKGROUND_PROGRESS_COLOR)
        progress = typedArray.getFloat(R.styleable.ScoreProgress_score, DEFAULT_PROGRESS)
        isRoundCorner = typedArray.getBoolean(R.styleable.ScoreProgress_progress_roundedCorner, DEFAULT_isRoundCorner)

        isClockwise = typedArray.getBoolean(R.styleable.ScoreProgress_progress_isClockwise, DEFAULT_CLOCKWISE)

        progressMaxScale = typedArray.getFloat(R.styleable.ScoreProgress_progress_maxscale, DEFAULT_MAXSCALE)

        typedArray.recycle()
    }

    fun init() {
        outerArc.strokeWidth = foregroundProgressbarWidth
        outerArc.style = Paint.Style.STROKE
        innerArc.color = backgroundProgressColor
        outerArc.color = foregroundProgressColor
        innerArc.strokeWidth = backgroundProgressbarWidth
        innerArc.style = Paint.Style.STROKE
        textPaint.color = Color.BLACK
        textPaint.strokeWidth = 5f
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 40f
        setRoundedCorner(isRoundCorner)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        progressValidation(progress)
        canvas.drawArc(progressRectF, startAngle, swipeAngle, false, innerArc)
        canvas.drawArc(progressRectF, startAngle, progress, false, outerArc)
        invalidate()
    }

    fun progressValidation(progress: Float) {
        Log.e("Progress",this.progress.toString()+"  "+progress)
        if (progress >= progressMaxScale) {
            this.progress = progressMaxScale

        } else {
            this.progress = getProgress(progress)
        }
        invalidate()
    }

    fun getProgress(progress: Float): Float {
        val dividedValue = (progress / progressMaxScale)
        val percentage = (progress / progressMaxScale) * 100
        val progressValue = (progress / progressMaxScale) * 180
        return progressValue
    }


    fun setRoundedCorner(isRoundCorner: Boolean) {
        this.isRoundCorner = isRoundCorner
        if (isRoundCorner) {
            outerArc.strokeCap = Paint.Cap.ROUND
            innerArc.strokeCap = Paint.Cap.ROUND
        } else {
            outerArc.strokeCap = Paint.Cap.SQUARE
            innerArc.strokeCap = Paint.Cap.SQUARE
        }
    }
}