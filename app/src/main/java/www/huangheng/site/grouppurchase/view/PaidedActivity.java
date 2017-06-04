package www.huangheng.site.grouppurchase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.PaidedFragmentPagerAdapter;

/**
 * 已付款订单
 */

public class PaidedActivity extends AppCompatActivity {

    @BindView(R.id.paided_tablayout)
    TabLayout mPaidedTablayout;
    @BindView(R.id.paided_viewpager)
    ViewPager mPaidedViewpager;

    TabLayout.Tab all;
    TabLayout.Tab uncommented;
    TabLayout.Tab reimburse;
    TabLayout.Tab logistics;

    int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paided);
        ButterKnife.bind(this);
        initializeTab();
        initializeViewPager();
        index = getIntent().getIntExtra("WHICH_ONE_TO_BE_SELECT", 0);
        if (index == 1) {
            uncommented.select();
        }
    }


    private void initializeTab() {

        all = mPaidedTablayout.newTab();
        uncommented = mPaidedTablayout.newTab();
        reimburse = mPaidedTablayout.newTab();
        logistics = mPaidedTablayout.newTab();

        mPaidedTablayout.addTab(all.setText("全部"));
        mPaidedTablayout.addTab(uncommented.setText("待评价"));
        mPaidedTablayout.addTab(reimburse.setText("退款单"));
        mPaidedTablayout.addTab(logistics.setText("物流单"));

        mPaidedTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPaidedViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void initializeViewPager() {
        mPaidedViewpager.setAdapter(new PaidedFragmentPagerAdapter(getSupportFragmentManager()));

        mPaidedViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    all.select();
                } else if (position == 1) {
                    uncommented.select();
                } else if (position == 2) {
                    reimburse.select();
                } else if (position == 3) {
                    logistics.select();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @OnClick(R.id.paided_titlebar_back)
    public void onViewClicked() {
        finish();
    }

}
