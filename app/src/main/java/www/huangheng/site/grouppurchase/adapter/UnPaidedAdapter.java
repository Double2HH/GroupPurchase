package www.huangheng.site.grouppurchase.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.view.PayActivity;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.UnPaided;

/**
 * 待付款
 */

public class UnPaidedAdapter extends BaseAdapter {

    private Context mContext;
    private List<UnPaided.ResultsBean> unPaideds;

    private boolean showCheckBox = false;

    public UnPaidedAdapter(Context context, List<UnPaided.ResultsBean> unPaideds) {
        this.mContext = context;
        this.unPaideds = unPaideds;
    }

    @Override
    public int getCount() {
        return unPaideds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext, convertView, R.layout.item_unpaided_listview, parent);

        final UnPaided.ResultsBean resultsBean = unPaideds.get(position);
        final UnPaided.ResultsBean.GoodsObjectIdBean goodsObjectId = resultsBean.getGoodsObjectId();

        CheckBox checkBox = commonViewHolder.getView(R.id.item_unpaided_checkbox);
        checkBox.setChecked(false);
        checkBox.setClickable(false);

        SimpleDraweeView draweeView = commonViewHolder.getView(R.id.item_unpaided_image);
        draweeView.setImageURI(goodsObjectId.getImageUrl());

        TextView product = commonViewHolder.getView(R.id.item_unpaided_product);
        product.setText(goodsObjectId.getProduct());

        TextView price = commonViewHolder.getView(R.id.item_unpaided_price_total);
        price.setText("¥ " + (goodsObjectId.getPriceAfter() * resultsBean.getAmount()));

        TextView amount = commonViewHolder.getView(R.id.item_unpaided_amount);
        amount.setText("数量：" + resultsBean.getAmount());

        Button pay = commonViewHolder.getView(R.id.item_unpaided_pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayActivity.class);
                intent.putExtra(ConstantPool.GOODSID, goodsObjectId.getGoodsId());
                intent.putExtra(ConstantPool.PRODUCT, goodsObjectId.getProduct());
                intent.putExtra(ConstantPool.BUYAMOUNT, resultsBean.getAmount());
                intent.putExtra(ConstantPool.PRICEAFTER, goodsObjectId.getPriceAfter());
                mContext.startActivity(intent);
            }
        });

        if (showCheckBox) {
            checkBox.setVisibility(View.VISIBLE);
            pay.setEnabled(false);
        } else {
            checkBox.setVisibility(View.GONE);
            pay.setEnabled(true);
        }

        return commonViewHolder.getConvertView();
    }


    /**
     * 显示CheckBox
     */
    public void showCheckBox() {
        showCheckBox = true;
        notifyDataSetChanged();
    }

    /**
     * 隐藏CheckBox
     */
    public void cancelShowCheckBox() {
        showCheckBox = false;
        notifyDataSetChanged();
    }

}
