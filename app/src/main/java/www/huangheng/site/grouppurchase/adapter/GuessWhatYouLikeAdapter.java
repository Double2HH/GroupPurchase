package www.huangheng.site.grouppurchase.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.GoodsListInfo;
import www.huangheng.site.grouppurchase.utils.BaiduMapUtils;
import www.huangheng.site.grouppurchase.utils.SharedPreferencesUtils;


/**
 * 猜你喜欢商品列表设配器
 */

public class GuessWhatYouLikeAdapter extends BaseAdapter {


    private Context mContext;
    private List<GoodsListInfo.ResultsBean> mDataList;
    private boolean isEmpty = false;

    private Map<String, String> geoLocationInfo;

    public GuessWhatYouLikeAdapter(Context mContext, List<GoodsListInfo.ResultsBean> mDataList) {
        this(mContext);
        this.mDataList = mDataList;
    }

    public GuessWhatYouLikeAdapter(Context mContext) {
        this.mContext = mContext;
        mDataList = new ArrayList<>();
        geoLocationInfo = SharedPreferencesUtils.getInstance().getGeoLocationFromSP(mContext);
    }

    @Override
    public int getCount() {
        if (mDataList.size() == 0) {
            isEmpty = true;
            return 1;
        } else {
            isEmpty = false;
            return mDataList.size();
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
            CommonViewHolder empeyViewHolder = CommonViewHolder.get(mContext, convertView, R.layout.item_homepage_like_empty, parent);
            return empeyViewHolder.getConvertView();
        }

        CommonViewHolder goodsListViewHolder = CommonViewHolder.get(mContext, convertView, R.layout.item_homepage_like, parent);
        SimpleDraweeView image = goodsListViewHolder.getView(R.id.guesswhatyoulike_list_image);
        image.setImageURI(Uri.parse(mDataList.get(position).getImageUrl()));

        ImageView appointment = goodsListViewHolder.getView(R.id.guesswhatyoulike_list_appoitment);
        appointment.setVisibility(mDataList.get(position).isFreeAppointment() ? View.VISIBLE : View.GONE);

        TextView product = goodsListViewHolder.getView(R.id.guesswhatyoulike_list_product);
        product.setText(mDataList.get(position).getProduct());

        TextView distance = goodsListViewHolder.getView(R.id.guesswhatyoulike_list_distance);
        String geoDistance = BaiduMapUtils.getDistance(mDataList.get(position).getGeoLocation().getLongitude(),
                mDataList.get(position).getGeoLocation().getLatitude(),
                Double.parseDouble(geoLocationInfo.get("Longtitude")),
                Double.parseDouble(geoLocationInfo.get("Latitude")));
        distance.setText(geoDistance);
        mDataList.get(position).setDistance(geoDistance);





        TextView title = goodsListViewHolder.getView(R.id.guesswhatyoulike_list_title);
        title.setText("[" + mDataList.get(position).getLocation() + "]" + mDataList.get(position).getTitle());

        TextView price_after = goodsListViewHolder.getView(R.id.guesswhatyoulike_price_after);
        price_after.setText(mDataList.get(position).getPriceAfter() + "");

        TextView price_before = goodsListViewHolder.getView(R.id.guesswhatyoulike_price_before);
        price_before.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        price_before.setText(mDataList.get(position).getPriceBefore() + "");

        TextView sold = goodsListViewHolder.getView(R.id.guesswhatyoulike_list_sold);
        sold.setText("已售 " + mDataList.get(position).getSold());

        return goodsListViewHolder.getConvertView();
    }


}
