package www.huangheng.site.grouppurchase.nohttp;

import android.content.Context;
import android.content.DialogInterface;

import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Response;

import www.huangheng.site.grouppurchase.custom.view.WaitDialog;

/**
 * NoHttp响应监听器
 */

public class NoHttpResponseListener<T> implements OnResponseListener<T> {

    private NoHttpListener<T> mNoHttpListener;
    private WaitDialog mWaitDialog;

    public NoHttpResponseListener( NoHttpListener<T> noHttpListener) {
        this.mNoHttpListener = noHttpListener;
    }


    public NoHttpResponseListener(Context context, NoHttpListener<T> noHttpListener, boolean canCancle) {
        if (context != null) {
            mWaitDialog = new WaitDialog(context);
            mWaitDialog.setCancelable(canCancle);
            mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mWaitDialog.cancel();
                }
            });
        }
        this.mNoHttpListener = noHttpListener;
    }

    public NoHttpResponseListener(Context context, NoHttpListener<T> noHttpListener, boolean canCancle, String dialogMessage) {
        this(context, noHttpListener, canCancle);
        mWaitDialog.setMessage(dialogMessage);
    }


    @Override
    public void onStart(int what) {
        if (mWaitDialog != null && !mWaitDialog.isShowing()) {
            mWaitDialog.show();
        }
    }

    @Override
    public void onSucceed(int what, Response<T> response) {
        if (mNoHttpListener != null) {
            mNoHttpListener.onSucceed(what, response);
        }
    }

    @Override
    public void onFailed(int what, Response<T> response) {
        if (mNoHttpListener != null) {
            mNoHttpListener.onFailed(what, response);
        }
    }

    @Override
    public void onFinish(int what) {
        if (mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.cancel();
        }
    }
}
