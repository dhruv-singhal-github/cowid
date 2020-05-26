package com.example.cowid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

public class CircularCompletionView extends View {

    private int completionPercent=0;
    private Paint paint = new Paint();
    private int radius = 100;
    private int strokeSize = 20;
    private int textSize = 10;
    private int diameter = radius * 2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircularCompletionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CircularCompletionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public CircularCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CircularCompletionView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public void setCompletionPercentage(int completion){
        completionPercent = completion;//size should change for different Resolution screens
        invalidate();
    }

    public void setTextSize(int size){
        textSize = size;//size should change for different Resolution screens
        invalidate();
    }

    public void setStrokeSize(int size){
        strokeSize = size;//size should change for different Resolution screens
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        if(width > height){
            width = height;
        }
        else{
            height = width;
        }

        diameter = width;
        radius = diameter/2;

        int newWidthMeasureSpec=MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int newHeightMeasureSpec=MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);

        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.parseColor("#dedede"));  // circle stroke color- grey
        paint.setStrokeWidth(strokeSize);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(radius, radius, radius-10, paint);

        paint.setColor(Color.parseColor("#04B404"));  // circle stroke color(indicating completion Percentage) - green
        paint.setStrokeWidth(strokeSize);
        paint.setStyle(Paint.Style.FILL);

        final RectF oval = new RectF();
        paint.setStyle(Paint.Style.STROKE);
        oval.set(10,10,(diameter)-10,(diameter)-10);

        canvas.drawArc(oval, 270, ((completionPercent*360)/100), false, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.parseColor("#282828"));  // text color - dark grey

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(textSize);

        canvas.drawText(completionPercent + "%", radius, radius+(paint.getTextSize()/2), paint);

    }

}
