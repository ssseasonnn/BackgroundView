package app.season.backgroundview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Season(ssseasonnn@gmail.com)
 * Date: 2016-05-17
 * Time: 16:54
 * FIXME
 */
public class BackgroundView extends RelativeLayout {

    private LeafView mLeaf1, mLeaf2, mLeaf3, mLeaf4;
    private CloudView mCloud1, mCloud2, mCloud3;

    private PointF[] mData = new PointF[5];  //5个数据点
    private PointF[] mCtrl = new PointF[6]; //6个控制点

    private List<Animator> mAnimators = new ArrayList<>();

    public BackgroundView(Context context) {
        this(context, null);
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BackgroundView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * 停止动画
     */
    public void stopAnimation() {
        for (Animator animator : mAnimators) {
            animator.cancel();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initBézierCurve(w);

        /**
         *可根据不同需求设置不同差值器，产生不同的叶子飘动效果
         */
        mAnimators.add(startLeafAnimation(mLeaf1, w, 200, 5000, 0, new AccelerateInterpolator()));
        mAnimators.add(startLeafAnimation(mLeaf2, w, 200, 5000, 3000, new AccelerateDecelerateInterpolator()));
        mAnimators.add(startLeafAnimation(mLeaf3, w, 200, 5000, 2000, new OvershootInterpolator()));
        mAnimators.add(startLeafAnimation(mLeaf4, w, 200, 5000, 4000, new DecelerateInterpolator()));

        mAnimators.add(startCloudAnimation(mCloud1, w, 30000, 0));
        mAnimators.add(startCloudAnimation(mCloud2, w, 30000, 4000));
        mAnimators.add(startCloudAnimation(mCloud3, w, 30000, 8000));
    }


    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.background, this, true);
        mLeaf1 = (LeafView) findViewById(R.id.leaf1);
        mLeaf2 = (LeafView) findViewById(R.id.leaf2);
        mLeaf3 = (LeafView) findViewById(R.id.leaf3);
        mLeaf4 = (LeafView) findViewById(R.id.leaf4);

        mCloud1 = (CloudView) findViewById(R.id.cloud1);
        mCloud2 = (CloudView) findViewById(R.id.cloud2);
        mCloud3 = (CloudView) findViewById(R.id.cloud3);


    }

    /**
     * 初始化贝塞尔曲线数据点和控制点
     *
     * @param width 宽度
     */
    private void initBézierCurve(int width) {

        for (int i = 0; i < mData.length; i++) {
            mData[i] = new PointF();
        }
        for (int i = 0; i < mCtrl.length; i++) {
            mCtrl[i] = new PointF();
        }

        mData[0].set(width, 10);
        mData[1].set(81, 9);
        mData[2].set(54, 9);
        mData[3].set(27, 9);
        mData[4].set(-10, 10);

        mCtrl[0].set(90, 5);
        mCtrl[1].set(72, 13.5f);
        mCtrl[2].set(59, 12);
        mCtrl[3].set(49, 6);
        mCtrl[4].set(37, 5);
        mCtrl[5].set(16.5f, 14);
    }

    /**
     * 开启叶子动画
     *
     * @param view         哪一个叶子
     * @param width        比例
     * @param height       比例
     * @param duration     动画持续时间
     * @param startDelay   动画延迟开始时间
     * @param interpolator 动画差值器
     * @return AnimatorSet
     */
    private AnimatorSet startLeafAnimation(final View view, int width, int height, long duration, long startDelay,
                                           Interpolator interpolator) {
        /**
         * 缩放比例
         */
        float mScaleW, mScaleH;

        mScaleW = width / 100;
        mScaleH = height / 20;

        Path path = new Path();
        path.moveTo(mData[0].x, mData[0].y * mScaleH);
        path.quadTo(mCtrl[0].x * mScaleW, mCtrl[0].y * mScaleH, mData[1].x * mScaleW, mData[1].y * mScaleH);
        path.cubicTo(mCtrl[1].x * mScaleW, mCtrl[1].y * mScaleH, mCtrl[2].x * mScaleW, mCtrl[2].y * mScaleH,
                mData[2].x * mScaleW, mData[2].y * mScaleH);
        path.cubicTo(mCtrl[3].x * mScaleW, mCtrl[3].y * mScaleH, mCtrl[4].x * mScaleW, mCtrl[4].y * mScaleH, mData[3]
                .x * mScaleW, mData[3].y * mScaleH);
        path.quadTo(mCtrl[5].x * mScaleW, mCtrl[5].y * mScaleH, mData[4].x * mScaleW, mData[4].y * mScaleH);


//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "x", "y", path);
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        objectAnimator.setRepeatMode(ValueAnimator.INFINITE);
//        objectAnimator.setDuration(5000);
//        objectAnimator.start();

        /**
         * 旋转动画
         */
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 360);
        rotationAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnimator.setRepeatMode(ValueAnimator.INFINITE);
        rotationAnimator.setDuration(duration);


        /**
         * 贝塞尔曲线动画
         */
        final PathMeasure mPathMeasure = new PathMeasure(path, false);
        final float[] pointF = new float[2];

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(duration);
        valueAnimator.setStartDelay(startDelay);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到pointF
                mPathMeasure.getPosTan(value, pointF, null);
                view.setX(pointF[0]);
                view.setTranslationY(pointF[1]);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator, rotationAnimator);
        animatorSet.start();
        return animatorSet;
    }

    private ObjectAnimator startCloudAnimation(final View view, final float width, final long duration, final long
            startDelay) {
        /**
         * 平移动画
         */
        ObjectAnimator translationAnimation = ObjectAnimator.ofFloat(view, "x", width, -width);
        translationAnimation.setDuration(duration);
        translationAnimation.setStartDelay(startDelay);
        translationAnimation.setRepeatCount(ValueAnimator.INFINITE);
        translationAnimation.setRepeatMode(ValueAnimator.INFINITE);

        translationAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.VISIBLE);
            }
        });
        translationAnimation.start();
        return translationAnimation;
    }
}
