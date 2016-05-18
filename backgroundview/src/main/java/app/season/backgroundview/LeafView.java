package app.season.backgroundview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * User: Season(ssseasonnn@gmail.com)
 * Date: 2016-05-17
 * Time: 11:40
 * FIXME
 */
public class LeafView extends View {
    private int mColor;
    private int mWidth, mHeight;
    private Paint mPaint;
    private float mPercentX, mPercentY;

    private PointF[] mData = new PointF[5];  //5个数据点
    private PointF[] mCtrl = new PointF[2]; //2个控制点

    public LeafView(Context context) {
        this(context, null);
    }

    public LeafView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeafView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TreeView);

        mColor = typedArray.getColor(R.styleable.TreeView_android_color, getResources().getColor(R.color
                .mountain_1));

        typedArray.recycle();

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LeafView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        for (int i = 0; i < mData.length; i++) {
            mData[i] = new PointF();
        }
        for (int i = 0; i < mCtrl.length; i++) {
            mCtrl[i] = new PointF();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mPercentX = w / 50;
        mPercentY = h / 50;
        super.onSizeChanged(w, h, oldw, oldh);

        mData[0].set(w / 2 - 2, 45);
        mData[1].set(w / 2 - 2, 35);
        mData[2].set(w / 2, 8);
        mData[3].set(w / 2 + 2, 35);
        mData[4].set(w / 2 + 2, 45);

        mCtrl[0].set(9, 27);
        mCtrl[1].set(41, 27);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(mData[0].x, mData[0].y * mPercentY);
        path.lineTo(mData[1].x, mData[1].y * mPercentY);
        path.quadTo(mCtrl[0].x * mPercentX, mCtrl[0].y * mPercentY, mData[2].x, mData[2].y * mPercentY);
        path.quadTo(mCtrl[1].x * mPercentX, mCtrl[1].y * mPercentY, mData[3].x, mData[3].y * mPercentY);
        path.lineTo(mData[4].x, mData[4].y * mPercentY);
        path.lineTo(mData[0].x, mData[0].y * mPercentY);
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
