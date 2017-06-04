package www.huangheng.site.grouppurchase.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用设配器
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    List<T> mDataList = new ArrayList<>();

    public CommonAdapter(List<T> mDataList) {
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
