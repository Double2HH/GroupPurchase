package www.huangheng.site.grouppurchase.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.Map;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.HomepageInfo;
import www.huangheng.site.grouppurchase.entity.HomepageStaticInfo;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;
import www.huangheng.site.grouppurchase.entity.UserInfo;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.utils.BaiduMapUtils;
import www.huangheng.site.grouppurchase.utils.HttpRequestUtils;
import www.huangheng.site.grouppurchase.utils.SharedPreferencesUtils;


/**
 * 欢迎界面
 */

public class WelcomeActivity extends AppCompatActivity implements NoHttpListener<String> {

    private Handler mHandler;
    private SharedPreferences mSharedPreferences;
    private boolean isFirstTimeToUseApp;

    private BaiduMapUtils mBaiduMapUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initialize();
        delayed();

        HttpRequestUtils.getInstance().requestOfHomepage("https://api.bmob.cn/1/classes/HomepageView", this);

        Map<String, String> userInfoMap = SharedPreferencesUtils.getInstance().getUserInfoFromSP(this);
        if (!TextUtils.isEmpty(userInfoMap.get("UserName"))) {
            StaticUserInfo.getInstance().setUsername(userInfoMap.get("UserName"));
            StaticUserInfo.getInstance().setObjectId(userInfoMap.get("ObjectId"));
            StaticUserInfo.getInstance().setSessionToken(userInfoMap.get("SessionToken"));
            HttpRequestUtils.getInstance().requestOfUser(userInfoMap.get("ObjectId"), this);
        }

        mBaiduMapUtils = new BaiduMapUtils(longLatitudeAndAddress -> {
            String[] lla = longLatitudeAndAddress.split("-");
            String[] ll = lla[0].split("%");
            SharedPreferencesUtils.getInstance().putGeoLocationToSP(this, lla[1].substring(2),ll[0],ll[1] );
        });
        mBaiduMapUtils.getAddress();
    }

    /**
     * 延时
     */
    private void delayed() {
        mHandler.postDelayed(() -> {

            Intent intent = new Intent();

            if (isFirstTimeToUseApp) {
                mSharedPreferences.edit().putBoolean("isFirstTimeToUseApp", false).apply();
                intent.setClass(WelcomeActivity.this, GuideActivity.class);
            } else {
                intent.setClass(WelcomeActivity.this, MainActivity.class);
            }

            startActivity(intent);

            finish();
        }, 2000);

    }

    /**
     * 初始化
     */
    private void initialize() {
        mHandler = new Handler();
        mSharedPreferences = getSharedPreferences("configuration", MODE_PRIVATE);
        isFirstTimeToUseApp = mSharedPreferences.getBoolean("isFirstTimeToUseApp", true);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.WELCOME_WHAT:
                Gson homepageGson = new Gson();
                HomepageInfo homepageInfo = homepageGson.fromJson(response.get(), HomepageInfo.class);
                if (homepageInfo.getResults().size() == 1) {
                    HomepageStaticInfo.getInstance().setStaticInfo(homepageInfo.getResults().get(0));
                } else {
                    HomepageStaticInfo.getInstance().setStaticInfo(null);
                }
                break;

            case ConstantPool.USER_REQUEST_WHAT:

                Gson userGson = new Gson();
                UserInfo userInfo = userGson.fromJson(response.get(), UserInfo.class);
                StaticUserInfo.getInstance().setUserInfo(userInfo, false);

                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.WELCOME_WHAT:
                HomepageStaticInfo.getInstance().setStaticInfo(null);
                break;
        }
    }


}
