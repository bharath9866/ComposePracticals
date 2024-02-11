package com.example.adaptivestreamingplayer.ilts.report

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import com.example.adaptivestreamingplayer.R
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class CustomViewTwo(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private val foreGroundPaint = Paint()
    private val backGroundPaint = Paint()
    private val emptyPaint = Paint()

    private val mainRectF = RectF()
    private val childRectF = RectF()
    private val progressRectF = RectF()
    private val scoreRectF = RectF()
    private val scoreTotalRectF = RectF()

    val zeroPercentText = "0%"
    val hundredPercentText = "100%"
    val scoreText = "Score"

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

    private var DEFAULT_PROGRESS = 400f
    private var DEFAULT_PERCENTAGE = 0F
    private var DEFAULT_MAXSCALE = 800f

    private var totalScore = DEFAULT_MAXSCALE

    private var scoreGradient: SweepGradient? = null

    private var score = DEFAULT_PROGRESS
    private var percentage = DEFAULT_PERCENTAGE

    private val scoreTextPaint = TextPaint()
    private val percentagePaint = TextPaint()
    private val currentScorePaint = TextPaint()
    private val totalScorePaint = TextPaint()

    var mont_bold: Typeface? = null
    var mont_medium: Typeface? = null
    var mont_semibold: Typeface? = null
    var mont_regular: Typeface? = null

    private var width = 300f;
    private var height = 300f;

    private var isClockwise = DEFAULT_CLOCKWISE

    private var isRoundCorner = DEFAULT_isRoundCorner

    init {
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.ScoreProgress)
        // typeface = Typeface.createFromAsset(context.assets, "font/montserrat_semibold.ttf")
        initTypeArray(typedArray)
        progressValidation(score)
        init()
    }


    constructor(context: Context, attributes: AttributeSet, defStyle: Int) : this(
        context,
        attributes
    ) {
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.ScoreProgress, defStyle, 0)
        // typeface = Typeface.createFromFile("font/montserrat_semibold.ttf")
        initTypeArray(typedArray)
        init()
        progressValidation(score)
    }


    private fun initTypeArray(typedArray: TypedArray) {
        // typeface = Typeface.createFromFile("font/montserrat_semibold.ttf")
        foregroundProgressbarWidth = typedArray.getFloat(R.styleable.ScoreProgress_progressbar_width, DEFAULT_PROGRESSBAR_WIDTH);
        backgroundProgressbarWidth = typedArray.getFloat(R.styleable.ScoreProgress_progressbar_width, DEFAULT_PROGRESSBAR_WIDTH);
        foregroundProgressColor = typedArray.getColor(R.styleable.ScoreProgress_progress_color, DEFAULT_FOREGROUND_PROGRESS_COLOR)
        backgroundProgressColor = typedArray.getColor(R.styleable.ScoreProgress_progress_background_color, DEFAULT_BACKGROUND_PROGRESS_COLOR)
        score = typedArray.getFloat(R.styleable.ScoreProgress_score, DEFAULT_PROGRESS)
        isRoundCorner = typedArray.getBoolean(R.styleable.ScoreProgress_progress_roundedCorner, DEFAULT_isRoundCorner)
        isClockwise = typedArray.getBoolean(R.styleable.ScoreProgress_progress_isClockwise, DEFAULT_CLOCKWISE)

        totalScore = typedArray.getFloat(R.styleable.ScoreProgress_progress_maxscale, DEFAULT_MAXSCALE)

        typedArray.recycle()
    }

    fun init() {
        mont_semibold = Typeface.createFromAsset(context.assets, "fonts/montserrat_semibold.ttf")
        mont_bold = Typeface.createFromAsset(context.assets, "fonts/montserrat_bold.ttf")
        mont_regular = Typeface.createFromAsset(context.assets, "fonts/montserrat_regular.ttf")
        mont_medium = Typeface.createFromAsset(context.assets, "fonts/montserrat_medium.ttf")

        emptyPaint.color = Color.TRANSPARENT

        foreGroundPaint.strokeWidth = foregroundProgressbarWidth
        foreGroundPaint.isAntiAlias = true
        foreGroundPaint.style = Paint.Style.STROKE
        foreGroundPaint.color = foregroundProgressColor

        backGroundPaint.color = backgroundProgressColor
        backGroundPaint.strokeWidth = backgroundProgressbarWidth
        backGroundPaint.style = Paint.Style.STROKE
        backGroundPaint.isAntiAlias = true

        scoreTextPaint.apply {
            isAntiAlias = true
            color = Color.parseColor("#6E7191")
            textSize = resources.getDimension(R.dimen.text_18sp)
            typeface = mont_medium
        }
        currentScorePaint.apply {
            isAntiAlias = true
            color = Color.parseColor("#4E4B66")
            textSize = resources.getDimension(R.dimen.text_20sp)
            typeface = mont_bold
        }
        totalScorePaint.apply {
            isAntiAlias = true
            color = Color.parseColor("#6E7191")
            textSize = resources.getDimension(R.dimen.text_19_54sp)
            typeface = mont_medium
        }
        percentagePaint.apply {
            isAntiAlias = true
            color = Color.parseColor("#4E4B66")
            textSize = resources.getDimension(R.dimen.text_12sp)
            typeface = mont_semibold
        }

        foreGroundPaint.strokeCap = Paint.Cap.ROUND
        backGroundPaint.strokeCap = Paint.Cap.ROUND
        // setRoundedCorner(isRoundCorner)
        scoreGradient = SweepGradient(
            width/2,
            height/2,
            intArrayOf(
                Color.parseColor("#FBECAB"),
                Color.parseColor("#FBECAB"),
                Color.parseColor("#FBECAB"),
                Color.parseColor("#FFEB93"),
                Color.parseColor("#FFEB93"),
                Color.parseColor("#FFE262"),
                Color.parseColor("#FFE262"),
                Color.parseColor("#FFD82A"),
                Color.parseColor("#FFD82A"),
                Color.parseColor("#FFD000"),
                Color.parseColor("#FFD000"),
                Color.parseColor("#FFD600"),
                Color.parseColor("#FFD600"),
                Color.parseColor("#FFD600"),
            ),
            null
        )

    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (MeasureSpec.getMode(widthMeasureSpec) > 0 && MeasureSpec.getMode(heightMeasureSpec) > 0) {
            width = MeasureSpec.getSize(widthMeasureSpec).toFloat()
//        Log.e("Width ",MeasureSpec.getMode(widthMeasureSpec).toString()+"height "+)
            height = MeasureSpec.getSize(heightMeasureSpec).toFloat()
        } else {
            width = 400f
            height = 400f
        }
        val maxStockWidth = foreGroundPaint.strokeWidth.coerceAtLeast(backGroundPaint.strokeWidth)
        mainRectF.set(
            0f + maxStockWidth / 2f,
            (0f + maxStockWidth / 2f),
            width - maxStockWidth / 2f,
            height
        )

        childRectF.set(
            0f + maxStockWidth / 2f,
            0f + maxStockWidth / 2f,
            width - maxStockWidth / 2f,
            height
        )

        setMeasuredDimension((mainRectF.left+mainRectF.right).roundToInt(), height.toInt() / 2 + (3 * maxStockWidth / 4).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        scoreGradient?.apply {
            val matrix = getGradientMatrix()
            matrix.preRotate(270f, score, height/2)
            setLocalMatrix(matrix)
        }

        canvas.drawRect(mainRectF, emptyPaint)

        foreGroundPaint.shader = scoreGradient

        canvas.drawArc(childRectF, startAngle, swipeAngle, false, backGroundPaint)
        canvas.drawArc(childRectF, startAngle, score, false, foreGroundPaint)

        val textOnEdgeX = width / 2 + (width / 2) * cos(Math.toRadians(swipeAngle + score.toDouble())).toFloat()
        val textOnEdgeY = height / 2 + (height / 2) * sin(Math.toRadians(swipeAngle + score.toDouble())).toFloat()
        canvas.drawText("${percentage.roundToInt()}%", textOnEdgeX, textOnEdgeY, percentagePaint)
        setCanvasCenterText(canvas)
        setPercentIndicatorText(canvas)

        invalidate()
    }

    private fun setCanvasCenterText(canvas: Canvas) {

        val canvasHeight = canvas.height.toFloat()
        val canvasWidth = canvas.width.toFloat()

        val currentScore = "${this.score.getScoreInText()}/ "
        val totalScore = "${this.totalScore.roundToInt()}"

        val sizeOfTheScore = currentScorePaint.measureText(currentScore)

        val xPositionOfScore = (canvasWidth/2)-(scoreTextPaint.measureText(scoreText)/2)
        val yPositionOfScore = (canvasHeight/2)
        canvas.drawText(scoreText, xPositionOfScore, yPositionOfScore, scoreTextPaint)


        val yPositionOfCurrentTotalScore = (canvasHeight/2)+resources.getDimension(R.dimen.text_20sp)
        canvas.drawText(currentScore, canvasWidth/3, yPositionOfCurrentTotalScore, currentScorePaint)
        canvas.drawText(totalScore, canvasWidth/3 + sizeOfTheScore, yPositionOfCurrentTotalScore, totalScorePaint)

    }

    private fun setPercentIndicatorText(canvas: Canvas) {

        val canvasHeight = canvas.height.toFloat()
        val canvasWidth = canvas.width.toFloat()

        canvas.drawText("0%", 0f, canvasHeight, scoreTextPaint)
        canvas.drawText("100%", canvasWidth-scoreTextPaint.measureText("100%"), canvasHeight, scoreTextPaint)


    }
    private fun getGradientMatrix(): Matrix {
        val matrix = Matrix()
        matrix.setRotate(startAngle, width/2, height/2)
        return matrix
    }

    fun progressValidation(score: Float) {
        Log.e("Progress",this.score.toString()+"  "+score)
        if (score >= totalScore)
            this.score = 180f
        else
            this.score = getScore(score)
        invalidate()
    }

    private fun Float.getScoreInText() = totalScore.div(180/this).roundToInt().toString()


    private fun getScore(progress: Float): Float {
        val dividedValue = (progress / totalScore)
        percentage = dividedValue * 100
        return dividedValue * 180
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    fun setRoundedCorner(isRoundCorner: Boolean) {
        this.isRoundCorner = isRoundCorner
        if (isRoundCorner) {
            foreGroundPaint.strokeCap = Paint.Cap.ROUND
            backGroundPaint.strokeCap = Paint.Cap.ROUND
        } else {
            foreGroundPaint.strokeCap = Paint.Cap.SQUARE
            backGroundPaint.strokeCap = Paint.Cap.SQUARE
        }
    }
}