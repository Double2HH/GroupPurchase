package www.huangheng.site.grouppurchase.utils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * SharedPreferences工具类
 */

public class SharedPreferencesUtils {


    private static final SharedPreferencesUtils sSharedPreferencesUtils = new SharedPreferencesUtils();

    public synchronized static SharedPreferencesUtils getInstance() {
        return sSharedPreferencesUtils;
    }

    private SharedPreferencesUtils() {
    }


    /**
     * 保存用户信息
     */
    public void putUserInfoToSP(Context context, String userName, String objectId, String sessionToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName", userName);
        editor.putString("ObjectId", objectId);
        editor.putString("SessionToken", sessionToken);
        editor.apply();
    }

    /**
     * 获取用户信息
     */
    public Map<String, String> getUserInfoFromSP(Context context) {
        Map<String, String> userInfo = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        userInfo.put("UserName", sharedPreferences.getString("UserName", ""));
        userInfo.put("ObjectId", sharedPreferences.getString("ObjectId", ""));
        userInfo.put("SessionToken", sharedPreferences.getString("SessionToken", ""));
        return userInfo;
    }


    /**
     * 清除用户数据
     *
     * @param context 上下文
     */
    public void clearUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserName", "");
        editor.putString("ObjectId", "");
        editor.putString("SessionToken", "");
        editor.commit();
    }

    /**
     * 保存地理位置信息
     *
     * @param context    上下文
     * @param address    地址
     * @param latitude   纬度
     * @param longtitude 经度
     */
    public void putGeoLocationToSP(Context context, String address, String latitude, String longtitude) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("GeoLocation", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Address", address);
        editor.putString("Latitude", latitude + "");
        editor.putString("Longtitude", longtitude + "");
        editor.apply();
    }


    /**
     * 获取地理位置信息
     */
    public Map<String, String> getGeoLocationFromSP(Context context) {
        Map<String, String> geoLocationInfo = new HashMap<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("GeoLocation", Activity.MODE_PRIVATE);
        geoLocationInfo.put("Address", sharedPreferences.getString("Address", ""));
        geoLocationInfo.put("Latitude", sharedPreferences.getString("Latitude", ""));
        geoLocationInfo.put("Longtitude", sharedPreferences.getString("Longtitude", ""));
        return geoLocationInfo;
    }

}
