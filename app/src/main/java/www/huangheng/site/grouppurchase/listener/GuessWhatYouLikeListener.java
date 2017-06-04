package www.huangheng.site.grouppurchase.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.GoodsListInfo;
import www.huangheng.site.grouppurchase.view.DetailActivity;

/**
 * 猜你喜欢点击监听器
 */

public class GuessWhatYouLikeListener implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<GoodsListInfo.ResultsBean> mGoodList;

   ;

    public GuessWhatYouLikeListener(Context mContext, List<GoodsListInfo.ResultsBean> mGoodList) {
        this.mContext = mContext;
        this.mGoodList = mGoodList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(ConstantPool.GOODSID, mGoodList.get(position - 1).getGoodsId());
        intent.putExtra(ConstantPool.OBJECTID, mGoodList.get(position - 1).getObjectId());
        intent.putExtra(ConstantPool.PRODUCT, mGoodList.get(position - 1).getProduct());
        intent.putExtra(ConstantPool.DISTANCE, mGoodList.get(position-1).getDistance());
        intent.putExtra(ConstantPool.TITLE, mGoodList.get(position - 1).getTitle());
        intent.putExtra(ConstantPool.PRICEAFTER, mGoodList.get(position - 1).getPriceAfter());
        intent.putExtra(ConstantPool.PRICEBEFORE, mGoodList.get(position - 1).getPriceBefore());
        intent.putExtra(ConstantPool.SOLD, mGoodList.get(position - 1).getSold());
        mContext.startActivity(intent);
    }

}
