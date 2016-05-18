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
 * Date: 2016-05-16
 * Time: 15:54
 * FIXME
 */
public class MountainView extends View {

    private static final int STYLE_1 = 0;
    private static final int STYLE_2 = 1;
    private static final int STYLE_3 = 2;

    private float mScaleW, mScaleH;
    private int mWidth, mHeight;
    private Paint mPaint;
    private int mCenterX, mCenterY;

    private int mStyle;
    private int mColor;

    private PointF[] mData = new PointF[5];  //5个数据点
    private PointF[] mCtrl = new PointF[7]; //7个控制点

    public MountainView(Context context) {
        this(context, null);
    }

    public MountainView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MountainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MountainView);

        mStyle = typedArray.getInteger(R.styleable.MountainView_style, 0);
        mColor = typedArray.getColor(R.styleable.MountainView_android_color, getResources().getColor(R.color
                .mountain_1));

        typedArray.recycle();
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MountainView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MountainView);

        mStyle = typedArray.getInteger(R.styleable.MountainView_style, 0);
        mColor = typedArray.getColor(R.styleable.MountainView_android_color, getResources().getColor(R.color
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
        mScaleW = w / 120;
        mScaleH = h / 30;
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w - 80;
        mCenterY = h / 2;

        switch (mStyle) {
            case STYLE_1:
                mData[0].set(0, 13);
                mData[1].set(24, 4);
                mData[2].set(50, 10);
                mData[3].set(93, 14);
                mData[4].set(mWidth, 11);

                mCtrl[0].set(16, 0);
                mCtrl[1].set(30, 8);
                mCtrl[2].set(32, 18);
                mCtrl[3].set(66, 3);
                mCtrl[4].set(90, 13);
                mCtrl[5].set(96, 17);
                mCtrl[6].set(114, 14);
                break;
            case STYLE_2:
                mData[0].set(0, 4.51f);
                mData[1].set(24.51f, 7.67f);
                mData[2].set(50.94f, 9.66f);
                mData[3].set(80.76f, 11.59f);
                mData[4].set(mWidth, 4.84f);

                mCtrl[0].set(8.5f, 13.5f);
                mCtrl[1].set(32, 5);
                mCtrl[2].set(44, 4);
                mCtrl[3].set(53, 11);
                mCtrl[4].set(63, 24);
                mCtrl[5].set(105, -3);
                mCtrl[6].set(111, 0);
                break;
            case STYLE_3:
                mData[0].set(0, 22);
                mData[1].set(23.84f, 22.01f);
                mData[2].set(52, 8);
                mData[3].set(68, 11);
                mData[4].set(mWidth, 26);

                mCtrl[0].set(10, -7.5f);
                mCtrl[1].set(27, 29.5f);
                mCtrl[2].set(42, 6.5f);
                mCtrl[3].set(56, 10);
                mCtrl[4].set(62, 23.9f);
                mCtrl[5].set(81, -11f);
                mCtrl[6].set(111, 22);
                break;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(mCenterX, mCenterY); // 将坐标系移动到画布中央

        Path path = new Path();
        path.moveTo(0, mHeight);
        path.lineTo(mData[0].x, mData[0].y * mScaleH);
        path.quadTo(mCtrl[0].x * mScaleW, mCtrl[0].y * mScaleH, mData[1].x * mScaleW, mData[1].y * mScaleH);
        path.cubicTo(mCtrl[1].x * mScaleW, mCtrl[1].y * mScaleH, mCtrl[2].x * mScaleW, mCtrl[2].y * mScaleH,
                mData[2].x * mScaleW, mData[2].y * mScaleH);
        path.cubicTo(mCtrl[3].x * mScaleW, mCtrl[3].y * mScaleH, mCtrl[4].x * mScaleW, mCtrl[4].y * mScaleH, mData[3]
                .x * mScaleW, mData[3].y * mScaleH);
        path.cubicTo(mCtrl[5].x * mScaleW, mCtrl[5].y * mScaleH, mCtrl[6].x * mScaleW, mCtrl[6].y * mScaleH, mData[4].x,
                mData[4].y * mScaleH);
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
