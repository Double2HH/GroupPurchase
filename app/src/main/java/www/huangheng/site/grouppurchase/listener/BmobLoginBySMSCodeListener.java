package www.huangheng.site.grouppurchase.listener;

/**
 * Bmob短信验证码登录监听器
 */

public interface BmobLoginBySMSCodeListener {

    public void onLoginSucceed();
    public void onLoginFailed(String errorMessage);

}
