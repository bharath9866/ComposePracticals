package com.example.adaptivestreamingplayer.ilts.report;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScoreProgressView extends View {

    Resources mResources;
    Rect mainRect;
    RectF mainrectf;
    RectF scoreRectF;
    RectF arcRectF;
    RectF arcFrameRectF;
    Paint mMainRectPaint;
    Paint mMainRectStrokePaint;
    Paint emptyScorePaint;
    Paint emptyArcPaint;
    Paint arcPaint;
    Paint backGroundPaint;
    Paint foreGroundPaint;

    TextPaint scoreTextPaint;
    TextPaint percentagePaint;
    TextPaint currentScorePaint;
    TextPaint totalScorePaint;

    DisplayMetrics displayMetrics;

    int width, height;
    int widthOfTheScore, heightOfTheScore;
    int mCenterX, mCenterY;


    Typeface mont_bold;
    Typeface mont_medium;
    Typeface mont_semibold;
    Typeface mont_regular;

    Float startAngle = 180f;
    Float swipeAngle = 180f;
    Float DEFAULT_PROGRESSBAR_WIDTH = convertDpToPixelsFloat(39f, getContext());

    Float progressbarWidth = DEFAULT_PROGRESSBAR_WIDTH;


    int DEFAULT_FOREGROUND_PROGRESS_COLOR = Color.parseColor("#FBD93E");
    int DEFAULT_BACKGROUND_PROGRESS_COLOR = Color.parseColor("#EFF0F6");
    int foregroundProgressColor = DEFAULT_FOREGROUND_PROGRESS_COLOR;
    int backgroundProgressColor = DEFAULT_BACKGROUND_PROGRESS_COLOR;

    float DEFAULT_PROGRESS = 400F;
    float DEFAULT_TOTAL_SCORE = 800F;
    float DEFAULT_PERCENTAGE = 0F;
    float scoreInSweepAngle = DEFAULT_PROGRESS;
    float mTotalScore = DEFAULT_TOTAL_SCORE;
    float mPercentage = DEFAULT_PERCENTAGE;

    public ScoreProgressView(Context context) {
        super(context);
        init(null);
        progressValidation(scoreInSweepAngle);
    }

    public ScoreProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        progressValidation(scoreInSweepAngle);
    }

    public ScoreProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        progressValidation(scoreInSweepAngle);
    }

    public ScoreProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
        progressValidation(scoreInSweepAngle);
    }

    public void init(AttributeSet attributeSet) {
        mResources = getResources();

        displayMetrics = mResources.getDisplayMetrics();

        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        widthOfTheScore = (int) convertDpToPixels(250, getContext());
        heightOfTheScore = (int) convertDpToPixels(144, getContext());

        mainRect = new Rect();
        mainRect.top = (int) convertDpToPixels(16, getContext());
        mainRect.left = (int) convertDpToPixels(16, getContext());
        mainRect.bottom = (int) convertDpToPixels(192, getContext());
        mainRect.right = (int) (width - convertDpToPixels(16, getContext()));

        mCenterX = mainRect.centerX();
        mCenterY = mainRect.centerY();

        mont_semibold = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_semibold.ttf");
        mont_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_bold.ttf");
        mont_regular = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_regular.ttf");
        mont_medium = Typeface.createFromAsset(getContext().getAssets(), "fonts/montserrat_medium.ttf");

        scoreTextPaint = new TextPaint();
        scoreTextPaint.setColor(Color.parseColor("#6E7191"));
        scoreTextPaint.setTextSize(convertDpToPixels(12, getContext()));
        scoreTextPaint.setAntiAlias(true);
        scoreTextPaint.setTypeface(mont_medium);

        mainrectf = new RectF(mainRect.left, mainRect.top, mainRect.right, mainRect.bottom);
        scoreRectF = new RectF(
                mainRect.centerX() - ((float) widthOfTheScore / 2),
                mainRect.centerY() - ((float) heightOfTheScore / 2),
                mainRect.centerX() + ((float) widthOfTheScore / 2),
                mainRect.centerY() + ((float) heightOfTheScore / 2)
        );

        arcFrameRectF = new RectF(
                mainRect.centerX() - ((float) widthOfTheScore / 2),
                mainRect.centerY() - ((float) heightOfTheScore / 2),
                mainRect.centerX() + ((float) widthOfTheScore / 2),
                mainRect.centerY() + ((float) heightOfTheScore / 2) - convertDpToPixels(12, getContext())
        );

        arcRectF = new RectF(
                arcFrameRectF.left + (progressbarWidth/2),
                arcFrameRectF.top + (progressbarWidth/2),
                arcFrameRectF.right - (progressbarWidth/2),
                arcFrameRectF.bottom
        );

        mMainRectPaint = new Paint();
        mMainRectPaint.setColor(Color.WHITE);
        mMainRectPaint.setStyle(Paint.Style.FILL);
        mMainRectPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        emptyScorePaint = new Paint();
        emptyScorePaint.setColor(Color.GREEN);
        emptyScorePaint.setStyle(Paint.Style.FILL);
        emptyScorePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        emptyArcPaint = new Paint();
        emptyArcPaint.setColor(Color.GRAY);
        emptyArcPaint.setStyle(Paint.Style.FILL);
        emptyArcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mMainRectStrokePaint = new Paint();
        mMainRectStrokePaint.setColor(Color.parseColor("#E7E8F1"));
        mMainRectStrokePaint.setAntiAlias(true);
        mMainRectStrokePaint.setStyle(Paint.Style.STROKE);
        mMainRectStrokePaint.setStrokeWidth(1);

        arcPaint = new Paint();
        arcPaint.setColor(mResources.getColor(android.R.color.white));
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(convertDpToPixels(25, getContext()));
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        backGroundPaint = new Paint();
        backGroundPaint.setStrokeWidth(progressbarWidth);
        backGroundPaint.setAntiAlias(true);
        backGroundPaint.setStyle(Paint.Style.STROKE);
        // backGroundPaint.setStrokeCap(Paint.Cap.ROUND);
        backGroundPaint.setColor(backgroundProgressColor);

        foreGroundPaint = new Paint();
        foreGroundPaint.setStrokeWidth(progressbarWidth);
        foreGroundPaint.setAntiAlias(true);
        foreGroundPaint.setStyle(Paint.Style.STROKE);
        // foreGroundPaint.setStrokeCap(Paint.Cap.ROUND);
        foreGroundPaint.setColor(foregroundProgressColor);

        mCenterX = mainRect.centerX();
        mCenterY = mainRect.centerY();
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mainrectf, 5, 5, mMainRectPaint);
        canvas.drawRoundRect(mainrectf, 5, 5, mMainRectStrokePaint);
        canvas.drawRect(scoreRectF, emptyScorePaint);
        canvas.drawRect(arcFrameRectF, emptyArcPaint);
        setPercentIndicatorText(canvas);
        drawArc(canvas);
        invalidate();
    }

    public void drawArc(Canvas canvas) {
        // canvas.drawRect(arcRectF, foreGroundPaint);

        float radius = Math.min(arcRectF.width(), arcRectF.height());

        // Calculate the left, top, right, and bottom coordinates of the bounding rectangle for the arc
        float left = arcRectF.centerX() - radius;
        float top = arcRectF.top;
        float right = arcRectF.centerX() + radius;
        float bottom = arcRectF.bottom + radius;

        // Draw the semicircle arc
        canvas.save();
        canvas.drawArc(left, top, right, bottom, startAngle, swipeAngle, false, backGroundPaint);
        canvas.drawArc(left, top, right, bottom, startAngle, scoreInSweepAngle, false, foreGroundPaint);
        canvas.restore();
    }

    public void setPercentIndicatorText(Canvas canvas) {
        canvas.drawText("0%", scoreRectF.left, scoreRectF.bottom, scoreTextPaint);
        canvas.drawText("100%", scoreRectF.right-scoreTextPaint.measureText("100%"), scoreRectF.bottom, scoreTextPaint);
    }

    private int convertDpToPixels(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private float convertDpToPixelsFloat(float dp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public void progressValidation(float score) {
        if (score >= mTotalScore)
            this.scoreInSweepAngle = 180f;
        else
            this.scoreInSweepAngle = getScore(score);
        Log.e("ProgressScoreProgressView",this.scoreInSweepAngle +"  "+score);
        invalidate();
    }

    public float getScore(float score) {
        float dividedValue = score / mTotalScore;
        mPercentage = dividedValue * 100;
        return dividedValue * 180;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}