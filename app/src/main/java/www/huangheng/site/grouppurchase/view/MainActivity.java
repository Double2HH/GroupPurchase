package www.huangheng.site.grouppurchase.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.MainFragmentPagerAdapter;
import www.huangheng.site.grouppurchase.custom.view.MyViewPager;


/**
 * 主Activity
 */

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.main_myviewpager)
    MyViewPager mMainMyviewpager;


    //四个底部标签
    @BindView(R.id.main_tab_homepage)
    LinearLayout mMainTabHomepage;
    @BindView(R.id.main_tab_nearby)
    LinearLayout mMainTabNearby;
    @BindView(R.id.main_tab_mine)
    LinearLayout mMainTabMine;
    @BindView(R.id.main_tab_more)
    LinearLayout mMainTabMore;


    //当前被选中的标签的索引
    public static int indexOfTab = 0;

    //最后一次点击返回键的时间
    public long lastBackTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }


    private void initialize() {
        mMainMyviewpager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager()));
        mMainMyviewpager.setOffscreenPageLimit(3);
        mMainMyviewpager.addOnPageChangeListener(this);
        changeTabState(0);
    }


    /**
     * 底部标签点击事件
     *
     * @param view 视图
     */
    @OnClick({R.id.main_tab_homepage, R.id.main_tab_nearby, R.id.main_tab_mine, R.id.main_tab_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_tab_homepage:
                if (indexOfTab != 0) {
                    changeTabState(0);
                }
                break;

            case R.id.main_tab_nearby:
                if (indexOfTab != 1) {
                    changeTabState(1);
                }
                break;

            case R.id.main_tab_mine:
                if (indexOfTab != 2) {
                    changeTabState(2);
                }
                break;

            case R.id.main_tab_more:
                if (indexOfTab != 3) {
                    changeTabState(3);
                }
                break;

        }
    }


    /**
     * 改变被选中标签的状态
     *
     * @param index 索引
     */
    private void changeTabState(int index) {
        indexOfTab = index;
        switch (index) {
            case 0:
                mMainTabHomepage.setSelected(true);
                mMainTabNearby.setSelected(false);
                mMainTabMine.setSelected(false);
                mMainTabMore.setSelected(false);
                mMainMyviewpager.setCurrentItem(0, false);
                break;

            case 1:
                mMainTabHomepage.setSelected(false);
                mMainTabNearby.setSelected(true);
                mMainTabMine.setSelected(false);
                mMainTabMore.setSelected(false);
                mMainMyviewpager.setCurrentItem(1, false);
                break;

            case 2:
                mMainTabHomepage.setSelected(false);
                mMainTabNearby.setSelected(false);
                mMainTabMine.setSelected(true);
                mMainTabMore.setSelected(false);
                mMainMyviewpager.setCurrentItem(2, false);
                break;

            case 3:
                mMainTabHomepage.setSelected(false);
                mMainTabNearby.setSelected(false);
                mMainTabMine.setSelected(false);
                mMainTabMore.setSelected(true);
                mMainMyviewpager.setCurrentItem(3, false);
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long time = System.currentTimeMillis() - lastBackTime;
                lastBackTime = System.currentTimeMillis();
                if (time >= 2000) {
                    Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    return false;
                }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (indexOfTab != position) {
            changeTabState(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

