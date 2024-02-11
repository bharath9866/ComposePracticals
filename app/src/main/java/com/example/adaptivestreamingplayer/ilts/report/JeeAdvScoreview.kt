package com.example.adaptivestreamingplayer.ilts.report

//import android.content.Context
//import android.content.res.Resources
//import android.graphics.Bitmap
//import android.graphics.Canvas
//import android.graphics.Color
//import android.graphics.Matrix
//import android.graphics.Paint
//import android.graphics.Rect
//import android.graphics.RectF
//import android.graphics.Typeface
//import android.os.Build
//import android.text.Layout
//import android.text.StaticLayout
//import android.text.TextPaint
//import android.util.AttributeSet
//import android.util.Log
//import android.util.TypedValue
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.core.graphics.drawable.DrawableCompat
//import com.example.adaptivestreamingplayer.R
//
//
//class JeeAdvScoreview : View {
//    private val ifAllScoreEqual = false
//    private var mTotalScore = 0
//    private var mYourScore = 0
//    private var mLowScore = 0
//    private var mCutOffScore = 0f
//    private var mHighScore = 0
//    private var mNegativeMarks = 0
//    private var mainRect: Rect? = null
//    private var mainrectF: RectF? = null
//    var triangle_rect: Rect? = null
//    private var mCenterX = 0
//    private var mCenterY = 0
//    private var mCenterRectHeight = 0
//    private var mCenterRectHeight1stpostion = 0
//    val mResources: Resources = resources
//    var height:Int = 0
//    var width:Int = 0
//    var mMainRectPaint: Paint? = null
//    var mMainRectStrokePaint: Paint? = null
//    var mCenterLinePaint: Paint? = null
//    var mLineRectangles: Paint? = null
//    var mScoreLinePaint: Paint? = null
//    var mTargetLinePaint: Paint? = null
//    var mNegativeRectPaint: Paint? = null
//    var mTargetRectangle: Paint? = null
//    var mStartRectangle: Paint? = null
//    var mDummyLinePaint: Paint? = null
//    var mLowTrainglePaint: Paint? = null
//    var mImagePaint: Paint? = null
//    var mRangeImagePaint: Paint? = null
//    var mStartTextPaint: Paint? = null
//    var mItlaicTextPaint: Paint? = null
//    var mNegativeTextPaint: Paint? = null
//    var mYourTextPaint: Paint? = null
//    var mYourCirclePaint: Paint? = null
//    var mYourCirclePaintAlpha1: Paint? = null
//    var mYourCirclePaintAlpha2: Paint? = null
//    var mYourCirclePaintAlpha3: Paint? = null
//    var framesPerSecond = 60
//    var animationDuration: Long = 10000 // 10 seconds
//    var start_end_rect_point = 0
//    var start_point = 0
//    var negative_start_point = 0.0
//    var negative_end_point = 0.0
//    var matrix = Matrix() // transformation matrix
//    var startTime: Long = 0
//    var positive_line_width = 0.0
//    var negative_line_width = 0.0
//    var mBoldTextPaint: TextPaint? = null
//    var mRegularTextPaint: TextPaint? = null
//    var mNegtaiveTextPaint: TextPaint? = null
//    var mRegularStartTextPaint: TextPaint? = null
//    var mRegularRangeTextPaint: TextPaint? = null
//    var mBoldRangeTextPaint: TextPaint? = null
//    var mSemiBoldTextPaint: TextPaint? = null
//    var mYourLabelPaint: TextPaint? = null
//    var mont_bold: Typeface? = null
//    var mont_medium: Typeface? = null
//    var mont_semibold: Typeface? = null
//    var mont_regular: Typeface? = null
//
//    constructor(context: Context?) : super(context) {
//        init(null)
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
//        init(attrs)
//    }
//
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
//        context,
//        attrs,
//        defStyleAttr
//    ) {
//        init(attrs)
//    }
//
//    constructor(
//        context: Context?,
//        attrs: AttributeSet?,
//        defStyleAttr: Int,
//        defStyleRes: Int
//    ) : super(context, attrs, defStyleAttr, defStyleRes) {
//        init(attrs)
//    }
//
//    fun init(attributeSet: AttributeSet?) {
//        val metrics = mResources.getDisplayMetrics()
//        width = metrics.widthPixels
//        height = metrics.heightPixels
//        mCenterRectHeight = mResources.getDimension(R.dimen.dimen_4dp).toInt()
//        mCenterRectHeight1stpostion = mResources.getDimension(R.dimen.dimen_9dp).toInt()
//        mainRect = Rect()
//        triangle_rect = Rect()
//        mainRect!!.top = mResources.getDimension(R.dimen.dimen_10dp).toInt()
//        mainRect!!.left = mResources.getDimension(R.dimen.dimen_10dp).toInt()
//        mainRect!!.bottom = mResources.getDimension(R.dimen.dimen_160dp).toInt()
//        mainRect!!.right = (width - mResources.getDimension(R.dimen.dimen_10dp)).toInt()
//        Log.d(TAG, "init: " + mainRect!!.right / 4.5)
//        //start_end_rect_point = (int) (mainRect.left + mResources.getDimension(R.dimen._50sdp));
//        start_end_rect_point = (mainRect!!.right / 4.5).toInt()
//        setStart_end_rect_point(start_end_rect_point)
//        start_point = start_end_rect_point
//        setStart_point(start_point)
//        negative_start_point = start_end_rect_point.toDouble()
//        negative_end_point = mainRect!!.left.toDouble()
//        mont_bold = Typeface.createFromAsset(context.assets, "fonts/montserrat_bold.ttf")
//        mont_regular = Typeface.createFromAsset(context.assets, "fonts/montserrat_regular.ttf")
//        mont_semibold = Typeface.createFromAsset(context.assets, "fonts/montserrat_semibold.ttf")
//        mont_medium = Typeface.createFromAsset(context.assets, "fonts/montserrat_medium.ttf")
//        mCenterX = mainRect!!.centerX()
//        mCenterY = mainRect!!.centerY()
//        mMainRectPaint = Paint()
//        mMainRectPaint!!.color = Color.WHITE
//        mMainRectPaint!!.isAntiAlias = true
//        mMainRectPaint!!.style = Paint.Style.FILL
//        mMainRectStrokePaint = Paint()
//        mMainRectStrokePaint!!.color = Color.parseColor("#E7E8F1")
//        mMainRectStrokePaint!!.isAntiAlias = true
//        mMainRectStrokePaint!!.style = Paint.Style.STROKE
//        mMainRectStrokePaint!!.strokeWidth = 2f
//        mCenterLinePaint = Paint()
//        mCenterLinePaint!!.color = Color.parseColor("#D9DBE9")
//        mCenterLinePaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mCenterLinePaint!!.style = Paint.Style.FILL
//        mDummyLinePaint = Paint()
//        mDummyLinePaint!!.color = Color.parseColor("#00FF00")
//        mDummyLinePaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mDummyLinePaint!!.style = Paint.Style.FILL_AND_STROKE
//        mDummyLinePaint!!.strokeWidth = mResources.getDimension(R.dimen.dimen_6dp)
//        mScoreLinePaint = Paint()
//        mScoreLinePaint!!.color = Color.parseColor("#A5AAAF")
//        mScoreLinePaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mScoreLinePaint!!.style = Paint.Style.FILL_AND_STROKE
//        mScoreLinePaint!!.strokeWidth = mResources.getDimension(R.dimen.dimen_1dp)
//        mTargetLinePaint = Paint()
//        mTargetLinePaint!!.color = Color.parseColor("#C9BAF1")
//        mTargetLinePaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mTargetLinePaint!!.style = Paint.Style.FILL_AND_STROKE
//        mTargetLinePaint!!.strokeWidth = mResources.getDimension(R.dimen.dimen_2dp)
//        mLineRectangles = Paint()
//        mLineRectangles!!.color = Color.parseColor("#A0A3BD")
//        mLineRectangles!!.flags = Paint.ANTI_ALIAS_FLAG
//        mLineRectangles!!.style = Paint.Style.FILL
//        mLowTrainglePaint = Paint()
//        mLowTrainglePaint!!.color = Color.parseColor("#EC9191")
//        mLowTrainglePaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mLowTrainglePaint!!.style = Paint.Style.FILL
//        mNegativeRectPaint = Paint()
//        mNegativeRectPaint!!.color = Color.parseColor("#EECCCC")
//        mNegativeRectPaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mNegativeRectPaint!!.style = Paint.Style.FILL
//        mNegativeRectPaint!!.alpha = 200
//        mTargetRectangle = Paint()
//        mTargetRectangle!!.color = Color.parseColor("#4E4B66")
//        mTargetRectangle!!.flags = Paint.ANTI_ALIAS_FLAG
//        mTargetRectangle!!.style = Paint.Style.FILL
//        mStartRectangle = Paint()
//        mStartRectangle!!.color = Color.parseColor("#EFF0F6")
//        mStartRectangle!!.flags = Paint.ANTI_ALIAS_FLAG
//        mStartRectangle!!.style = Paint.Style.FILL
//        mImagePaint = Paint()
//        mImagePaint!!.isAntiAlias = true
//        mImagePaint!!.isFilterBitmap = false
//        mImagePaint!!.isDither = false
//        mRangeImagePaint = Paint()
//        mRangeImagePaint!!.isAntiAlias = true
//        mRangeImagePaint!!.textAlign = Paint.Align.LEFT
//        mRangeImagePaint!!.isFilterBitmap = false
//        mRangeImagePaint!!.isDither = false
//        mainrectF = RectF(
//            mainRect!!.left.toFloat(),
//            mainRect!!.top.toFloat(),
//            mainRect!!.right.toFloat(),
//            mainRect!!.bottom.toFloat()
//        )
//        mStartTextPaint = Paint()
//        mStartTextPaint!!.color = Color.BLACK
//        mStartTextPaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mStartTextPaint!!.textSize = mResources.getDimension(R.dimen.dimen_12dp)
//        mStartTextPaint!!.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
//        mItlaicTextPaint = Paint()
//        mItlaicTextPaint!!.color = Color.BLACK
//        mItlaicTextPaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mItlaicTextPaint!!.textSize = mResources.getDimension(R.dimen.dimen_10dp)
//        mItlaicTextPaint!!.setTypeface(mont_regular)
//        mNegativeTextPaint = Paint()
//        mNegativeTextPaint!!.color = Color.BLACK
//        mNegativeTextPaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mNegativeTextPaint!!.textSize = mResources.getDimension(R.dimen.text_10sp)
//        mNegativeTextPaint!!.setTypeface(mont_regular)
//        mYourTextPaint = Paint()
//        mYourTextPaint!!.color = Color.parseColor("#79BC82")
//        mYourTextPaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mYourTextPaint!!.textSize = mResources.getDimension(R.dimen.text_11sp)
//        mYourTextPaint!!.setTypeface(mont_bold)
//        mYourCirclePaint = Paint()
//        mYourCirclePaint!!.color = Color.parseColor("#79BC83")
//        mYourCirclePaint!!.flags = Paint.ANTI_ALIAS_FLAG
//        mYourCirclePaint!!.style = Paint.Style.FILL
//        mYourCirclePaintAlpha1 = Paint()
//        mYourCirclePaintAlpha1!!.color = Color.parseColor("#BCF1C3")
//        mYourCirclePaintAlpha1!!.flags = Paint.ANTI_ALIAS_FLAG
//        mYourCirclePaintAlpha1!!.style = Paint.Style.FILL
//        mYourCirclePaintAlpha2 = Paint()
//        mYourCirclePaintAlpha2!!.color = Color.parseColor("#D7FADC")
//        mYourCirclePaintAlpha2!!.flags = Paint.ANTI_ALIAS_FLAG
//        mYourCirclePaintAlpha2!!.style = Paint.Style.FILL
//        mYourCirclePaintAlpha3 = Paint()
//        mYourCirclePaintAlpha3!!.color = Color.parseColor("#ECFFEE")
//        mYourCirclePaintAlpha3!!.flags = Paint.ANTI_ALIAS_FLAG
//        mYourCirclePaintAlpha3!!.style = Paint.Style.FILL
//        mBoldTextPaint = TextPaint()
//        mBoldTextPaint!!.isAntiAlias = true
//        mBoldTextPaint!!.textAlign = Paint.Align.CENTER
//        mBoldTextPaint!!.setTypeface(mont_bold)
//        mBoldTextPaint!!.textSize = mResources.getDimension(R.dimen.text_12sp)
//        mBoldTextPaint!!.color = Color.BLACK
//        mBoldRangeTextPaint = TextPaint()
//        mBoldRangeTextPaint!!.isAntiAlias = true
//        mBoldRangeTextPaint!!.textAlign = Paint.Align.CENTER
//        mBoldRangeTextPaint!!.setTypeface(mont_semibold)
//        mBoldRangeTextPaint!!.textSize = mResources.getDimension(R.dimen.text_12sp)
//        mBoldRangeTextPaint!!.color = Color.parseColor("#1E2A36")
//        mSemiBoldTextPaint = TextPaint()
//        mSemiBoldTextPaint!!.isAntiAlias = true
//        mSemiBoldTextPaint!!.textAlign = Paint.Align.CENTER
//        mSemiBoldTextPaint!!.setTypeface(mont_semibold)
//        mSemiBoldTextPaint!!.textSize = mResources.getDimension(R.dimen.text_10sp)
//        mSemiBoldTextPaint!!.color = Color.BLACK
//        mRegularTextPaint = TextPaint()
//        mRegularTextPaint!!.isAntiAlias = true
//        mRegularTextPaint!!.textAlign = Paint.Align.CENTER
//        mRegularTextPaint!!.setTypeface(mont_medium)
//        mRegularTextPaint!!.textSize = mResources.getDimension(R.dimen.text_12sp)
//        mRegularTextPaint!!.color = Color.BLACK
//        mNegtaiveTextPaint = TextPaint()
//        mNegtaiveTextPaint!!.isAntiAlias = true
//        mNegtaiveTextPaint!!.textAlign = Paint.Align.CENTER
//        mNegtaiveTextPaint!!.setTypeface(mont_medium)
//        mNegtaiveTextPaint!!.textSize = mResources.getDimension(R.dimen.text_8sp)
//        mNegtaiveTextPaint!!.color = Color.BLACK
//        mRegularStartTextPaint = TextPaint()
//        mRegularStartTextPaint!!.isAntiAlias = true
//        mRegularStartTextPaint!!.textAlign = Paint.Align.CENTER
//        mRegularStartTextPaint!!.setTypeface(mont_semibold)
//        mRegularStartTextPaint!!.textSize = mResources.getDimension(R.dimen.text_10sp)
//        mRegularStartTextPaint!!.color = Color.parseColor("#1E2A36")
//        mRegularRangeTextPaint = TextPaint()
//        mRegularRangeTextPaint!!.isAntiAlias = true
//        mRegularRangeTextPaint!!.textAlign = Paint.Align.LEFT
//        mRegularRangeTextPaint!!.setTypeface(mont_regular)
//        mRegularRangeTextPaint!!.textSize = mResources.getDimension(R.dimen.text_10sp)
//        mRegularRangeTextPaint!!.color = Color.BLACK
//        mYourLabelPaint = TextPaint()
//        mYourLabelPaint!!.isAntiAlias = true
//        mYourLabelPaint!!.textAlign = Paint.Align.CENTER
//        mYourLabelPaint!!.setTypeface(mont_bold)
//        mYourLabelPaint!!.textSize = mResources.getDimension(R.dimen.text_12sp)
//        mYourLabelPaint!!.color = Color.parseColor("#79BC82")
//        positive_line_width =
//            (mainRect!!.right - mResources.getDimension(R.dimen.dimen_10dp) - start_point).toDouble()
//        setPositive_line_width(positive_line_width)
//        negative_line_width = (mainRect!!.left - start_end_rect_point).toDouble()
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        canvas.drawRoundRect(mainrectF!!, 25f, 25f, mMainRectPaint!!)
//        canvas.drawRoundRect(mainrectF!!, 25f, 25f, mMainRectStrokePaint!!)
//        drawstartpoint(canvas)
//        drawScore(canvas)
//        canvas.drawRect(
//            mainRect!!.left.toFloat(),
//            (mainRect!!.centerY() + mCenterRectHeight).toFloat(),
//            mainRect!!.right - mResources!!.getDimension(R.dimen.dimen_10dp),
//            (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//            mCenterLinePaint!!
//        )
//        drawlinerectangles(canvas)
//        drawtargetscore(canvas)
//        drawtargetrectangle(canvas)
//        canvas.drawRect(
//            mainRect!!.left.toFloat(),
//            (mainRect!!.centerY() + mCenterRectHeight).toFloat(),
//            start_end_rect_point - mResources!!.getDimension(R.dimen.dimen_8dp),
//            (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//            mNegativeRectPaint!!
//        )
//    }
//
//    fun drawlinerectangles(canvas: Canvas) {
//        val space = 20
//        for (i in 1 until (mainRect!!.right / mainRect!!.left)) {
//            canvas.drawRect(
//                (mainRect!!.left * i).toFloat(),
//                (mainRect!!.centerY() + mCenterRectHeight).toFloat(),
//                (mainRect!!.left * i + mainRect!!.left - 10).toFloat(),
//                (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//                mLineRectangles!!
//            )
//        }
//    }
//
//    fun drawtargetrectangle(canvas: Canvas) {
//        canvas.drawRect(
//            mainRect!!.right - mResources!!.getDimension(R.dimen.dimen_18dp),
//            (mainRect!!.centerY() + mCenterRectHeight).toFloat(),
//            mainRect!!.right - mResources!!.getDimension(R.dimen.dimen_10dp),
//            (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//            mTargetRectangle!!
//        )
//        val text = "Max"
//        val mHighValue = getmTotalScore().toString()
//        val staticLayout = StaticLayout(
//            text, mRegularTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val staticLayout1 = StaticLayout(
//            mHighValue, mBoldTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0f, false
//        )
//        canvas.save()
//        canvas.translate(
//            mainRect!!.right - mResources!!.getDimension(R.dimen.dimen_13dp),
//            (mCenterY + mCenterRectHeight * 3).toFloat()
//        )
//        staticLayout.draw(canvas)
//        canvas.restore()
//        canvas.save()
//        canvas.translate(
//            mainRect!!.right - mResources!!.getDimension(R.dimen.dimen_13dp),
//            (mCenterY + mCenterRectHeight * 7).toFloat()
//        )
//        staticLayout1.draw(canvas)
//        canvas.restore()
//    }
//
//    fun drawstartpoint(canvas: Canvas) {
//        val start_rect_point =
//            (start_end_rect_point - mResources!!.getDimension(R.dimen.dimen_8dp)).toInt()
//        val start_2nd_rect_point = start_end_rect_point
//        val start_2nd_end_rect_point =
//            (start_end_rect_point + mResources!!.getDimension(R.dimen.dimen_8dp)).toInt()
//        Log.d(TAG, "drawstartpoint: $start_end_rect_point")
//        canvas.drawRect(
//            mainRect!!.left.toFloat(),
//            (mainRect!!.centerY() + mCenterRectHeight).toFloat(),
//            start_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//            mNegativeRectPaint!!
//        )
//
//
//        //0th position
//        canvas.drawRect(
//            start_end_rect_point - mResources!!.getDimension(R.dimen.dimen_8dp),
//            (mainRect!!.centerY() + mCenterRectHeight).toFloat(),
//            start_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//            mStartRectangle!!
//        )
//        canvas.drawRect(
//            start_end_rect_point.toFloat(),
//            (mainRect!!.centerY() + mCenterRectHeight).toFloat(),
//            start_end_rect_point + mResources!!.getDimension(R.dimen.dimen_8dp),
//            (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//            mTargetRectangle!!
//        )
//
//        //1stposition
//        canvas.drawRect(
//            start_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 3).toFloat(),
//            start_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//            mTargetRectangle!!
//        )
//        canvas.drawRect(
//            start_2nd_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 3).toFloat(),
//            start_2nd_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight).toFloat(),
//            mStartRectangle!!
//        )
//
//        //2ndposition
//        canvas.drawRect(
//            start_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 5).toFloat(),
//            start_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 3).toFloat(),
//            mStartRectangle!!
//        )
//        canvas.drawRect(
//            start_2nd_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 5).toFloat(),
//            start_2nd_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 3).toFloat(),
//            mTargetRectangle!!
//        )
//
//        //3rdposition
//        canvas.drawRect(
//            start_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 7).toFloat(),
//            start_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 5).toFloat(),
//            mTargetRectangle!!
//        )
//        canvas.drawRect(
//            start_2nd_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 7).toFloat(),
//            start_2nd_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 5).toFloat(),
//            mStartRectangle!!
//        )
//
//        //4thposition
//        canvas.drawRect(
//            start_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 9).toFloat(),
//            start_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 7).toFloat(),
//            mStartRectangle!!
//        )
//        canvas.drawRect(
//            start_2nd_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 9).toFloat(),
//            start_2nd_end_rect_point.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 7).toFloat(),
//            mTargetRectangle!!
//        )
//
//
//        //Negative Rect
//        val text = """
//             Negative
//             Mark's
//             """.trimIndent()
//        Log.d(TAG, "drawstartpoint: " + mainRect!!.left + "---" + start_rect_point)
//        val staticLayout = StaticLayout(
//            text, mNegtaiveTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_CENTER, 1.0f, 0f, false
//        )
//        canvas.save()
//        canvas.translate(
//            mainRect!!.left.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 8).toFloat()
//        )
//        staticLayout.draw(canvas)
//        canvas.restore()
//        canvas.drawText(
//            "0",
//            start_end_rect_point - mResources!!.getDimension(R.dimen.dimen_3dp),
//            (mCenterY + mCenterRectHeight * 4).toFloat(),
//            mStartTextPaint!!
//        )
//    }
//
//    var mYourPositiveScored_formula = 0
//    fun getmLineYourPositiveScored_formula(): Int {
//        return mLineYourPositiveScored_formula
//    }
//
//    fun setmLineYourPositiveScored_formula(mLineYourPositiveScored_formula: Int) {
//        this.mLineYourPositiveScored_formula = mLineYourPositiveScored_formula
//        invalidate()
//    }
//
//    fun getStart_point(): Int {
//        return start_point
//    }
//
//    fun setStart_point(start_point: Int) {
//        this.start_point = start_point
//        invalidate()
//    }
//
//    fun getStart_end_rect_point(): Int {
//        return start_end_rect_point
//    }
//
//    fun setStart_end_rect_point(start_end_rect_point: Int) {
//        this.start_end_rect_point = start_end_rect_point
//        invalidate()
//    }
//
//    fun getPositive_line_width(): Double {
//        return positive_line_width
//    }
//
//    fun setPositive_line_width(positive_line_width: Double) {
//        this.positive_line_width = positive_line_width
//        invalidate()
//    }
//
//    var mLineYourPositiveScored_formula = 0
//    var mLowScored_formula = 0
//    var mCutOff_formula = 0f
//    var high_formula = 0
//    fun updatecircleanimation(canvas: Canvas) {
//        mYourPositiveScored_formula = if (getmYourScore() >= 0) {
//            (start_end_rect_point + positive_line_width / getmTotalScore() * getmYourScore()).toInt()
//        } else {
//            (negative_start_point + negative_line_width / getmNegativeMarks() * getmYourScore()).toInt()
//        }
//        Log.d(
//            TAG,
//            "updatecircleanimation: $mLineYourPositiveScored_formula"
//        )
//        canvas.drawCircle(
//            mLineYourPositiveScored_formula.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 14).toFloat(),
//            32f,
//            mYourCirclePaintAlpha3!!
//        )
//        canvas.drawCircle(
//            mLineYourPositiveScored_formula.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 14).toFloat(),
//            28f,
//            mYourCirclePaintAlpha2!!
//        )
//        canvas.drawCircle(
//            mLineYourPositiveScored_formula.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 14).toFloat(),
//            24f,
//            mYourCirclePaintAlpha1!!
//        )
//        canvas.drawCircle(
//            mLineYourPositiveScored_formula.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 14).toFloat(),
//            20f,
//            mYourCirclePaint!!
//        )
//        Log.d(
//            TAG,
//            "updatecircleanimation: $mYourPositiveScored_formula"
//        )
//    }
//
//    fun drawScore(canvas: Canvas) {
//        mYourPositiveScored_formula = if (mYourScore >= 0) {
//            (start_end_rect_point + positive_line_width / getmTotalScore() * getmYourScore()).toInt()
//        } else {
//            (negative_start_point + negative_line_width / getmNegativeMarks() * getmYourScore()).toInt()
//        }
//        mLowScored_formula = if (mLowScore >= 0) {
//            (start_end_rect_point + positive_line_width / getmTotalScore() * getmLowScore()).toInt()
//        } else {
//            (negative_start_point + negative_line_width / getmNegativeMarks() * getmLowScore()).toInt()
//        }
//        mCutOff_formula = if (mCutOffScore >= 0f) {
//            (start_end_rect_point + positive_line_width / getmTotalScore() * getmCutOffScore()).toInt()
//                .toFloat()
//        } else {
//            (negative_start_point + negative_line_width / getmNegativeMarks() * getmCutOffScore()).toInt()
//                .toFloat()
//        }
//        high_formula = if (mHighScore >= 0) {
//            (start_end_rect_point + positive_line_width / getmTotalScore() * getmHighScore()).toInt()
//        } else {
//            (negative_start_point + negative_line_width / getmNegativeMarks() * getmHighScore()).toInt()
//        }
//        val mLowBitmap = getBitmapFromVectorDrawable(
//            context, R.drawable.ic_score_low
//        )
//        val mHighBitmap = getBitmapFromVectorDrawable(
//            context, R.drawable.ic_score_high
//        )
//        val mCutOffBitmap = getBitmapFromVectorDrawable(
//            context, R.drawable.ic_score_cut_off
//        )
//        val mYou = "You"
//        val mLowText = "Low"
//        val mHighText = "High"
//        val mCutoffText = "Cutoff"
//        val mLowHigh = "Low + High"
//        val mLowCutoff = "Low + Cutoff"
//        val mHighCutoff = "High+Cutoff"
//        val mAllText = "Low + High + You"
//        val mYourValue = mYourScore.toString()
//        val mLowValue = mLowScore.toString()
//        val mHighValue = mHighScore.toString()
//        val mCutoffValue = mCutOffScore.toString()
//        val mAllSL = StaticLayout(
//            mAllText, mRegularTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mYouSL = StaticLayout(
//            mYou, mBoldTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mLowTextSL = StaticLayout(
//            mLowText, mRegularTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mHighTextSL = StaticLayout(mHighText, mRegularTextPaint, convertDpToPixels(200f, context), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false)
//        val mCutoffTextSL = StaticLayout(
//            mCutoffText, mRegularTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mLowHighSL = StaticLayout(
//            mLowHigh, mRegularTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mLowCutoffSL = StaticLayout(
//            mLowCutoff, mRegularTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mHighCutoffSL = StaticLayout(
//            mHighCutoff, mRegularTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mLowValueSL = StaticLayout(
//            mLowValue, mBoldTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mHighValueSL = StaticLayout(
//            mHighValue, mBoldTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mCutoffValueSL = StaticLayout(
//            mCutoffValue, mBoldTextPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        val mYouValueSL = StaticLayout(
//            mYourValue, mYourLabelPaint, convertDpToPixels(
//                200f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false
//        )
//        updatecircleanimation(canvas)
//        canvas.drawLine(
//            mLineYourPositiveScored_formula.toFloat(),
//            mCenterY.toFloat(),
//            mLineYourPositiveScored_formula.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 11).toFloat(),
//            mScoreLinePaint!!
//        )
//        //LowScore
//        canvas.drawBitmap(
//            mLowBitmap,
//            (mLowScored_formula - mLowBitmap.width / 2).toFloat(),
//            (mCenterY - mCenterRectHeight * 10).toFloat(),
//            mImagePaint
//        )
//        canvas.drawLine(
//            mLowScored_formula.toFloat(),
//            mCenterY.toFloat(),
//            mLowScored_formula.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 6).toFloat(),
//            mScoreLinePaint!!
//        )
//
//        //HighScore
//        canvas.drawBitmap(
//            mHighBitmap,
//            (high_formula - mHighBitmap.width / 2).toFloat(),
//            (mCenterY - mCenterRectHeight * 10).toFloat(),
//            mImagePaint
//        )
//        canvas.drawLine(
//            high_formula.toFloat(),
//            mCenterY.toFloat(),
//            high_formula.toFloat(),
//            (mainRect!!.centerY() - mCenterRectHeight * 6).toFloat(),
//            mScoreLinePaint!!
//        )
//
//        //CutOffScore
//        canvas.drawBitmap(
//            mCutOffBitmap,
//            mCutOff_formula - mCutOffBitmap.width / 2,
//            (mCenterY - mCenterRectHeight * 10).toFloat(),
//            mImagePaint
//        )
//        canvas.drawLine(
//            mCutOff_formula,
//            mCenterY.toFloat(),
//            mCutOff_formula,
//            (mainRect!!.centerY() - mCenterRectHeight * 6).toFloat(),
//            mScoreLinePaint!!
//        )
//        drawBottomAllScoresCollide(canvas)
//        mYourLabelPaint!!.color = Color.parseColor("#79BC82")
//        canvas.save()
//        canvas.translate(
//            mLineYourPositiveScored_formula.toFloat(),
//            (mCenterY + mCenterRectHeight * 4).toFloat()
//        )
//        mYouSL.draw(canvas)
//        canvas.restore()
//        canvas.save()
//        canvas.translate(
//            mLineYourPositiveScored_formula.toFloat(),
//            (mCenterY + mCenterRectHeight * 8).toFloat()
//        )
//        mYouValueSL.draw(canvas)
//        canvas.restore()
//        Log.d(TAG, "validate: Only you")
//    }
//
//    private fun convertDpToPixels(dp: Float, context: Context): Int {
//        return TypedValue.applyDimension(
//            TypedValue.COMPLEX_UNIT_DIP,
//            dp,
//            context.resources.displayMetrics
//        ).toInt()
//    }
//
//    fun drawtargetscore(canvas: Canvas) {
//        val bmp = getBitmapFromVectorDrawable(
//            context, R.drawable.ic_score_target_flag
//        )
//        canvas.drawBitmap(
//            bmp,
//            mainRect!!.right - mResources.getDimension(R.dimen.dimen_16dp),
//            (mCenterY - mCenterRectHeight * 12).toFloat(),
//            mImagePaint
//        )
//        canvas.drawLine(
//            mainRect!!.right - mResources.getDimension(R.dimen.dimen_14dp),
//            mCenterY.toFloat(),
//            mainRect!!.right - mResources.getDimension(R.dimen.dimen_14dp),
//            (mainRect!!.centerY() - mCenterRectHeight * 12).toFloat(),
//            mTargetLinePaint!!
//        )
//    }
//
//    fun getmTotalScore(): Int {
//        return mTotalScore
//    }
//
//    fun setmTotalScore(mTotalScore: Int) {
//        this.mTotalScore = mTotalScore
//        invalidate()
//    }
//
//    fun getmYourScore(): Int {
//        return mYourScore
//    }
//
//    fun setmYourScore(mYourScore: Int) {
//        this.mYourScore = mYourScore
//        invalidate()
//    }
//
//    fun getmLowScore(): Int {
//        return mLowScore
//    }
//
//    fun setmLowScore(mLowScore: Int) {
//        this.mLowScore = mLowScore
//        invalidate()
//    }
//
//    fun getmCutOffScore(): Float {
//        return mCutOffScore
//    }
//
//    fun setmCutOffScore(mCutOffScore: Float) {
//        this.mCutOffScore = mCutOffScore
//        invalidate()
//    }
//
//    fun getmHighScore(): Int {
//        return mHighScore
//    }
//
//    fun setmHighScore(mHighScore: Int) {
//        this.mHighScore = mHighScore
//        invalidate()
//    }
//
//    fun getmNegativeMarks(): Int {
//        return mNegativeMarks
//    }
//
//    fun setmNegativeMarks(mNegativeMarks: Int) {
//        this.mNegativeMarks = mNegativeMarks
//        invalidate()
//    }
//
//    //All Collided
//    fun drawBottomAllScoresCollide(canvas: Canvas) {
//        val padding = mResources!!.getDimension(R.dimen.dimen_15dp).toInt()
//        val mLeftPadding = mainRect!!.left + padding
//        val mRightPadding =
//            (mainRect!!.right - mResources!!.getDimension(R.dimen.dimen_18dp)).toInt()
//        val LowBitmap = getBitmapFromVectorDrawable(
//            context, R.drawable.ic_score_low
//        )
//        val HighBitmap = getBitmapFromVectorDrawable(
//            context, R.drawable.ic_score_high
//        )
//        val CutOffBitmap = getBitmapFromVectorDrawable(
//            context, R.drawable.ic_score_cut_off
//        )
//        val mLowText = "Low"
//        val mHighText = "High"
//        val mCutOff = "CutOff"
//        val mLowValue = mLowScore.toString()
//        val mHighValue = mHighScore.toString()
//        val mCutOffValue = mCutOffScore.toString()
//        val staticLayout = StaticLayout(
//            mLowText, mRegularRangeTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 0f, 0f, false
//        )
//        val staticLayout1 = StaticLayout(
//            mLowValue, mBoldRangeTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 0f, 0f, false
//        )
//        canvas.save()
//        canvas.translate(
//            mRightPadding - mResources!!.getDimension(R.dimen.dimen_270dp),
//            (mCenterY + mCenterRectHeight * 13.5).toFloat()
//        )
//        canvas.drawBitmap(LowBitmap, 0.toFloat(), 0f, mRangeImagePaint)
//        canvas.restore()
//        canvas.save()
//        canvas.translate(
//            (mRightPadding - mResources!!.getDimension(R.dimen.dimen_240dp)),
//            (mCenterY + mCenterRectHeight * 14.3).toFloat()
//        )
//        staticLayout.draw(canvas)
//        canvas.restore()
//        canvas.save()
//        canvas.translate(
//            (mRightPadding - mResources!!.getDimension(R.dimen.dimen_210dp)),
//            (mCenterY + mCenterRectHeight * 14.0).toFloat()
//        )
//        staticLayout1.draw(canvas)
//        canvas.restore()
//        val mHighLeftPadding =
//            (mLeftPadding + LowBitmap.width + padding / 2 + staticLayout.ellipsizedWidth / 1.5).toInt()
//        //High
//        val staticLayoutHigh = StaticLayout(
//            mHighText, mRegularRangeTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 0f, 0f, false
//        )
//        val staticLayoutHighValue = StaticLayout(
//            mHighValue, mBoldRangeTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 0f, 0f, false
//        )
//        canvas.save()
//        canvas.translate(
//            mRightPadding - mResources!!.getDimension(R.dimen.dimen_180dp),
//            (mCenterY + mCenterRectHeight * 13.5).toFloat()
//        )
//        canvas.drawBitmap(HighBitmap, 0.toFloat(), 0f, mRangeImagePaint)
//        canvas.restore()
//        canvas.save()
//        canvas.translate(
//            (mRightPadding - mResources!!.getDimension(R.dimen.dimen_150dp)),
//            (mCenterY + mCenterRectHeight * 14.3).toFloat()
//        )
//        staticLayoutHigh.draw(canvas)
//        canvas.restore()
//        canvas.save()
//        canvas.translate(
//            mRightPadding.toFloat() - mResources!!.getDimension(R.dimen.dimen_110dp),
//            (mCenterY + mCenterRectHeight * 14.0).toFloat()
//        )
//        staticLayoutHighValue.draw(canvas)
//        canvas.restore()
//
//
//        //CutoFF
//        val staticLayoutCutOff = StaticLayout(
//            mCutOff, mRegularRangeTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 0f, 0f, false
//        )
//        val staticLayoutCutOffValue = StaticLayout(
//            mCutOffValue, mBoldRangeTextPaint, convertDpToPixels(
//                100f,
//                context
//            ), Layout.Alignment.ALIGN_NORMAL, 0f, 0f, false
//        )
//        canvas.save()
//        canvas.translate(
//            mRightPadding - mResources!!.getDimension(R.dimen.dimen_90dp),
//            (mCenterY + mCenterRectHeight * 13.5).toFloat()
//        )
//        canvas.drawBitmap(CutOffBitmap, 0.toFloat(), 0f, mRangeImagePaint)
//        canvas.restore()
//        canvas.save()
//        canvas.translate(
//            (mRightPadding - mResources!!.getDimension(R.dimen.dimen_60dp)),
//            (mCenterY + mCenterRectHeight * 14.3).toFloat()
//        )
//        staticLayoutCutOff.draw(canvas)
//        canvas.restore()
//        canvas.save()
//        canvas.translate(mRightPadding.toFloat(), (mCenterY + mCenterRectHeight * 14.0).toFloat())
//        staticLayoutCutOffValue.draw(canvas)
//        canvas.restore()
//    }
//
//    companion object {
//        private const val TAG = "SCOREVIEW"
//        fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
//            var drawable = ContextCompat.getDrawable(context, drawableId)
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                drawable = DrawableCompat.wrap(drawable!!).mutate()
//            }
//            val bitmap = Bitmap.createBitmap(
//                context.resources.getDimension(R.dimen.dimen_15dp).toInt(),
//                context.resources.getDimension(R.dimen.dimen_15dp).toInt(),
//                Bitmap.Config.ARGB_8888
//            )
//            val canvas = Canvas(bitmap)
//            drawable!!.setBounds(0, 0, canvas.width, canvas.height)
//            drawable.draw(canvas)
//            return bitmap
//        }
//    }
//}