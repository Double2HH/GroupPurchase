package www.huangheng.site.grouppurchase.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.List;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.UnPaided;

/**
 * 待付款监听器
 */

public class UnPaidedListener implements AdapterView.OnItemClickListener {

    private Context mContext;

    private boolean isShowCheckBox = false;
    private List<UnPaided.ResultsBean> unPaideds;
    private List<String> objectIds;
    private Button mUnpaidedDelete;

    private int num = 0;


    public UnPaidedListener(Context context, List<UnPaided.ResultsBean> unPaideds, List<String> objectIds, Button mUnpaidedDelete) {
        this.mContext = context;
        this.unPaideds = unPaideds;
        this.objectIds = objectIds;
        this.mUnpaidedDelete = mUnpaidedDelete;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isShowCheckBox) {
            CheckBox checkbox = (CheckBox) view.findViewById(R.id.item_unpaided_checkbox);
            checkbox.setChecked(!checkbox.isChecked());
            if (checkbox.isChecked()) {
                objectIds.add(unPaideds.get(position).getObjectId());
                num++;
            } else {
                objectIds.remove(unPaideds.get(position).getObjectId());
                num--;
            }

            if (num == 0) {
                mUnpaidedDelete.setText("删除");
                mUnpaidedDelete.setEnabled(false);
            } else {
                mUnpaidedDelete.setText("删除 (" + num + ")");
                mUnpaidedDelete.setEnabled(true);
            }

        } else {
//            Intent intent = new Intent(mContext, DetailActivity.class);
//            mContext.startActivity(intent);
            Toast.makeText(mContext, "跳转到商品详情", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 显示CheckBox与否
     *
     * @param show trueorfalse
     */
    public void checkOrUnCheck(boolean show) {
        isShowCheckBox = show;
    }

    /**
     * 删除数目设置为0
     */
    public void setNunToZero() {
        num = 0;
        mUnpaidedDelete.setText("删除");
        mUnpaidedDelete.setEnabled(false);
    }

}
