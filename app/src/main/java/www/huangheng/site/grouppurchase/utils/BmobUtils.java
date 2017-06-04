package www.huangheng.site.grouppurchase.utils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import www.huangheng.site.grouppurchase.entity.FavoriteInfo;
import www.huangheng.site.grouppurchase.entity.GoodsListInfo;
import www.huangheng.site.grouppurchase.listener.BmobLoginBySMSCodeListener;
import www.huangheng.site.grouppurchase.listener.BmobRequestSMSCodeListener;

/**
 * Bmob工具类
 */

final public class BmobUtils {

    private static BmobUtils mBmobUtils = null;

    public String objcetId;

    public synchronized static BmobUtils getInstance() {
        if (mBmobUtils != null) {
            return mBmobUtils;
        } else {
            return new BmobUtils();
        }
    }


    /**
     * 发送短信验证码
     *
     * @param phoneNumber            手机号码
     * @param requestSMSCodeListener 监听器
     */
    public void sendSMSCode(String phoneNumber, final BmobRequestSMSCodeListener requestSMSCodeListener) {
        BmobSMS.requestSMSCode(phoneNumber, "模板名称", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    requestSMSCodeListener.onSendSucceed();
                } else {
                    requestSMSCodeListener.onSendFailed(e.getMessage());
                }
            }
        });
    }

    /**
     * 通过短信验证码登录
     *
     * @param phoneNumber                手机号码
     * @param code                       验证码
     * @param bmobLoginBySMSCodeListener 监听器
     */
    public void loginBySMSCode(String phoneNumber, String code, final BmobLoginBySMSCodeListener bmobLoginBySMSCodeListener) {
        BmobUser.loginBySMSCode(phoneNumber, code, new LogInListener<FavoriteInfo>() {
            @Override
            public void done(FavoriteInfo myUser, BmobException e) {
                if (e == null) {
                    bmobLoginBySMSCodeListener.onLoginSucceed();
                } else {
                    bmobLoginBySMSCodeListener.onLoginFailed(e.getMessage());
                }
            }
        });
    }


    /**
     * 保存数据
     *
     * @param favoriteInfo 数据模型
     */
    public void saveData(FavoriteInfo favoriteInfo, SaveListener<String> saveListener) {
        favoriteInfo.save(saveListener);
    }


    /**
     * 删除数据
     *
     * @param favoriteInfo 数据
     * @param objectId     id
     */
    public void deleteData(FavoriteInfo favoriteInfo, String objectId, UpdateListener updateListener) {
        favoriteInfo.delete(objectId, updateListener);
    }

    /**
     * 根据objectId来查询数据
     *
     * @param objectId      id
     * @param queryListener 监听器
     */
    public void queryData(String objectId, QueryListener<FavoriteInfo> queryListener) {
        BmobQuery<FavoriteInfo> query = new BmobQuery<>();
        query.getObject(objectId, queryListener);
    }

    /**
     * 根据goodsId来查询数据
     *
     * @param goodsId      商品Id
     * @param findListener 监听器
     */
    public void queryData(String goodsId, FindListener<FavoriteInfo> findListener) {
        BmobQuery<FavoriteInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("goodsId", goodsId);
        query.findObjects(findListener);
    }

    /**
     * 根据key/value来查询数据
     *
     * @param findListener 监听器
     */
    public void queryAllData(FindListener<FavoriteInfo> findListener) {
        BmobQuery<FavoriteInfo> query = new BmobQuery<>();
        query.findObjects(findListener);
    }


    /**
     * 猜你喜欢列表数据
     *
     * @param findListener 监听器
     */
    public void quaryLikeAllData(QueryListener<GoodsListInfo> findListener) {
        BmobQuery<GoodsListInfo> query = new BmobQuery<>();
        query.getObject("PAjk777B", findListener);
    }


}
