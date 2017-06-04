package www.huangheng.site.grouppurchase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.custom.view.MySwipeRefreshLayout;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;
import www.huangheng.site.grouppurchase.entity.UserInfo;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.utils.HttpRequestUtils;
import www.huangheng.site.grouppurchase.utils.SharedPreferencesUtils;
import www.huangheng.site.grouppurchase.view.CollectActivity;
import www.huangheng.site.grouppurchase.view.HistoryActivity;
import www.huangheng.site.grouppurchase.view.LoginActivity;
import www.huangheng.site.grouppurchase.view.PaidedActivity;
import www.huangheng.site.grouppurchase.view.PersonageActivity;
import www.huangheng.site.grouppurchase.view.TicketActivity;
import www.huangheng.site.grouppurchase.view.UnPaidedActivity;

import static www.huangheng.site.grouppurchase.entity.StaticUserInfo.username;


/**
 * 我的
 */

public class TabMineFragment extends Fragment implements NoHttpListener<String>, SwipeRefreshLayout.OnRefreshListener {

    //刷新
    @BindView(R.id.mine_refresh)
    MySwipeRefreshLayout mMineRefresh;

    //登录后
    @BindView(R.id.mine_login_after_avartar)
    SimpleDraweeView mMineLoginAfterAvartar;
    @BindView(R.id.mine_login_after_username)
    TextView mMineLoginAfterUsername;
    @BindView(R.id.mine_login_after_level)
    ImageView mMineLoginAfterLevel;


    //登录前
    @BindView(R.id.mine_login_before)
    RelativeLayout mMineLoginBefore;

    //登录后
    @BindView(R.id.mine_Login_after)
    RelativeLayout mMineLoginAfter;

    //拉手券、收藏、最近浏览
    @BindView(R.id.mine_login_ticket_count_textview)
    TextView mMineLoginTicketCountTextview;
    @BindView(R.id.mine_login_collect_count_textview)
    TextView mMineLoginCollectCountTextview;
    @BindView(R.id.mine_login_recently_count_textview)
    TextView mMineLoginRecentlyCountTextview;

    //待付款、已付款、待评价
    @BindView(R.id.mine_unpaided_message)
    TextView mMineUnpaidedMessage;
    @BindView(R.id.mine_paided_message)
    TextView mMinePaidedMessage;
    @BindView(R.id.mine_uncommented_message)
    TextView mMineUncommentedMessage;

    //我的资产
    @BindView(R.id.mine_asset_balance_count_textview)
    TextView mMineAssetBalanceCountTextview;
    @BindView(R.id.mine_asset_voucher_count_textview)
    TextView mMineAssetVoucherCountTextview;

    //抽奖
    @BindView(R.id.mine_mysurprise_count_textview)
    TextView mMineMysurpriseCountTextview;


    private Handler mHandler;
    private boolean isRefreshSucceed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initialize();
    }

    /**
     * 初始化
     */
    private void initialize() {
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_mine, container, false);
        ButterKnife.bind(this, view);
        initRefresh();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Map<String, String> userInfoMap = SharedPreferencesUtils.getInstance().getUserInfoFromSP(getActivity());
        if (!TextUtils.isEmpty(userInfoMap.get("UserName"))) {
            setUserInfoToShow(StaticUserInfo.getInstance());
            HttpRequestUtils.getInstance().requestOfUser(StaticUserInfo.getInstance().getObjectId(), this);
        }
    }


    /**
     * 初始化刷新
     */
    private void initRefresh() {
        mMineRefresh.setColorSchemeResources(R.color.swiperefresh_color_one,
                R.color.swiperefresh_color_two,
                R.color.swiperefresh_color_three,
                R.color.swiperefresh_color_four);
        mMineRefresh.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {

        if (!"000000".equals(StaticUserInfo.getInstance().getObjectId())) {
            HttpRequestUtils.getInstance().requestOfUser(StaticUserInfo.getInstance().getObjectId(), this);
            Runnable runnable = () -> {
                if (mMineRefresh.isRefreshing() || !isRefreshSucceed) {
                    mMineRefresh.setRefreshing(false);
                    Toast.makeText(getActivity(), "连接失败，请检查网络是否正常", Toast.LENGTH_SHORT).show();
                }
            };
            mHandler.postDelayed(runnable, 5000);
        } else {
            mHandler.postDelayed(() -> mMineRefresh.setRefreshing(false), 1000);
            Toast.makeText(getActivity(), "您还没有登录哦", Toast.LENGTH_SHORT).show();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StaticUserInfo staticUserInfo) {
        if (staticUserInfo.isSucceed()) {
            setUserInfoToShow(staticUserInfo);
        } else {
            setUserNoLoginView();
        }
    }


    /**
     * 用户退出登录
     */
    private void setUserNoLoginView() {

        mMineLoginAfter.setVisibility(View.GONE);
        mMineLoginBefore.setVisibility(View.VISIBLE);

        mMineLoginTicketCountTextview.setVisibility(View.GONE);
        mMineLoginCollectCountTextview.setVisibility(View.GONE);
        mMineLoginRecentlyCountTextview.setVisibility(View.GONE);

        mMineAssetBalanceCountTextview.setVisibility(View.GONE);
        mMineAssetVoucherCountTextview.setVisibility(View.GONE);

        mMineUnpaidedMessage.setVisibility(View.GONE);
        mMinePaidedMessage.setVisibility(View.GONE);
        mMineUncommentedMessage.setVisibility(View.GONE);

        mMineMysurpriseCountTextview.setVisibility(View.GONE);

    }


    /**
     * 设置用户信息
     */
    private void setUserInfoToShow(StaticUserInfo staticUserInfo) {

        mMineLoginBefore.setVisibility(View.GONE);
        mMineLoginAfter.setVisibility(View.VISIBLE);

        mMineLoginTicketCountTextview.setVisibility(View.VISIBLE);
        mMineLoginCollectCountTextview.setVisibility(View.VISIBLE);
        mMineLoginRecentlyCountTextview.setVisibility(View.VISIBLE);

        mMineAssetBalanceCountTextview.setVisibility(View.VISIBLE);
        mMineAssetVoucherCountTextview.setVisibility(View.VISIBLE);


        //登录后
        mMineLoginAfterAvartar.setImageURI(staticUserInfo.getAvartarUrl());
        mMineLoginAfterUsername.setText(staticUserInfo.getUsername());

        switch (staticUserInfo.getAccountLevel()) {
            case 0:
                mMineLoginAfterLevel.setImageResource(R.drawable.icon_mine_login_level_0);
                break;
            case 1:
                mMineLoginAfterLevel.setImageResource(R.drawable.icon_mine_login_level_1);
                break;
            case 2:
                mMineLoginAfterLevel.setImageResource(R.drawable.icon_mine_login_level_2);
                break;
        }


        //拉手券、收藏、最近浏览
        mMineLoginTicketCountTextview.setText(staticUserInfo.getTicketCount() + "");
        mMineLoginCollectCountTextview.setText(staticUserInfo.getCollectCount() + "");
        mMineLoginRecentlyCountTextview.setText(staticUserInfo.getRecentlyCount() + "");

        //待付款、已付款、待评价
        if (staticUserInfo.getUnpaidedMessages() != 0) {
            mMineUnpaidedMessage.setVisibility(View.VISIBLE);
            mMineUnpaidedMessage.setText(staticUserInfo.getUnpaidedMessages() + "");
        } else {
            mMineUnpaidedMessage.setVisibility(View.GONE);
        }

        if (staticUserInfo.getPaidedMessages() != 0) {
            mMinePaidedMessage.setVisibility(View.VISIBLE);
            mMinePaidedMessage.setText(staticUserInfo.getPaidedMessages() + "");
        } else {
            mMinePaidedMessage.setVisibility(View.GONE);
        }

        if (staticUserInfo.getUncommentedMessages() != 0) {
            mMineUncommentedMessage.setVisibility(View.VISIBLE);
            mMineUncommentedMessage.setText(staticUserInfo.getUncommentedMessages() + "");
        } else {
            mMineUncommentedMessage.setVisibility(View.GONE);
        }

        //我的资产
        mMineAssetBalanceCountTextview.setText(staticUserInfo.getBalanceCount() + "元");
        mMineAssetVoucherCountTextview.setText(staticUserInfo.getVoucherCount() + "张");

        //抽奖
        mMineMysurpriseCountTextview.setText(staticUserInfo.getSurpriseCount() + "");

    }


    @OnClick(R.id.mine_Login_after)
    public void onLoginAfterClicked() {
        Intent intent = new Intent(getActivity(), PersonageActivity.class);
        intent.putExtra(ConstantPool.USERNAME, username);
        startActivity(intent);
    }

    @OnClick({R.id.mine_login_click_textview, R.id.mine_login_ticket, R.id.mine_login_collect, R.id.mine_login_recently})
    public void onLoginViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_login_click_textview:
                jumpToLoginActivity();
                break;
            case R.id.mine_login_ticket:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                } else {
                    Intent intent = new Intent(getActivity(), TicketActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_login_collect:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                } else {
                    Intent intent = new Intent(getActivity(), CollectActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_login_recently:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                } else {
                    Intent intent = new Intent(getActivity(), HistoryActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }


    @OnClick({R.id.mine_unpaided, R.id.mine_paided, R.id.mine_uncommented})
    public void onOrderViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_unpaided:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                } else {
                    Intent intent = new Intent(getActivity(), UnPaidedActivity.class);
                    intent.putExtra(ConstantPool.USERNAME, username);
                    startActivity(intent);
                }
                break;
            case R.id.mine_paided:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                } else {
                    Intent intent = new Intent(getActivity(), PaidedActivity.class);
                    intent.putExtra("WHICH_ONE_TO_BE_SELECT",0);
                    startActivity(intent);
                }
                break;
            case R.id.mine_uncommented:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                } else {
                    Intent intent = new Intent(getActivity(), PaidedActivity.class);
                    intent.putExtra("WHICH_ONE_TO_BE_SELECT",1);
                    startActivity(intent);
                }
                break;
        }
    }

    @OnClick({R.id.mine_asset_balance, R.id.mine_asset_voucher})
    public void onAssetViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_asset_balance:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                }
                break;
            case R.id.mine_asset_voucher:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                }
                break;
        }
    }

    @OnClick({R.id.mine_mycomment, R.id.mine_mysurprise, R.id.mine_store})
    public void onOtherViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_mycomment:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                }
                break;
            case R.id.mine_mysurprise:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                }
                break;
            case R.id.mine_store:
                if (!StaticUserInfo.getInstance().isSucceed()) {
                    jumpToLoginActivity();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.USER_REQUEST_WHAT:

                Gson gson = new Gson();
                UserInfo userInfo = gson.fromJson(response.get(), UserInfo.class);
                StaticUserInfo.getInstance().setUserInfo(userInfo, false);
                setUserInfoToShow(StaticUserInfo.getInstance());

                isRefreshSucceed = true;
                if (mMineRefresh.isRefreshing()) {
                    mMineRefresh.setRefreshing(false);
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.USER_REQUEST_WHAT:
                isRefreshSucceed = false;
                break;
        }
    }


    private void jumpToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
