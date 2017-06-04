package www.huangheng.site.grouppurchase.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import www.huangheng.site.grouppurchase.view.DetailActivity;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.NearbyProductInfo;

/**
 * 附近商品、搜索商品点击监听器
 */

public class NearbyProductListener implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<NearbyProductInfo.ResultsBean> mGoodList;

    public NearbyProductListener(Context mContext, List<NearbyProductInfo.ResultsBean> mGoodList) {
        this.mContext = mContext;
        this.mGoodList = mGoodList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(ConstantPool.GOODSID, mGoodList.get(position).getGoodsId());
        intent.putExtra(ConstantPool.PRODUCT, mGoodList.get(position).getProduct());
        intent.putExtra(ConstantPool.DISTANCE,mGoodList.get(position).getDistance());
        intent.putExtra(ConstantPool.TITLE, mGoodList.get(position).getTitle());
        intent.putExtra(ConstantPool.PRICEAFTER, mGoodList.get(position).getPriceAfter());
        intent.putExtra(ConstantPool.PRICEBEFORE, mGoodList.get(position).getPriceBefore());
        intent.putExtra(ConstantPool.SOLD, mGoodList.get(position).getSold());
        mContext.startActivity(intent);
    }
}
