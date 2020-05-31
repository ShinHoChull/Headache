package com.m2comm.headache.module;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CalendarCirView extends View {

    final static int LINE = 1;
    final static int CIRCLE = 2;
    int curShape = LINE;
    Context context;


    public CalendarCirView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        switch (this.curShape) {
            case LINE:
                canvas.drawLine(10, 10, 100, 100, paint);
                break;
            case CIRCLE:
                canvas.drawCircle(100, 100, 50, paint);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("MainActivity", event.toString());
        return super.onTouchEvent(event);
    }

}
