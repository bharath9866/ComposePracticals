package com.example.adaptivestreamingplayer.ilts.report;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MySemiCircleView extends View {

    private Paint paint;
    private RectF rectF;

    public MySemiCircleView(Context context) {
        super(context);
        init();
    }

    public MySemiCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySemiCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MySemiCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL); // Filling the semi-circle
        //paint.setStyle(Paint.Style.STROKE); // Uncomment this line if you want just the outline
        paint.setAntiAlias(true);

        // Define the bounds of the rectangle (left, top, right, bottom)
        rectF = new RectF(100, 100, 600, 600);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw a semi-circle (180-degree arc) within the specified rectangle bounds
        canvas.drawArc(rectF, 180, 180, true, paint);
    }
}
