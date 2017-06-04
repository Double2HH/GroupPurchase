package www.huangheng.site.grouppurchase.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.NearbyProductInfo;
import www.huangheng.site.grouppurchase.utils.BaiduMapUtils;
import www.huangheng.site.grouppurchase.utils.SharedPreferencesUtils;

/**
 * 附近商品、搜索商品
 */

public class NearbyProductAdapter extends BaseAdapter {

    private Context mContext;
    private List<NearbyProductInfo.ResultsBean> results;
    private boolean isEmpty = false;

    private Map<String, String> geoLocationInfo;

    public NearbyProductAdapter(Context context, List<NearbyProductInfo.ResultsBean> results) {
        this(context);
        this.results = results;

    }

    public NearbyProductAdapter(Context mContext) {
        this.mContext = mContext;
        results = new ArrayList<>();
        geoLocationInfo = SharedPreferencesUtils.getInstance().getGeoLocationFromSP(mContext);
    }

    @Override
    public int getCount() {
        if (results.size() == 0) {
            isEmpty = true;
            return 1;
        } else {
            isEmpty = false;
            return results.size();
        }
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

        if (isEmpty) {
            CommonViewHolder emptyViewHolder = CommonViewHolder.get(mContext, convertView, R.layout.item_homepage_like_empty, parent);
            return emptyViewHolder.getConvertView();
        }

        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext, convertView, R.layout.item_nearby_nearby, parent);

        SimpleDraweeView image = commonViewHolder.getView(R.id.item_nearby_listview_draweeview);
        image.setImageURI(results.get(position).getImageUrl());

        TextView product = commonViewHolder.getView(R.id.item_nearby_listview_product);
        product.setText(results.get(position).getProduct());

        ImageView isGroup = commonViewHolder.getView(R.id.item_nearby_listview_isgroup);
        isGroup.setVisibility(results.get(position).isIsGroup() ? View.VISIBLE : View.INVISIBLE);

        RatingBar score = commonViewHolder.getView(R.id.item_nearby_listview_score);
        score.setRating(results.get(position).getScore());

        TextView commentCount = commonViewHolder.getView(R.id.item_nearby_listview_comment);
        commentCount.setText(results.get(position).getCommentCount() + "人评价");

        TextView classify = commonViewHolder.getView(R.id.item_nearby_listview_classify);
        classify.setText(results.get(position).getClassify());

        TextView location = commonViewHolder.getView(R.id.item_nearby_listview_location);
        location.setText(results.get(position).getLocation());

        TextView distance = commonViewHolder.getView(R.id.item_nearby_listview_distance);

        String geoDistance = BaiduMapUtils.getDistance(results.get(position).getGeoLocation().getLongitude(),
                results.get(position).getGeoLocation().getLatitude(),
                Double.parseDouble(geoLocationInfo.get("Longtitude")),
                Double.parseDouble(geoLocationInfo.get("Latitude")));
        distance.setText(geoDistance);
        results.get(position).setDistance(geoDistance);

        TextView priceAfter = commonViewHolder.getView(R.id.item_nearby_listview_price_after);
        priceAfter.setText("¥ " + results.get(position).getPriceAfter());

        TextView priceBefore = commonViewHolder.getView(R.id.item_nearby_listview_price_before);
        priceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        priceBefore.setText("¥ " + results.get(position).getPriceBefore());

        TextView title = commonViewHolder.getView(R.id.item_nearby_listview_title);
        title.setText(results.get(position).getTitle());

        TextView sold = commonViewHolder.getView(R.id.item_nearby_listview_sold);
        sold.setText("已售" + results.get(position).getSold());


        return commonViewHolder.getConvertView();

    }
}
