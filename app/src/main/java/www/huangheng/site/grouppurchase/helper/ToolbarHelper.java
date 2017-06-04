package www.huangheng.site.grouppurchase.helper;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Toolbar帮助类
 */

public class ToolbarHelper {

    private Context mContext;
    private int layoutResID;
    private Toolbar mToolbar;
    private LinearLayout mContentView;
    private View mUserView;

    public ToolbarHelper(Context context, int layoutResID) {
        this.mContext = context;
        this.layoutResID = layoutResID;
        initContentView();

    }

    private void initContentView() {
        mContentView = new LinearLayout(mContext);
        mContentView.setOrientation(LinearLayout.VERTICAL);
        mContentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
        initUserView();
    }

    private void initUserView() {
      LayoutInflater.from(mContext).inflate(layoutResID, mContentView);
    }


    private void initToolbar() {
        mToolbar = new Toolbar(mContext);
        mToolbar.setLayoutParams(new Toolbar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContentView.addView(mToolbar);
    }


    public Toolbar getToolbar() {
        return mToolbar;
    }


    public LinearLayout getContentView() {
        return mContentView;
    }
}
