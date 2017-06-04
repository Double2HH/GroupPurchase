package www.huangheng.site.grouppurchase.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * 搜索历史监听器
 */

public class SearchHistoryListener implements AdapterView.OnItemClickListener {

    private Context mContext;
    private List<String> mDataList;

    private SearchHistorySearchListener listener;

    public SearchHistoryListener(Context context, List<String> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null) {
            //逆序
            listener.historySearch(mDataList.get(mDataList.size() - position - 1));
        }
    }

    public void setSearchHistorySearchListener(SearchHistorySearchListener listener) {
        this.listener = listener;
    }

    public interface SearchHistorySearchListener {
        void historySearch(String searchContent);
    }


}
