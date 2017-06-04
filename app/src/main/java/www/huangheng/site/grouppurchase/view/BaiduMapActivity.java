package www.huangheng.site.grouppurchase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudRgcResult;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.huangheng.site.grouppurchase.R;

/**
 * 百度地图界面
 */
public class BaiduMapActivity extends AppCompatActivity implements View.OnClickListener, CloudListener {

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @BindView(R.id.baidumap_mapview)
    MapView mBaidumapMapview;

    private BaiduMap baidumap;
    private boolean isFirstLoc;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidumap);
        ButterKnife.bind(this);
        initialize();
    }


    private void initialize() {


        baidumap = mBaidumapMapview.getMap();
        baidumap.setMyLocationEnabled(true);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);

        initLocation();


        Button button = (Button) findViewById(R.id.baidumap_button);
        button.setOnClickListener(this);

        //初始化
        CloudManager.getInstance().init(this);


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//
//        option.setCoorType("bd09ll");
//        //可选，默认gcj02，设置返回的定位结果坐标系
//
//        int span = 1000;
//        option.setScanSpan(span);
//        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//
//        option.setIsNeedAddress(true);
//        //可选，设置是否需要地址信息，默认不需要
//
//        option.setOpenGps(true);
//        //可选，默认false,设置是否使用gps
//
//        option.setLocationNotify(true);
//        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//
//        option.setIsNeedLocationDescribe(true);
//        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//
//        option.setIsNeedLocationPoiList(true);
//        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//
//        option.setIgnoreKillProcess(false);
//        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//
//        option.SetIgnoreCacheException(false);
//        //可选，默认false，设置是否收集CRASH信息，默认收集
//
//        option.setEnableSimulateGps(false);
//        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//
//        mLocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
        //周边poi检索
        NearbySearchInfo info = new NearbySearchInfo();
        info.ak = "lZFy8n7ZQm0RbD12jPyGte5j6LAHNyCZ";
        info.geoTableId = 164999;
        info.radius = 3000;
        info.location = "113.391503,23.049433";
        CloudManager.getInstance().nearbySearch(info);
    }


    @Override
    public void onGetSearchResult(CloudSearchResult cloudSearchResult, int i) {
        //检索回调方法
        if (cloudSearchResult != null && cloudSearchResult.poiList != null && cloudSearchResult.poiList.size() > 0) {
            //清除map
            baidumap.clear();
            LatLng latLng;
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_nearby_gcoding);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (CloudPoiInfo info : cloudSearchResult.poiList) {
                latLng = new LatLng(info.latitude, info.longitude);
                MarkerOptions options = new MarkerOptions().icon(bitmapDescriptor).position(latLng);
//                OverlayOptions option = new MarkerOptions()
//                        .position(latLng)
//                        .icon(bitmapDescriptor);
                baidumap.addOverlay(options);
                builder.include(latLng);
            }


        }

    }

    @Override
    public void onGetDetailSearchResult(DetailSearchResult detailSearchResult, int i) {

    }

    @Override
    public void onGetCloudRgcResult(CloudRgcResult cloudRgcResult, int i) {

    }


    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            if (mBaidumapMapview == null || location == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            baidumap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baidumap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        mBaidumapMapview.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁定位
        mLocationClient.stop();
        //关闭地图定位
        baidumap.setMyLocationEnabled(false);
        mBaidumapMapview.onDestroy();
        mBaidumapMapview = null;


    }

    @Override
    protected void onResume() {
        super.onResume();
        mBaidumapMapview.onResume();
    }
}
