package www.huangheng.site.grouppurchase.nohttp;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * NoHttp监听器
 */

public interface NoHttpListener<T> {
    void onSucceed(int what, Response<T> response);

    void onFailed(int what, Response<T> response);
}
