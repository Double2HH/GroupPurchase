package www.huangheng.site.grouppurchase.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通用ViewHolder
 */

public class CommonViewHolder {
    //效率高、key值只能是integer
    private SparseArray<View> mSparseArray;
    private View mConvertView;

    private CommonViewHolder(Context context, int layoutID, ViewGroup parent) {
        mSparseArray = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutID, parent, false);
        mConvertView.setTag(this);
    }

    public static CommonViewHolder get(Context context, View convertView, int layoutID, ViewGroup parent) {
        if (convertView == null) {
            return new CommonViewHolder(context, layoutID, parent);
        } else {
            return (CommonViewHolder) convertView.getTag();
        }
    }


    public <T extends View> T getView(int viewID) {
        View view = mSparseArray.get(viewID);
        if (view == null) {
            view = mConvertView.findViewById(viewID);
            mSparseArray.put(viewID, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


}
