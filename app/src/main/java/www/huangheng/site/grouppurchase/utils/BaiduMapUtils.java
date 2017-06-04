package www.huangheng.site.grouppurchase.utils;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.text.DecimalFormat;

import www.huangheng.site.grouppurchase.application.MyApplication;
import www.huangheng.site.grouppurchase.listener.BaiduMapAddressListener;


/**
 * 百度地图
 */

public class BaiduMapUtils {

    private LocationClient mLocationClient = null;

    private static BaiduMapUtils sBaiduMapUtils = new BaiduMapUtils();

    private BaiduMapAddressListener mBaiduMapAddressListener;

    public static BaiduMapUtils getInstance() {
        return sBaiduMapUtils;
    }


    private BaiduMapUtils() {
        //声明LocationClient类
        mLocationClient = new LocationClient(MyApplication.getContext());
        //注册监听函数
        mLocationClient.registerLocationListener(new MyLocationListener());
        initLocation();
    }

    public BaiduMapUtils(BaiduMapAddressListener mBaiduMapAddressListener) {
        this.mBaiduMapAddressListener = mBaiduMapAddressListener;
        //声明LocationClient类
        mLocationClient = new LocationClient(MyApplication.getContext());
        //注册监听函数
        mLocationClient.registerLocationListener(new MyLocationListener());
        initLocation();
    }

    /**
     * 配置定位SDK参数
     */
    private void initLocation() {

        LocationClientOption option = new LocationClientOption();

        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");

        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(0);

        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);

        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);

        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);

        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);

        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);

        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);

        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
    }

    /**
     * 获得地址信息
     */
    private class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {

            //获取定位结果
            StringBuilder sb = new StringBuilder(256);

            sb.append(location.getLatitude());    //获取纬度信息，使用 - 进行经纬度隔离
            sb.append("%");


            sb.append(location.getLongitude());    //获取经度信息
            sb.append("-");


            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                // GPS定位结果

                //获取地址信息
                sb.append(location.getAddrStr());

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                // 网络定位结果

                //获取地址信息
                sb.append(location.getAddrStr());


            } else {
                sb.append("定位失败");
            }

            if (mBaiduMapAddressListener != null) {
                mBaiduMapAddressListener.getLongLatitudeAndAddress(sb.toString());
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }

    /**
     * 获得定位地址
     */
    public void getAddress() {
        mLocationClient.start();
    }

    /**
     * 计算两个经纬度之间的距离
     *
     * @param longitude1 经度1
     * @param latitude1  纬度1
     * @param longitude2 经度2
     * @param latitude2  纬度2
     * @return 距离
     */
    public static String getDistance(Double longitude1, Double latitude1, Double longitude2, Double latitude2) {
        double lon1 = (Math.PI / 180) * longitude1;
        double lon2 = (Math.PI / 180) * longitude2;//经度

        double lat1 = (Math.PI / 180) * latitude1;
        double lat2 = (Math.PI / 180) * latitude2;//维度

        //地球半径
        double R = 6371;

        //两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;

        DecimalFormat df = new DecimalFormat("0.0");

        if (d < 1) {
            d = d * 1000;
            return df.format(d) + "m";
        }

        return df.format(d) + "km";
    }

}
