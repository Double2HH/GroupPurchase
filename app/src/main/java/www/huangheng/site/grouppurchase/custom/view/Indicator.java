package www.huangheng.site.grouppurchase.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import www.huangheng.site.grouppurchase.R;

/**
 * 广告条指示器
 */

public class Indicator extends View {

    private Paint mForePaint;
    private Paint mBackPaint;

    private float mOffsetFraction;  //0.0~1.0
    private int mPosition;  //ViewPager的position
    private float mOffsetX;  //第一个圆的圆心X坐标
    private float mOffsetY;  //圆心Y坐标

    private int mNumber = 3;    //圆的默认数目
    private int mRadius = 8;    //圆的默认半径
    private int mForeColor; //前景颜色
    private int mBackColor; //背景颜色


    public Indicator(Context context) {
        this(context, null);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Indicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        mNumber = typedArray.getInteger(R.styleable.Indicator_indicator_number, 3);
        mRadius = typedArray.getInteger(R.styleable.Indicator_indicator_radius, 10);
        mForeColor = typedArray.getColor(R.styleable.Indicator_indicator_forecolor, 0);
        mBackColor = typedArray.getColor(R.styleable.Indicator_indicator_backcolor, 0);
        typedArray.recycle();

        initialize();
    }

    private void initialize() {
        mForePaint = new Paint();
        mForePaint.setAntiAlias(true);
        mForePaint.setStyle(Paint.Style.FILL);
        mForePaint.setColor(mForeColor);
        mForePaint.setStrokeWidth(2);

        mBackPaint = new Paint();
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setColor(mBackColor);
        mBackPaint.setStrokeWidth(2);

    }


    public void setOffset(int position, float offset) {
        mPosition = position;
        mOffsetFraction = offset;
        //处理最后一个圆与第一个圆的交接
        if (position < (mNumber - 1)) {
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mOffsetX = (getMeasuredWidth() - (mNumber * mRadius * 2 + (mNumber - 1) * 10)) / 2 + mRadius;
        mOffsetY = (getMeasuredHeight() - Math.min(mRadius * 2, getMeasuredHeight())) / 2 + Math.min(mRadius, getMeasuredHeight() / 2);

        for (int i = 0; i < mNumber; i++) {
            canvas.drawCircle(mOffsetX + i * (mRadius * 2 + 10), mOffsetY, Math.min(mRadius, getMeasuredHeight() / 2), mBackPaint);
        }
        canvas.drawCircle(mOffsetX + (mPosition + mOffsetFraction) * (mRadius * 2 + 10), mOffsetY, Math.min(mRadius, getMeasuredHeight() / 2), mForePaint);

    }
}
