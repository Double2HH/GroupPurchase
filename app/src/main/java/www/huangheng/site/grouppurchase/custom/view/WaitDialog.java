package www.huangheng.site.grouppurchase.custom.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

/**
 * 等待对话框
 */

public class WaitDialog extends ProgressDialog {


    public WaitDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setProgressStyle(STYLE_SPINNER);
        setMessage("正在请求，请稍后...");
    }

    public WaitDialog(Context context, int theme) {
        super(context, theme);
    }
}
