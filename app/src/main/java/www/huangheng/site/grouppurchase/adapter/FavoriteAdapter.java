package www.huangheng.site.grouppurchase.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.FavoriteInfo;

/**
 * 收藏设配器
 */
public class FavoriteAdapter  extends CommonAdapter<FavoriteInfo>{

    private Context mContext;
    public FavoriteAdapter(Context context, List<FavoriteInfo> mDataList) {
        super(mDataList);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder commonViewHolder =CommonViewHolder.get(mContext, convertView,R.layout.item_homepage_like,parent);
        return null;
    }
}
