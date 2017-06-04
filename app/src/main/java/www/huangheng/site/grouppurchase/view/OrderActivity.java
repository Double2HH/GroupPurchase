package www.huangheng.site.grouppurchase.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;


/**
 * 订单
 */

public class OrderActivity extends AppCompatActivity {


    @BindView(R.id.order_product)
    TextView mOrderProduct;
    @BindView(R.id.order_price)
    TextView mOrderPrice;
    @BindView(R.id.order_price_total)
    TextView mOrderPriceTotal;

    @BindView(R.id.order_voucher)
    TextView mOrderVoucher;
    @BindView(R.id.order_phone)
    TextView mOrderPhone;

    @BindView(R.id.order_alltimes)
    LinearLayout mOrderAlltimes;
    @BindView(R.id.order_pastdue)
    LinearLayout mOrderPastdue;
    @BindView(R.id.order_freeappointment)
    LinearLayout mOrderFreeappointment;

    @BindView(R.id.order_commit_totalprice)
    TextView mOrderCommitTotalprice;

    @BindView(R.id.order_sub)
    TextView mOrderSub;
    @BindView(R.id.order_amount)
    EditText mOrderAmount;
    @BindView(R.id.order_plus)
    TextView mOrderPlus;


    private String objectId;
    private String product;
    private int price;
    private int amountInt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        getAndInitDataFromDetail(getIntent());
        initSubAndPlus();
    }

    /**
     * 获得商品详情传递过来的数据
     */
    private void getAndInitDataFromDetail(Intent intent) {

        objectId = intent.getStringExtra(ConstantPool.OBJECTID);
        product = intent.getStringExtra(ConstantPool.PRODUCT);
        price = intent.getIntExtra(ConstantPool.PRICEAFTER, 0);

        mOrderProduct.setText(product);

        mOrderPrice.setText("¥ " + price);
        mOrderPriceTotal.setText("¥ " + price);
        mOrderCommitTotalprice.setText("¥ " + price);

        if (!intent.getBooleanExtra(ConstantPool.ALLTIMES, false))
            mOrderAlltimes.setVisibility(View.GONE);
        if (!intent.getBooleanExtra(ConstantPool.PASTDUE, false))
            mOrderPastdue.setVisibility(View.GONE);
        if (!intent.getBooleanExtra(ConstantPool.FREEAPPOINTMENT, false))
            mOrderFreeappointment.setVisibility(View.GONE);

    }


    /**
     * 初始化加减计算区
     */
    private void initSubAndPlus() {

        amountInt = 1;

        mOrderAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mOrderPriceTotal.setText("¥ " + 0);
                    mOrderCommitTotalprice.setText("¥ " + 0);
                } else {
                    amountInt = Integer.parseInt(s.toString());
                    if (amountInt == 0) {
                        Toast.makeText(OrderActivity.this, "最小购买数量为1", Toast.LENGTH_SHORT).show();
                    }
                    mOrderPriceTotal.setText("¥ " + (amountInt * price));
                    mOrderCommitTotalprice.setText("¥ " + (amountInt * price));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (amountInt > 1) {
                    mOrderSub.setEnabled(true);
                } else {
                    mOrderSub.setEnabled(false);
                }


            }

        });


    }


    @OnClick({R.id.order_sub, R.id.order_plus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_sub:

                if (mOrderSub.isEnabled()) {
                    amountInt -= 1;
                    mOrderAmount.setText(amountInt + "");
                    mOrderAmount.setSelection(String.valueOf(amountInt).length());
                }

                break;
            case R.id.order_plus:

                amountInt += 1;
                mOrderAmount.setText(amountInt + "");
                mOrderAmount.setSelection(String.valueOf(amountInt).length());

                break;
        }
    }

    @OnClick(R.id.order_commit_button)
    public void onViewClicked() {
        if (mOrderAmount.getText().toString().length() == 0) {
            Toast.makeText(OrderActivity.this, "请输入购买数量", Toast.LENGTH_SHORT).show();
        } else if (amountInt == 0) {
            Toast.makeText(OrderActivity.this, "最小购买数量为1", Toast.LENGTH_SHORT).show();
        } else {
            //提交订单
            Intent intent = new Intent(OrderActivity.this, PayActivity.class);
            intent.putExtra(ConstantPool.OBJECTID, objectId);
            intent.putExtra(ConstantPool.USERNAME, StaticUserInfo.username);
            intent.putExtra(ConstantPool.PRODUCT, product);
            intent.putExtra(ConstantPool.BUYAMOUNT, amountInt);
            intent.putExtra(ConstantPool.PRICEAFTER, price);
            intent.putExtra(ConstantPool.ISFROMORDER, true);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.order_titlebar_back)
    public void onTitlebarClicked() {
        finish();
    }
}
