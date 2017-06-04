package www.huangheng.site.grouppurchase.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import www.huangheng.site.grouppurchase.fragment.TabHomepageFragment;
import www.huangheng.site.grouppurchase.fragment.TabMineFragment;
import www.huangheng.site.grouppurchase.fragment.TabMoreFragment;
import www.huangheng.site.grouppurchase.fragment.TabNearbyFragment;

/**
 * FragmentPagerAdapter
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new TabHomepageFragment());
        mFragments.add(new TabNearbyFragment());
        mFragments.add(new TabMineFragment());
        mFragments.add(new TabMoreFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
