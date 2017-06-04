package www.huangheng.site.grouppurchase.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.custom.view.ObservableScrollView;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.GoodDetailsInfo;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpServer;

/**
 * 猜你喜欢详情界面
 */

public class DetailActivity extends AppCompatActivity implements NoHttpListener<String> {


    //标题栏
    @BindView(R.id.detail_titlebar_product)
    TextView mDetailTitlebarProduct;
    @BindView(R.id.detail_titlebar_back)
    ImageView mDetailTitlebarBack;
    @BindView(R.id.detail_titlebar_favorite)
    ImageView mDetailTitlebarFavorite;
    @BindView(R.id.detail_titlebar_share)
    ImageView mDetailTitlebarShare;
    @BindView(R.id.detail_titlebar)
    RelativeLayout mDetailTitlebar;

    //滚动组件
    @BindView(R.id.detail_details)
    ObservableScrollView mDetailDetails;

    //商品图片
    @BindView(R.id.detail_details_image)
    SimpleDraweeView mDetailDetailsImage;

    //商品信息
    @BindView(R.id.detail_details_product)
    TextView mDetailDetailsProduct;
    @BindView(R.id.detail_details_sold)
    TextView mDetailDetailsSold;
    @BindView(R.id.detail_details_subtitle)
    TextView mDetailDetailsSubtitle;

    //随时、过期退，免预约
    @BindView(R.id.detail_details_alltimes)
    LinearLayout mDetailDetailsAlltimes;
    @BindView(R.id.detail_details_pastdue)
    LinearLayout mDetailDetailsPastdue;
    @BindView(R.id.detail_details_freeappointment)
    LinearLayout mDetailDetailsFreeappointment;

    //评价
    @BindView(R.id.detail_details_comment_ratingbar)
    RatingBar mDetailDetailsCommentRatingbar;
    @BindView(R.id.detail_details_comment_score)
    TextView mDetailDetailsCommentScore;
    @BindView(R.id.detail_details_comment_rightarrow)
    ImageView mDetailDetailsCommentRightarrow;
    @BindView(R.id.detail_details_comment_text)
    TextView mDetailDetailsCommentText;

    //商家信息
    @BindView(R.id.detail_details_merchant_product)
    TextView mDetailDetailsMerchantProduct;
    @BindView(R.id.detail_details_merchant_address)
    TextView mDetailDetailsMerchantAddress;
    @BindView(R.id.detail_details_merchant_time)
    TextView mDetailDetailsMerchantTime;
    @BindView(R.id.detail_details_merchant_distance)
    TextView mDetailDetailsMerchantDistance;

    //详情、提示
    @BindView(R.id.detail_details_details_webview)
    WebView mDetailDetailsDetailsWebview;
    @BindView(R.id.detail_details_tips_webview)
    WebView mDetailDetailsTipsWebview;

    //底部购买栏
    @BindView(R.id.detail_buy_buy)
    Button mDetailBuyBuy;
    @BindView(R.id.detail_buy_price_after)
    TextView mDetailBuyPriceAfter;
    @BindView(R.id.detail_buy_price_before)
    TextView mDetailBuyPriceBefore;


    private int goodsId;
    private String objectId;
    private String product;
    private String distance;
    private String title;
    private int priceAfter;
    private int priceBefore;
    private int sold;

    private boolean alltimes = false;
    private boolean pastdue = false;
    private boolean freeappointment = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getDataFromGoodsList(getIntent());
        setDataFromGoodsList();

        requestData(goodsId);
        initScrollListener();

    }

    /**
     * 获取猜你喜欢列表传递过来的数据
     */
    private void getDataFromGoodsList(Intent intent) {
        objectId = intent.getStringExtra(ConstantPool.OBJECTID);
        goodsId = intent.getIntExtra(ConstantPool.GOODSID, 0);
        product = intent.getStringExtra(ConstantPool.PRODUCT);
        distance = intent.getStringExtra(ConstantPool.DISTANCE);
        title = intent.getStringExtra(ConstantPool.TITLE);
        priceAfter = intent.getIntExtra(ConstantPool.PRICEAFTER, 0);
        priceBefore = intent.getIntExtra(ConstantPool.PRICEBEFORE, 0);
        sold = intent.getIntExtra(ConstantPool.SOLD, 0);
    }

    /**
     * 设置猜你喜欢列表传递过来的数据
     */
    private void setDataFromGoodsList() {
        //商品信息
        mDetailDetailsProduct.setText(product);
        mDetailDetailsSold.setText("已售 " + sold);
        mDetailDetailsSubtitle.setText(title);
        //mDetailDetailsSubtitle.setText(title.substring(title.charAt(']'), title.length()));

        //商家信息
        mDetailDetailsMerchantProduct.setText(product);

        //价格
        mDetailBuyPriceAfter.setText("¥ " + priceAfter);
        mDetailBuyPriceBefore.setText("¥ " + priceBefore);
        mDetailBuyPriceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        //距离
        mDetailDetailsMerchantDistance.setText(distance);
    }

    /**
     * 数据请求
     */
    private void requestData(int goodsId) {
        //猜你喜欢的请求
        String urlEncode = null;
        try {
            urlEncode = URLEncoder.encode("{\"goodsId\":" + goodsId + "}", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/classes/GoodDetails?where=" + urlEncode, RequestMethod.GET);
        stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
        stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
        NoHttpServer.getInstance().add(DetailActivity.this, ConstantPool.GOODDETAILS_WHAT, stringRequest, this, true);
    }

    /**
     * 初始化滚动监听
     */
    private void initScrollListener() {

        ViewTreeObserver viewTreeObserver = mDetailTitlebar.getViewTreeObserver();

        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                mDetailTitlebar.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final int height = mDetailDetailsImage.getHeight();

                mDetailDetails.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                        if (y <= height) {
                            mDetailTitlebarProduct.setVisibility(View.GONE);
                            mDetailTitlebar.setBackgroundColor(Color.argb(0, 0, 0, 0));
                        } else if (y > height) {
                            mDetailTitlebarProduct.setVisibility(View.VISIBLE);
                            mDetailTitlebarProduct.setText(product);
                            mDetailTitlebarProduct.setTextColor(Color.argb(255, 0, 0, 0));
                            mDetailTitlebar.setBackgroundColor(Color.argb(255, 255, 255, 255));
                        }
                    }
                });
            }
        });

    }


    /**
     * 初始化标题栏喜欢按钮
     */
    private void initTitlebarFavorite(boolean isFavorite) {
        if (isFavorite) mDetailTitlebarFavorite.setImageResource(R.drawable.icon_detail_favorite);
    }

    /**
     * 初始化图片
     */
    private void initImages(String urlString) {
        mDetailDetailsImage.setImageURI(urlString);
    }

    /**
     * 初始化服务显示视图
     */
    private void initServiceView(boolean alltimes, boolean pastdue, boolean freeappointment) {

        this.alltimes = alltimes;
        this.pastdue = alltimes;
        this.freeappointment = freeappointment;

        if (!alltimes) mDetailDetailsAlltimes.setVisibility(View.GONE);
        if (!pastdue) mDetailDetailsPastdue.setVisibility(View.GONE);
        if (!freeappointment) mDetailDetailsFreeappointment.setVisibility(View.GONE);
    }

    /**
     * 初始化评价
     */
    private void initCommentDataAndView(int commentCount, float rating, float score) {
        if (commentCount != 0) {
            mDetailDetailsCommentText.setText(commentCount + "人评价");
            mDetailDetailsCommentRightarrow.setVisibility(View.VISIBLE);
            mDetailDetailsCommentRatingbar.setRating(rating);
            mDetailDetailsCommentScore.setVisibility(View.VISIBLE);
            mDetailDetailsCommentScore.setText(score + "分");
        }
    }

    /**
     * 初始化商家信息
     */
    private void initMerchantData(String address, String time) {
        mDetailDetailsMerchantAddress.setText(address);
        mDetailDetailsMerchantTime.setText(time);
    }

    /**
     * 初始化本单详情和温馨提示页面
     */
    private void initDetailsAndTipsWeb(String details, String tips) {
        mDetailDetailsDetailsWebview.loadDataWithBaseURL("about:blank", details, "text/html", "UTF-8", null);
        mDetailDetailsTipsWebview.loadDataWithBaseURL("about:blank", tips, "text/html", "UTF-8", null);
    }


    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.GOODDETAILS_WHAT:
                Gson gson = new Gson();
                GoodDetailsInfo goodDetailsInfo = gson.fromJson(response.get(), GoodDetailsInfo.class);
                GoodDetailsInfo.ResultsBean resultsBean = goodDetailsInfo.getResults().get(0);

                initTitlebarFavorite(resultsBean.isIsFavorite());
                initImages(resultsBean.getImagesUrl().get(0));
                initServiceView(resultsBean.getService().get(0), resultsBean.getService().get(1), resultsBean.getService().get(2));
                initCommentDataAndView(resultsBean.getCommentCount(), resultsBean.getRatingBar(), resultsBean.getScore());
                initMerchantData(resultsBean.getAddress(), resultsBean.getTime());
                initDetailsAndTipsWeb(resultsBean.getDetails(), resultsBean.getTips());

                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.GOODDETAILS_WHAT:
                Toast.makeText(this, "failed id:" + goodsId, Toast.LENGTH_SHORT).show();

                break;
        }
    }


    @OnClick({R.id.detail_titlebar_back, R.id.detail_titlebar_favorite, R.id.detail_titlebar_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_titlebar_back:
                finish();
                break;
            case R.id.detail_titlebar_favorite:

                break;
            case R.id.detail_titlebar_share:
                showShare();
                break;
        }
    }

    /**
     * 标题栏分享
     */
    private void showShare() {

        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("分享");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("评论");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }


    @OnClick(R.id.detail_buy_buy)
    public void onViewClicked() {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(ConstantPool.OBJECTID, objectId);
        intent.putExtra(ConstantPool.PRODUCT, product);
        intent.putExtra(ConstantPool.PRICEAFTER, priceAfter);

        intent.putExtra(ConstantPool.ALLTIMES, alltimes);
        intent.putExtra(ConstantPool.PASTDUE, pastdue);
        intent.putExtra(ConstantPool.FREEAPPOINTMENT, freeappointment);

        startActivity(intent);
    }
}
