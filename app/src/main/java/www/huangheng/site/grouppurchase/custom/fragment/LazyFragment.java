package www.huangheng.site.grouppurchase.custom.fragment;

import android.support.v4.app.Fragment;


public abstract class LazyFragment extends Fragment {

    protected boolean isVisible = false;
    protected boolean isLoadData = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }

    private void onVisible() {
        if (!isLoadData) {
            lazyLoad();
        }
    }

    /**
     * 懒加载
     */
    protected abstract void lazyLoad();

}
