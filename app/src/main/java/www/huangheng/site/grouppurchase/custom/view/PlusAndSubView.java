package www.huangheng.site.grouppurchase.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 订单加减
 */

public class PlusAndSubView extends LinearLayout {


    public PlusAndSubView(Context context) {
        this(context, null);
    }

    public PlusAndSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlusAndSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();

    }

    private void initialize() {
        setOrientation(LinearLayout.HORIZONTAL);
    }




}
