package www.huangheng.site.grouppurchase.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import www.huangheng.site.grouppurchase.fragment.PaidedAllFragment;
import www.huangheng.site.grouppurchase.fragment.PaidedLogisticsFragment;
import www.huangheng.site.grouppurchase.fragment.PaidedReimburseFragment;
import www.huangheng.site.grouppurchase.fragment.PaidedUncommentedFragment;

/**
 * FragmentPagerAdapter
 */

public class PaidedFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();

    public PaidedFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new PaidedAllFragment());
        mFragments.add(new PaidedUncommentedFragment());
        mFragments.add(new PaidedReimburseFragment());
        mFragments.add(new PaidedLogisticsFragment());
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
