package www.huangheng.site.grouppurchase.listener;

/**
 * Bmob获取短信验证码监听器
 */

public interface BmobRequestSMSCodeListener {

    public void onSendSucceed();
    public void onSendFailed(String errorMessage);

}
