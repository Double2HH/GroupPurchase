package www.huangheng.site.grouppurchase.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.NearbyProductAdapter;
import www.huangheng.site.grouppurchase.adapter.SearchHistoryAdapter;
import www.huangheng.site.grouppurchase.custom.view.FlowLayout;
import www.huangheng.site.grouppurchase.database.DatabaseUtils;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.NearbyProductInfo;
import www.huangheng.site.grouppurchase.listener.NearbyProductListener;
import www.huangheng.site.grouppurchase.listener.SearchHistoryListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpServer;
import www.huangheng.site.grouppurchase.other.citylist.utils.DensityUtil;
import www.huangheng.site.grouppurchase.other.citylist.utils.KeyBoard;

/**
 * 搜索
 */

public class SearchActivity extends AppCompatActivity implements NoHttpListener<String> {


    @BindView(R.id.search_flowandhistory)
    LinearLayout mSearchFlowandhistory;

    @BindView(R.id.search_titlebar_edittext)
    EditText mSearchTitlebarEdittext;

    @BindView(R.id.search_history_listview)
    ListView mSearchHistoryListview;
    SearchHistoryAdapter searchHistoryAdapter;
    List<String> mDataList;
    //FooterView
    TextView footer;

    @BindView(R.id.search_flowlayout)
    FlowLayout mSearchFlowlayout;

    //搜索结果
    @BindView(R.id.search_result)
    LinearLayout mSearchResult;
    @BindView(R.id.search_result_listview)
    ListView mSearchResultListview;

    View footerView;

    SearchHistoryListener mSearchHistoryListener;


    boolean isResultShow = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        initFlowLayoutDataAndView();
        initSearchHistoryDataAndView();
        initSearchHistoryClearHistory();
        initSearchResultView();

    }

    /**
     * 初始化流式布局
     */
    private void initFlowLayoutDataAndView() {
        final String[] product = new String[]{"酒店", "百胜棋牌", "身体体检", "儿童摄影", "亲自摄影", "长隆欢乐世界", "香香果水果专卖店", "休闲娱乐", "自助餐"};
        String[] color = new String[]{"#BA55D3", "#FFB6C1", "#CDCDC1", "#7A67EE", "#54FF9F", "#CAFF70", "#EE7621", "#8B668B"};
        TextView textView = null;
        for (int i = 0; i < product.length; i++) {
            textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setText(product[i]);
            textView.setPadding(12, 0, 12, 0);
            textView.setLines(1);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search(product[finalI]);
                }
            });

            textView.setBackgroundColor(Color.parseColor(color[i * 3 % 8]));
            mSearchFlowlayout.addView(textView, ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(this, 36));
        }
    }


    /**
     * 初始化搜索历史
     */
    private void initSearchHistoryDataAndView() {

        mDataList = DatabaseUtils.getSearchHistory(this);

        searchHistoryAdapter = new SearchHistoryAdapter(this, mDataList);


        mSearchHistoryListview.setAdapter(searchHistoryAdapter);

        mSearchHistoryListener = new SearchHistoryListener(this, mDataList);

        mSearchHistoryListview.setOnItemClickListener(mSearchHistoryListener);

        mSearchHistoryListener.setSearchHistorySearchListener(new SearchHistoryListener.SearchHistorySearchListener() {
            @Override
            public void historySearch(String searchContent) {
                search(searchContent);
                mSearchTitlebarEdittext.setText(searchContent);
                mSearchTitlebarEdittext.setSelection(searchContent.length());
            }
        });
    }


    /**
     * 初始化清除搜索历史按钮
     */
    private void initSearchHistoryClearHistory() {

        RelativeLayout relativeLayout = new RelativeLayout(this);

        footer = new TextView(this);
        if (mDataList.size() == 0) {
            footer.setText("无搜索历史");
            footer.setEnabled(false);
        } else {
            footer.setText("一键清除搜索历史");
            footer.setEnabled(true);
        }

        footer.setGravity(Gravity.CENTER);
        footer.setBackgroundResource(R.drawable.item_click_selector);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 48));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        relativeLayout.addView(footer, layoutParams);

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除搜索历史
                if (mDataList.size() != 0) {
                    DatabaseUtils.deleteSearchHistory(SearchActivity.this);
                    mDataList.clear();
                    searchHistoryAdapter.notifyDataSetChanged();
                    footer.setText("无搜索历史");
                    footer.setEnabled(false);
                }
            }
        });

        searchHistoryAdapter.setSearchHistoryEmptyListener(new SearchHistoryAdapter.SearchHistoryEmptyListener() {
            @Override
            public void empty() {
                footer.setText("无搜索历史");
                footer.setEnabled(false);
            }
        });

        mSearchHistoryListview.addFooterView(relativeLayout);
    }

    /**
     * 初始化搜索结果
     */
    private void initSearchResultView() {
        footerView = LayoutInflater.from(this).inflate(R.layout.item_load_all, null);
        mSearchResultListview.addFooterView(footerView, null, false);
    }


    @OnClick({R.id.search_titlebar_back, R.id.search_titlebar_search_textview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_titlebar_back:
                if (isResultShow) {
                    mSearchResult.setVisibility(View.GONE);
                    mSearchFlowandhistory.setVisibility(View.VISIBLE);
                    isResultShow = false;
                } else {
                    finish();
                }
                break;

            case R.id.search_titlebar_search_textview:

                search(mSearchTitlebarEdittext.getText().toString());

                break;
        }
    }

    /**
     * 搜索逻辑
     */
    private void search(String searchContent) {


        if (!TextUtils.isEmpty(searchContent)) {

            mSearchTitlebarEdittext.setText(searchContent);
            mSearchTitlebarEdittext.setSelection(searchContent.length());

            DatabaseUtils.saveSearchHistory(this, searchContent);
            if (!mDataList.contains(searchContent)) {
                mDataList.add(searchContent);
            }

            footer.setEnabled(true);
            footer.setText("一键清除搜索历史");

            requestData(searchContent);

            KeyBoard.closeSoftKeyboard(this);
            searchHistoryAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(SearchActivity.this, "请输入商品名、品类或商圈...", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 数据请求
     */
    private void requestData(String searchContent) {
        Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/classes/NearbyProduct", RequestMethod.GET);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        stringRequest.add("where", "{\"product\":\""+searchContent+"\"}");
        NoHttpServer.getInstance().add(this, ConstantPool.SEARCH_WHAT, stringRequest, this, true, "正在加载...");
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.SEARCH_WHAT:

                Gson gson = new Gson();
                NearbyProductInfo nearbyProductInfo = gson.fromJson(response.get(), NearbyProductInfo.class);

                if (!isResultShow) {
                    mSearchFlowandhistory.setVisibility(View.GONE);
                    mSearchResult.setVisibility(View.VISIBLE);
                    isResultShow = true;
                }

                mSearchResultListview.setAdapter(new NearbyProductAdapter(this, nearbyProductInfo.getResults()));
                mSearchResultListview.setOnItemClickListener(new NearbyProductListener(this, nearbyProductInfo.getResults()));

                break;
        }

    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.SEARCH_WHAT:
                Toast.makeText(this, "搜索失败，请检查网络", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
