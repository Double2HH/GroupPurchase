package www.huangheng.site.grouppurchase.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.database.DatabaseUtils;


/**
 * 搜索历史
 */

public class SearchHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDataList;
    private SearchHistoryEmptyListener listener;

    public SearchHistoryAdapter(Context context, List<String> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        CommonViewHolder commonViewHolder = CommonViewHolder.get(mContext, convertView, R.layout.item_search_history, parent);
        TextView product = commonViewHolder.getView(R.id.search_history_product);
        //逆序
        product.setText(mDataList.get(getCount() - position - 1));
        ImageView delete = commonViewHolder.getView(R.id.search_history_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除某条历史记录
                DatabaseUtils.deleteSearchHistory(mContext, mDataList.get(position));
                mDataList.remove(mDataList.get(getCount() - position - 1));
                notifyDataSetChanged();
                if (mDataList.size() == 0) {
                    listener.empty();
                }
            }
        });

        return commonViewHolder.getConvertView();


    }

    public void setSearchHistoryEmptyListener(SearchHistoryEmptyListener listener) {
        this.listener = listener;
    }

    public interface SearchHistoryEmptyListener {
        void empty();
    }

}
