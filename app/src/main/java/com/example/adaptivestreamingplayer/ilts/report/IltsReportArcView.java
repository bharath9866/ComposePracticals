package com.example.adaptivestreamingplayer.ilts.report;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.adaptivestreamingplayer.R;

public class IltsReportArcView extends View {

    Resources resources;
    Rect main_rect;
    RectF arcRectF;

    Paint mainRectPaint;
    Paint arcPaint;
    DisplayMetrics displayMetrics;


    int width, height;
    int mCenterX, mCenterY;

    public IltsReportArcView(Context context) {
        super(context);
        init(null);
    }

    public IltsReportArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public IltsReportArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public IltsReportArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    public void init(AttributeSet attributeSet) {
        resources = getResources();

        displayMetrics = resources.getDisplayMetrics();

        mainRectPaint = new Paint();
        mainRectPaint.setColor(resources.getColor(android.R.color.black));
        mainRectPaint.setStyle(Paint.Style.FILL);
        mainRectPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        arcPaint = new Paint();
        arcPaint.setColor(resources.getColor(android.R.color.white));
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(resources.getDimension(R.dimen.dimen_25dp));
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        main_rect = new Rect(0, 0, (int) resources.getDimension(R.dimen.dimen_350dp), (int) resources.getDimension(R.dimen.dimen_192dp));
        arcRectF = new RectF(0, 0, (int) resources.getDimension(R.dimen.dimen_250dp), (int) resources.getDimension(R.dimen.dimen_144dp));
        mCenterX = main_rect.centerX();
        mCenterY = main_rect.centerY();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(main_rect, mainRectPaint);

        canvas.save();
        canvas.translate((float) mCenterX /2, (float) mCenterY /2);
        canvas.drawArc(arcRectF, 180f, 180, false, arcPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
