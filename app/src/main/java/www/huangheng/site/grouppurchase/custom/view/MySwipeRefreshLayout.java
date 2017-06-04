package www.huangheng.site.grouppurchase.custom.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 解决滑动冲突
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout{
    private float startX;
    private float startY;

    //记录ViewPager是否拖拽的标记
    private boolean isViewPagerDragger;
    private int touchSlop;


    public MySwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                isViewPagerDragger = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (isViewPagerDragger) {
                    return false;
                }

                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);

                if (distanceX > touchSlop && distanceX > distanceY) {
                    isViewPagerDragger = true;
                    return false;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isViewPagerDragger = false;
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


}
