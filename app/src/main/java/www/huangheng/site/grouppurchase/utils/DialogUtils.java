package www.huangheng.site.grouppurchase.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * 对话框工具类
 */

public class DialogUtils {

    private static final DialogUtils sDialogUtils = new DialogUtils();

    public synchronized static DialogUtils getInstance() {
        return sDialogUtils;
    }

    private DialogUtils() {
    }

    public void messageDialog(final Context context, String message, DialogInterface.OnClickListener negListener, DialogInterface.OnClickListener posListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.setNegativeButton("取消", negListener);
        dialog.setPositiveButton("确定", posListener);
        dialog.setCancelable(true);
        dialog.create();
        dialog.show();
    }


}
