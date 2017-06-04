package www.huangheng.site.grouppurchase.listener;

import android.support.v4.view.ViewPager;

import www.huangheng.site.grouppurchase.custom.view.Indicator;

/**
 * 广告条ViewPager监听器
 */

public class ViewPagerChangeListener implements ViewPager.OnPageChangeListener {

    private Indicator mIndicator;

    public ViewPagerChangeListener(Indicator indicator) {
        this.mIndicator = indicator;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mIndicator.setOffset(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
