package www.huangheng.site.grouppurchase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpServer;


/**
 * 支付
 */

public class PayActivity extends AppCompatActivity implements NoHttpListener<String> {

    @BindView(R.id.pay_product)
    TextView mPayProduct;
    @BindView(R.id.pay_amount)
    TextView mPayAmount;
    @BindView(R.id.pay_price)
    TextView mPayPrice;

    @BindView(R.id.pay_weixin_checkbox)
    CheckBox mPayWeixinCheckbox;
    @BindView(R.id.pay_alipay_checkbox)
    CheckBox mPayAlipayCheckbox;

    @BindView(R.id.pay_pay)
    Button mPayPay;


    private String objectId;
    private String username;

    private String product;
    private int amount;
    private int price;

    private boolean isFromOrder = false;

    //支付方式：微信支付：0；支付宝：1
    private int payMode = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        initGetDataFromOrder(getIntent());
        initView();
        initSaveDataToOrder();
        unpaidedMessagesAddOne();
    }

    /**
     * 获取订单中的数据
     */
    private void initGetDataFromOrder(Intent intent) {
        objectId = intent.getStringExtra(ConstantPool.OBJECTID);
        username = intent.getStringExtra(ConstantPool.USERNAME);
        product = intent.getStringExtra(ConstantPool.PRODUCT);
        amount = intent.getIntExtra(ConstantPool.BUYAMOUNT, 0);
        price = intent.getIntExtra(ConstantPool.PRICEAFTER, 0);
        isFromOrder = intent.getBooleanExtra(ConstantPool.ISFROMORDER, false);
    }

    /**
     * 初始化数据
     */
    private void initView() {
        mPayProduct.setText(product);
        mPayAmount.setText("数量：" + amount);
        mPayPrice.setText("¥ " + (price * amount));
        mPayPay.setText("确认支付 " + (price * amount) + "元");
    }

    /**
     * 将订单数据传输到服务器
     */
    private void initSaveDataToOrder() {
        if (isFromOrder) {
            Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/classes/UnPaided", RequestMethod.POST);
            stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
            stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
            stringRequest.addHeader("Content-Type", "application/json");
            stringRequest.setDefineRequestBodyForJson("{\"goodsObjectId\":{\"__type\":\"Pointer\", \"className\":\"GoodsListInfo\",\"objectId\":\"" + objectId + "\"},\"username\":\"" + username + "\",\"amount\":" + amount + "}");
            NoHttpServer.getInstance().add(PayActivity.this, ConstantPool.PAY_WHAT, stringRequest, this, true);
        }
    }

    /**
     * 将User表中的unpaidedMessages字段值加1
     */
    private void unpaidedMessagesAddOne() {
        if (isFromOrder) {
            Request<String> messagesRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/users/" + StaticUserInfo.objectId, RequestMethod.PUT);
            messagesRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
            messagesRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
            messagesRequest.addHeader("Content-Type", "application/json");
            messagesRequest.addHeader("X-Bmob-Session-Token", StaticUserInfo.sessionToken);
            messagesRequest.setDefineRequestBodyForJson("{\"unpaidedMessages\":" + (StaticUserInfo.unpaidedMessages + 1) + "}");
            NoHttpServer.getInstance().add(PayActivity.this, ConstantPool.ADD_UNPAIDEDMESSAGES_WHAT, messagesRequest, this, true);
        }
    }


    @OnClick({R.id.pay_titlebar_back, R.id.pay_weixin, R.id.pay_alipay, R.id.pay_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_titlebar_back:
                //保存待付款订单


                finish();
                break;
            case R.id.pay_weixin:
                mPayWeixinCheckbox.setChecked(true);
                mPayAlipayCheckbox.setChecked(false);
                payMode = 0;

                break;
            case R.id.pay_alipay:
                mPayAlipayCheckbox.setChecked(true);
                mPayWeixinCheckbox.setChecked(false);
                payMode = 1;

                break;
            case R.id.pay_pay:
                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.PAY_WHAT:
                break;
            case ConstantPool.ADD_UNPAIDEDMESSAGES_WHAT:
                StaticUserInfo.unpaidedMessages++;
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.PAY_WHAT:
                initSaveDataToOrder();
                unpaidedMessagesAddOne();
                break;
            case ConstantPool.ADD_UNPAIDEDMESSAGES_WHAT:

                break;
        }
    }
}
