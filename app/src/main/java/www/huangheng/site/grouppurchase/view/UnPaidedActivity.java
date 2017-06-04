package www.huangheng.site.grouppurchase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.UnPaidedAdapter;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.UnPaided;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;
import www.huangheng.site.grouppurchase.listener.UnPaidedListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpServer;
import www.huangheng.site.grouppurchase.utils.HttpRequestUtils;


/**
 * 待付款
 */

public class UnPaidedActivity extends AppCompatActivity implements NoHttpListener<String> {

    @BindView(R.id.unpaided_listview)
    ListView mUnpaidedListview;

    @BindView(R.id.unpaided_titlebar_delete)
    ImageView mUnpaidedTitlebarDelete;
    @BindView(R.id.unpaided_titlebar_delete_cancel)
    TextView mUnpaidedTitlebarDeleteCancel;

    @BindView(R.id.unpaided_delete)
    Button mUnpaidedDelete;

    @BindView(R.id.unpaided_bottom_delete)
    LinearLayout mUnpaidedBottomDelete;

    private String username;

    private UnPaidedAdapter mUnPaidedAdapter;
    private UnPaidedListener mUnPaidedListener;

    private List<String> objectIds;
    private List<UnPaided.ResultsBean> mResults;

    private int numUnPaidedBefore;
    private int numUnPaidedAfter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpaided);
        ButterKnife.bind(this);
        initialize();

    }

    @Override
    protected void onStart() {
        super.onStart();
        HttpRequestUtils.getInstance().requestOfUnPaided(this,this);
    }

    /**
     * 初始化
     */
    private void initialize() {
        username = getIntent().getStringExtra(ConstantPool.USERNAME);
        objectIds = new ArrayList<>();
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.UNPAIDED_GETDATA_WHAT:
                Gson gson = new Gson();
                UnPaided unPaided = gson.fromJson(response.get(), UnPaided.class);
                mResults = unPaided.getResults();
                mUnPaidedAdapter = new UnPaidedAdapter(this, mResults);
                mUnpaidedListview.setAdapter(mUnPaidedAdapter);
                mUnPaidedListener = new UnPaidedListener(this, mResults, objectIds, mUnpaidedDelete);
                mUnpaidedListview.setOnItemClickListener(mUnPaidedListener);
                break;

            case ConstantPool.UNPAIDED_DELETEDATA_WHAT:

                //移除数据
                if (response.get().contains("success")) {
                    numUnPaidedBefore = mResults.size();
                    for (int i = 0; i < mResults.size(); i++) {
                        if (objectIds.contains(mResults.get(i).getObjectId())) {
                            mResults.remove(i);
                            i--;
                        }
                    }
                    numUnPaidedAfter = mResults.size();
                    unpaidedMessagesSub(numUnPaidedBefore - numUnPaidedAfter);

                    mUnPaidedAdapter.notifyDataSetChanged();
                    objectIds.clear();

                    mUnPaidedListener.setNunToZero();
                    mUnpaidedDelete.setText("删除");
                    mUnpaidedDelete.setEnabled(false);

                    Toast.makeText(UnPaidedActivity.this, "订单删除成功", Toast.LENGTH_SHORT).show();

                }

                break;


            case ConstantPool.SUB_UNPAIDEDMESSAGES_WHAT:

                StaticUserInfo.unpaidedMessages -= (numUnPaidedBefore - numUnPaidedAfter);
                break;

        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.UNPAIDED_GETDATA_WHAT:
                Toast.makeText(UnPaidedActivity.this, "获取待付款商品列表失败，请重试", Toast.LENGTH_SHORT).show();
                break;
            case ConstantPool.UNPAIDED_DELETEDATA_WHAT:
                Toast.makeText(UnPaidedActivity.this, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                break;
            case ConstantPool.SUB_UNPAIDEDMESSAGES_WHAT:
                unpaidedMessagesSub(numUnPaidedBefore - numUnPaidedAfter);
                break;
        }
    }

    @OnClick({R.id.unpaided_titlebar_back, R.id.unpaided_titlebar_delete, R.id.unpaided_titlebar_delete_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.unpaided_titlebar_back:
                finish();
                break;
            case R.id.unpaided_titlebar_delete:
                mUnPaidedAdapter.showCheckBox();
                mUnpaidedTitlebarDelete.setVisibility(View.GONE);
                mUnpaidedTitlebarDeleteCancel.setVisibility(View.VISIBLE);

                mUnPaidedListener.checkOrUnCheck(true);
                mUnPaidedListener.setNunToZero();

                mUnpaidedBottomDelete.setVisibility(View.VISIBLE);
                objectIds.clear();


                break;
            case R.id.unpaided_titlebar_delete_cancel:
                mUnPaidedAdapter.cancelShowCheckBox();
                mUnpaidedTitlebarDeleteCancel.setVisibility(View.GONE);
                mUnpaidedTitlebarDelete.setVisibility(View.VISIBLE);

                mUnPaidedListener.checkOrUnCheck(false);
                mUnpaidedBottomDelete.setVisibility(View.GONE);

                break;
        }
    }

    @OnClick(R.id.unpaided_delete)
    public void onViewClicked() {
        requestDelete();
    }

    /**
     * 删除待付款商品
     */
    private void requestDelete() {
        Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/batch", RequestMethod.POST);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        stringRequest.addHeader("Content-Type", "application/json");
        stringRequest.setDefineRequestBodyForJson(getBody(objectIds));
        NoHttpServer.getInstance().add(UnPaidedActivity.this, ConstantPool.UNPAIDED_DELETEDATA_WHAT, stringRequest, this, true);
    }


    /**
     * 将User表中的unpaidedMessages字段值减 num
     */
    private void unpaidedMessagesSub(int num) {
        Request<String> messagesRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/users/" + StaticUserInfo.objectId, RequestMethod.PUT);
        messagesRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        messagesRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        messagesRequest.addHeader("Content-Type", "application/json");
        messagesRequest.addHeader("X-Bmob-Session-Token", StaticUserInfo.sessionToken);
        messagesRequest.setDefineRequestBodyForJson("{\"unpaidedMessages\":" + (StaticUserInfo.unpaidedMessages - num) + "}");
        NoHttpServer.getInstance().add(UnPaidedActivity.this, ConstantPool.SUB_UNPAIDEDMESSAGES_WHAT, messagesRequest, this, true);
    }

    /**
     * 生成删除待付款商品的Url的Body
     *
     * @return body
     */
    private String getBody(List<String> objectIds) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"requests\": [");
        for (int i = 0, len = objectIds.size(); i < len; i++) {
            stringBuilder.append("{\"method\": \"DELETE\", \"path\": \"/1/classes/UnPaided/").append(objectIds.get(i)).append("\"}");
            if (i != len - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]}");

        return stringBuilder.toString();
    }
}
