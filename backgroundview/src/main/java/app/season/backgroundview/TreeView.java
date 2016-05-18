package app.season.backgroundview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * User: Season(ssseasonnn@gmail.com)
 * Date: 2016-05-17
 * Time: 10:36
 * FIXME
 */
public class TreeView extends View {

    private int mColor;
    private int mWidth, mHeight;
    private Paint mPaint;

    public TreeView(Context context) {
        this(context, null);
    }

    public TreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TreeView);

        mColor = typedArray.getColor(R.styleable.TreeView_android_color, getResources().getColor(R.color
                .mountain_1));

        typedArray.recycle();
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TreeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TreeView);

        mColor = typedArray.getColor(R.styleable.TreeView_android_color, getResources().getColor(R.color
                .mountain_1));

        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        // 绘制贝塞尔曲线
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(60);
        mPaint.setPathEffect(new CornerPathEffect(4));

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(0, mHeight);
        path.lineTo(mWidth / 2, 0);
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
}
