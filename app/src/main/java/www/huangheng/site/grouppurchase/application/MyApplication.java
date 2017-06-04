package www.huangheng.site.grouppurchase.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yanzhenjie.nohttp.NoHttp;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import www.huangheng.site.grouppurchase.other.citylist.db.DBManager;

/**
 * Application,用于初始化
 */

public class MyApplication extends Application {

    private static Context context;
    private DBManager dbManager;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        context = getApplicationContext();

        NoHttp.initialize(this);

        Fresco.initialize(this);

        //ZXingLibrary.initDisplayOpinion(this);

        SDKInitializer.initialize(getApplicationContext());

        dbManager = new DBManager(getApplicationContext());
        dbManager.openDatabase();

        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("9fc41959205e1bbfaf50ad619eb45d0d")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);

    }

    /**
     * 获得Context
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        dbManager.closeDatabase();
    }


}
