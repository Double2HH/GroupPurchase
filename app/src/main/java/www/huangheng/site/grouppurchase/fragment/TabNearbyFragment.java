package www.huangheng.site.grouppurchase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.NearbyProductAdapter;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.NearbyProductInfo;
import www.huangheng.site.grouppurchase.listener.NearbyProductListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.utils.BaiduMapUtils;
import www.huangheng.site.grouppurchase.utils.HttpRequestUtils;
import www.huangheng.site.grouppurchase.view.BaiduMapActivity;
import www.huangheng.site.grouppurchase.view.SearchActivity;


/**
 * 碎片
 */

public class TabNearbyFragment extends Fragment implements NoHttpListener<String> {

    public static final int ADDRESS = 0;    //地址
    public static final int LATITUDEANDLONGTITUDE = 1;    //纬度和经度


    private double longtitude;
    private double latitude;

    @BindView(R.id.nearby_headerview_location_text)
    TextView mNearbyHeaderviewLocationText;

    //列表
    @BindView(R.id.nearby_listview)
    ListView mNearbyListview;

    BaiduMapUtils mBaiduMapUtils;
    boolean isRefreshLocation = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADDRESS:
                    mNearbyHeaderviewLocationText.setText((String) msg.obj);
                    if (isRefreshLocation) {
                        Toast.makeText(getActivity(), "位置刷新成功", Toast.LENGTH_SHORT).show();
                        isRefreshLocation = false;
                    }
                    break;

                case LATITUDEANDLONGTITUDE:

                    String[] lla = ((String) msg.obj).split("%");
                    latitude = Double.parseDouble(lla[0]);
                    longtitude = Double.parseDouble(lla[1]);
                    HttpRequestUtils.getInstance().requestOfNearbyData(TabNearbyFragment.this, latitude, longtitude);

                    break;

            }

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Toast.makeText(getActivity(),"我是onCreate()",Toast.LENGTH_SHORT).show();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_nearby, container, false);
        ButterKnife.bind(this, view);
        // Toast.makeText(getActivity(),"我是onCreateView()",Toast.LENGTH_SHORT).show();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBaiduMapUtils = new BaiduMapUtils(longLatitudeAndAddress -> {

            String[] lla = longLatitudeAndAddress.split("-");

            mHandler.sendMessage(mHandler.obtainMessage(LATITUDEANDLONGTITUDE, lla[0]));
            mHandler.sendMessage(mHandler.obtainMessage(ADDRESS, lla[1].substring(2)));

        });
        mBaiduMapUtils.getAddress();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.NEARBYPRODUCT_WHAT:
                Gson gson = new Gson();
                NearbyProductInfo nearbyProductInfo = gson.fromJson(response.get(), NearbyProductInfo.class);
                List<NearbyProductInfo.ResultsBean> nearbyProductInfoResults = nearbyProductInfo.getResults();
                mNearbyListview.setAdapter(new NearbyProductAdapter(getActivity(), nearbyProductInfoResults));
                mNearbyListview.setOnItemClickListener(new NearbyProductListener(getActivity(), nearbyProductInfoResults));

                if (mNearbyListview.getFooterViewsCount() == 0 && nearbyProductInfo.getResults().size() != 0) {
                    View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.item_load_all, null);
                    mNearbyListview.addFooterView(footerView, null, false);
                }

                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.NEARBYPRODUCT_WHAT:
                mNearbyListview.setAdapter(new NearbyProductAdapter(getActivity()));
                break;
        }

    }


    @OnClick({R.id.nearby_titlebar_route, R.id.nearby_titlebar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nearby_titlebar_route:
                Intent mapIntent = new Intent(getActivity(), BaiduMapActivity.class);
                startActivity(mapIntent);
                break;
            case R.id.nearby_titlebar_search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.nearby_headerview_refresh)
    public void onViewClicked() {
        isRefreshLocation = true;
        mBaiduMapUtils.getAddress();
    }


}
