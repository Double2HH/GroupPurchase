package www.huangheng.site.grouppurchase.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ClassifyInfo;
import www.huangheng.site.grouppurchase.entity.HomepageStaticInfo;

/**
 * 首页商品分类设配器
 */

public class HomepageClassifyAdapter extends BaseAdapter {

    private Context context;
    private List<ClassifyInfo> mClassifyInfo;

    public HomepageClassifyAdapter(Context context, List<ClassifyInfo> mClassifyInfo) {
        this.context = context;
        this.mClassifyInfo = mClassifyInfo;
    }

    @Override
    public int getCount() {
        return mClassifyInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder commonViewHolder = CommonViewHolder.get(context, convertView, R.layout.item_homepage_viewpager_classify_item, parent);
        SimpleDraweeView draweeView = commonViewHolder.getView(R.id.classify_gridview_draweeview);
        TextView textView = commonViewHolder.getView(R.id.classify_gridview_textview);
        if (HomepageStaticInfo.isSucceed) {
            draweeView.setImageURI(mClassifyInfo.get(position).getClassifyImageUrl());
        }
        textView.setText(mClassifyInfo.get(position).getClassifyName());
        return commonViewHolder.getConvertView();
    }


}
