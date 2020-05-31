package com.m2comm.headache.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.m2comm.headache.R;


public class CirView extends RelativeLayout {

    private int x, y, w, h;
    GradientDrawable gd1;
    public CirView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context, attrs);
    }

    private void initView(Context c, AttributeSet a) {
        TypedArray t = c.obtainStyledAttributes(a, R.styleable.CirView);

        this.w = t.getInt(R.styleable.CirView_w, 0);
        this.h = t.getInt(R.styleable.CirView_w, 0);
        int color = t.getColor(R.styleable.CirView_mixcolor, 0);

        this.gd1 = new GradientDrawable();
        this.gd1.setShape(GradientDrawable.RECTANGLE);
//        this.gd1.setColor();
//
        this.gd1.setStroke(2, Color.TRANSPARENT);

        this.setBackground(gd1);
    }

    public void setColor (int color) {
        this.gd1.setColor(color);
    }

    public void setRadius(float radius) {
        this.gd1.setCornerRadius(radius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.w > 0)
            widthMeasureSpec = w;
        if (this.h > 0)
            heightMeasureSpec = h;
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
}

