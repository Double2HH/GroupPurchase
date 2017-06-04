package www.huangheng.site.grouppurchase.other.citylist.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.CommonViewHolder;
import www.huangheng.site.grouppurchase.other.citylist.bean.RegionInfo;


/**
 * 头部热门城市的适配器
 */

public class CityGridViewAdapter extends CityBaseAdapter<RegionInfo, GridView> {

    private Context mContext;
    private List<RegionInfo> list;

    public CityGridViewAdapter(Context context, List<RegionInfo> list) {
        super(context, list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext, convertView, R.layout.item_citylist_hotcity, parent);
        TextView cityname = commonViewHolder.getView(R.id.item_citylist_hotcity_name);
        RegionInfo info = list.get(position);
        cityname.setText(info.getName());
        return commonViewHolder.getConvertView();
    }

}