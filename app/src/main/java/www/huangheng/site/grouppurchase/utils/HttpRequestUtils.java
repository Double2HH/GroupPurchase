package www.huangheng.site.grouppurchase.utils;

import android.content.Context;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpServer;

/**
 * 网络请求
 */

public class HttpRequestUtils {

    private static final HttpRequestUtils sHttpRequestUtils = new HttpRequestUtils();

    public synchronized static HttpRequestUtils getInstance() {
        return sHttpRequestUtils;
    }

    private HttpRequestUtils() {
    }


    /**
     * 在WelcomeActivity中请求在Homepage中显示的数据
     *
     * @param url      连接
     * @param listener 监听器
     */
    public void requestOfHomepage(String url, NoHttpListener listener) {
        Request<String> stringRequest = NoHttp.createStringRequest(url, RequestMethod.GET);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        NoHttpServer.getInstance().add(ConstantPool.WELCOME_WHAT, stringRequest, listener);
    }


    /**
     * 初始化猜你喜欢列表
     */
    public void requestOfLike(String url, NoHttpListener listener) {
        //猜你喜欢的请求
        Request<String> stringRequest = NoHttp.createStringRequest(url, RequestMethod.GET);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        NoHttpServer.getInstance().add(ConstantPool.GUESSWHATYOULIKE_WHAT, stringRequest, listener);
    }

    //请求用户数据
    public void requestOfUser(String objectId, NoHttpListener listener) {
        Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/users/" + objectId, RequestMethod.GET);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        stringRequest.addHeader("Content-Type", "application/json");
        NoHttpServer.getInstance().add(ConstantPool.USER_REQUEST_WHAT, stringRequest, listener);
    }

    //请求用户数据
    public void requestOfUser(Context context, String username, String password, NoHttpListener<String> listener) {
        Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/login", RequestMethod.GET);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        stringRequest.addHeader("Content-Type", "application/json");
        stringRequest.add("username", username);
        stringRequest.add("password", password);
        NoHttpServer.getInstance().add(context, ConstantPool.LOGIN_WHAT, stringRequest, listener, true, "正在登录，请稍后...");
    }


    /**
     * 数据请求
     */
    public void requestOfNearbyData(NoHttpListener listener, double latitude, double longitude) {
        Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/classes/GoodsListInfo", RequestMethod.GET);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        stringRequest.addHeader("Content-Type", "application/json");
        stringRequest.add("limit", 2);
        stringRequest.add("where", "{\"geoLocation\":{\"$nearSphere\":{\"__type\":\"GeoPoint\",\"latitude\":" + latitude + ",\"longitude\":" + longitude + "}}}");
        NoHttpServer.getInstance().add(ConstantPool.NEARBYPRODUCT_WHAT, stringRequest, listener);
    }


    /**
     * 获取待付款数据
     */
    public void requestOfUnPaided(Context context, NoHttpListener<String> listener) {
        Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/classes/UnPaided", RequestMethod.GET);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        stringRequest.addHeader("Content-Type", "application/json");
        stringRequest.add("include", "goodsObjectId[goodsId|imageUrl|product|priceAfter]");
        stringRequest.add("username", StaticUserInfo.getInstance().getUsername());
        NoHttpServer.getInstance().add(context, ConstantPool.UNPAIDED_GETDATA_WHAT, stringRequest, listener, true);
    }

    /*
    *商品收藏
     */
    public void requestOfCollection(NoHttpListener listener) {

    }


}
