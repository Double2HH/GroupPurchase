package www.huangheng.site.grouppurchase.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 商品详情滚动视图
 */
public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    public interface ScrollViewListener{
        void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                             int oldx, int oldy);
    }


    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener){
        this.scrollViewListener = scrollViewListener;

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null){
            scrollViewListener.onScrollChanged(this,l,t,oldl,oldt);
        }

    }
}
