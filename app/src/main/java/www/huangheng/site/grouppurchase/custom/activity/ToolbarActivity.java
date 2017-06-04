package www.huangheng.site.grouppurchase.custom.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import www.huangheng.site.grouppurchase.helper.ToolbarHelper;

/**
 * ToolbarActivity基类
 */

public abstract class ToolbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        ToolbarHelper toolbarHelper = new ToolbarHelper(this, layoutResID);
        super.setContentView(toolbarHelper.getContentView());
        Toolbar toolbar = toolbarHelper.getToolbar();
        onCreateCustomToolbar(toolbar);
        setSupportActionBar(toolbar);
    }

    public abstract void onCreateCustomToolbar(Toolbar toolbar);

}
