package www.huangheng.site.grouppurchase.nohttp;

import android.content.Context;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

/**
 * NoHttp封装
 */

public class NoHttpServer {


    private static final NoHttpServer sNoHttpServerInstance = new NoHttpServer();
    private final RequestQueue mRequestQueue;

    public synchronized static NoHttpServer getInstance() {
        return sNoHttpServerInstance;
    }

    private NoHttpServer() {
        mRequestQueue = NoHttp.newRequestQueue();
    }


    public <T> void add(int what, Request<T> request, NoHttpListener<T> noHttpListener) {
        mRequestQueue.add(what, request, new NoHttpResponseListener<T>(noHttpListener));
    }


    public <T> void add(Context context, int what, Request<T> request, NoHttpListener<T> noHttpListener, boolean canCancel) {
        mRequestQueue.add(what, request, new NoHttpResponseListener<T>(context, noHttpListener, canCancel));
    }

    public <T> void add(Context context, int what, Request<T> request, NoHttpListener<T> noHttpListener, boolean canCancel, String dialogMessage) {
        mRequestQueue.add(what, request, new NoHttpResponseListener<T>(context, noHttpListener, canCancel, dialogMessage));
    }

}
