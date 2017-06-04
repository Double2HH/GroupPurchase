package www.huangheng.site.grouppurchase.event;

/**
 * 用户登录事件
 */

public class UserEvent {
    String UserName;

    public UserEvent(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
