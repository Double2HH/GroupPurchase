package www.huangheng.site.grouppurchase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.GuessWhatYouLikeAdapter;
import www.huangheng.site.grouppurchase.adapter.HomepageClassifyAdapter;
import www.huangheng.site.grouppurchase.adapter.MyViewPagerAdapter;
import www.huangheng.site.grouppurchase.custom.view.Indicator;
import www.huangheng.site.grouppurchase.custom.view.MySwipeRefreshLayout;
import www.huangheng.site.grouppurchase.entity.ClassifyInfo;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.GoodsListInfo;
import www.huangheng.site.grouppurchase.entity.HomepageInfo;
import www.huangheng.site.grouppurchase.entity.HomepageStaticInfo;
import www.huangheng.site.grouppurchase.listener.GuessWhatYouLikeListener;
import www.huangheng.site.grouppurchase.listener.ViewPagerChangeListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.utils.HttpRequestUtils;
import www.huangheng.site.grouppurchase.view.CityListActivity;
import www.huangheng.site.grouppurchase.view.SearchActivity;
import www.huangheng.site.grouppurchase.view.WebViewActivity;

import static www.huangheng.site.grouppurchase.entity.HomepageStaticInfo.classifyUrl;


/**
 * 碎片——主页
 */

public class TabHomepageFragment extends Fragment implements View.OnClickListener, NoHttpListener<String>, SwipeRefreshLayout.OnRefreshListener {


    //用于改变标题栏背景
    private SparseArray recordSp = new SparseArray(0);
    private int mCurrentfirstVisibleItem = 0;

    //标题栏
    RelativeLayout mHomepageTitlebar;

    //刷新
    MySwipeRefreshLayout mHomepageRefresh;
    private boolean isHomepageViewRefresh = false;
    private boolean isHomepageLikeRefresh = false;

    //城市
    LinearLayout mHomepageTitlebarLocation;
    TextView mHomepageTitlebarLocationCity;

    //搜索框
    LinearLayout mHomepageTitlebarSearch;

    //二维码、消息提醒
    ImageView mHomepageTitlebarQr;
    ImageView mHomepageTitlebarMessage;
    TextView mHomepageTitlebarMessageCount;

    //猜你喜欢列表
    ListView mHomepageGuesswhatyoulikeListview;

    //广告条
    @BindView(R.id.homepage_banner_viewpager)
    ViewPager mHomepageBannerViewpager;
    @BindView(R.id.homepage_banner_indicator)
    Indicator mHomepageBannerIndicator;

    //商品分类
    @BindView(R.id.homepage_classes_viewpager)
    ViewPager mHomepageClassesViewpager;
    @BindView(R.id.homepage_classes_indicator)
    Indicator mHomepageClassesIndicator;

    //头条
    @BindView(R.id.homepage_headline)
    ViewSwitcher mHomepageHeadline;

    //广告
    @BindView(R.id.homepage_ad_top_left_text_1)
    TextView mHomepageAdTopLeftText1;
    @BindView(R.id.homepage_ad_top_left_text_2)
    TextView mHomepageAdTopLeftText2;
    @BindView(R.id.homepage_ad_top_left_draweeview)
    SimpleDraweeView mHomepageAdTopLeftDraweeview;

    @BindView(R.id.homepage_ad_top_right_text_1)
    TextView mHomepageAdTopRightText1;
    @BindView(R.id.homepage_ad_top_right_text_2)
    TextView mHomepageAdTopRightText2;
    @BindView(R.id.homepage_ad_top_right_draweeview)
    SimpleDraweeView mHomepageAdTopRightDraweeview;

    @BindView(R.id.homepage_ad_middle_left_text_1)
    TextView mHomepageAdMiddleLeftText1;
    @BindView(R.id.homepage_ad_middle_left_text_2)
    TextView mHomepageAdMiddleLeftText2;
    @BindView(R.id.homepage_ad_middle_left_draweeview)
    SimpleDraweeView mHomepageAdMiddleLeftDraweeview;

    @BindView(R.id.homepage_ad_middle_right_text_1)
    TextView mHomepageAdMiddleRightText1;
    @BindView(R.id.homepage_ad_middle_right_text_2)
    TextView mHomepageAdMiddleRightText2;
    @BindView(R.id.homepage_ad_middle_right_draweeview)
    SimpleDraweeView mHomepageAdMiddleRightDraweeview;

    @BindView(R.id.homepage_ad_bottom_left_text_1)
    TextView mHomepageAdBottomLeftText1;
    @BindView(R.id.homepage_ad_bottom_left_text_2)
    TextView mHomepageAdBottomLeftText2;
    @BindView(R.id.homepage_ad_bottom_left_draweeview)
    SimpleDraweeView mHomepageAdBottomLeftDraweeview;

    @BindView(R.id.homepage_ad_bottom_middle_text_1)
    TextView mHomepageAdBottomMiddleText1;
    @BindView(R.id.homepage_ad_bottom_middle_text_2)
    TextView mHomepageAdBottomMiddleText2;
    @BindView(R.id.homepage_ad_bottom_middle_draweeview)
    SimpleDraweeView mHomepageAdBottomMiddleDraweeview;

    @BindView(R.id.homepage_ad_bottom_right_text_1)
    TextView mHomepageAdBottomRightText1;
    @BindView(R.id.homepage_ad_bottom_right_text_2)
    TextView mHomepageAdBottomRightText2;
    @BindView(R.id.homepage_ad_bottom_right_draweeview)
    SimpleDraweeView mHomepageAdBottomRightDraweeview;

    //整个首页视图
    private View mView;

    //ListView的HeaderView
    private View mHeaderView;

    //商品分类视图
    private List<View> mViewList;

    private Handler mHandler;
    private Runnable mHeadlineRunnable;
    private Runnable mBannerRunnable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    /**
     * 初始化
     */
    private void initialize() {
        mViewList = new ArrayList<>();
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //防止重新加载数据
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_tab_homepage, container, false);

            mHomepageGuesswhatyoulikeListview = (ListView) mView.findViewById(R.id.homepage_guesswhatyoulike_listview);

            //标题栏
            mHomepageTitlebar = (RelativeLayout) mView.findViewById(R.id.homepage_titlebar);

            //刷新
            mHomepageRefresh = (MySwipeRefreshLayout) mView.findViewById(R.id.homepage_swipeRefreshLayout);

            //城市
            mHomepageTitlebarLocation = (LinearLayout) mView.findViewById(R.id.homepage_titlebar_location);
            mHomepageTitlebarLocation.setOnClickListener(this);
            mHomepageTitlebarLocationCity = (TextView) mView.findViewById(R.id.homepage_titlebar_location_city);

            //搜索框
            mHomepageTitlebarSearch = (LinearLayout) mView.findViewById(R.id.homepage_titlebar_search);
            mHomepageTitlebarSearch.setOnClickListener(this);

            //二维码、消息提醒
            mHomepageTitlebarQr = (ImageView) mView.findViewById(R.id.homepage_titlebar_qr);
            mHomepageTitlebarQr.setOnClickListener(this);
            mHomepageTitlebarMessage = (ImageView) mView.findViewById(R.id.homepage_titlebar_message);
            mHomepageTitlebarMessage.setOnClickListener(this);
            mHomepageTitlebarMessageCount = (TextView) mView.findViewById(R.id.homepage_titlebar_message_count);



            initRefreshLayout();
            initHeaderView();
            initDataAndView();

            if (!HomepageStaticInfo.isSucceed) {
                Toast.makeText(getActivity(), "连接失败，请检查网络是否正常", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequestUtils.getInstance().requestOfHomepage("https://api.bmob.cn/1/classes/HomepageView", TabHomepageFragment.this);
                    }
                }, 5000);
            }

            HttpRequestUtils.getInstance().requestOfLike("https://api.bmob.cn/1/classes/GoodsListInfo", this);



            mHomepageGuesswhatyoulikeListview.addHeaderView(mHeaderView, null, false);

        }
        ButterKnife.bind(this, mView);
        return mView;
    }

    /**
     * 初始化数据和视图
     */
    private void initDataAndView() {
        initBannerDataAndView(HomepageStaticInfo.bannerImageUrl, HomepageStaticInfo.bannerUrl);
        initClassifyDataAndView(HomepageStaticInfo.classifyImageUrl, HomepageStaticInfo.classifyString, classifyUrl);
        initHeadlineDataAndView(HomepageStaticInfo.headlineString, HomepageStaticInfo.headlineUrl);
        initADDataAndView(HomepageStaticInfo.adString, HomepageStaticInfo.adImageUrl);
    }


    /**
     * 初始化下拉刷新控件
     */
    private void initRefreshLayout() {
        mHomepageRefresh.setColorSchemeResources(R.color.swiperefresh_color_one,
                R.color.swiperefresh_color_two,
                R.color.swiperefresh_color_three,
                R.color.swiperefresh_color_four);
        mHomepageRefresh.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        isHomepageViewRefresh = true;
        isHomepageLikeRefresh = true;

        mHandler.removeCallbacks(mBannerRunnable);
        mHandler.removeCallbacks(mHeadlineRunnable);

        HttpRequestUtils.getInstance().requestOfHomepage("https://api.bmob.cn/1/classes/HomepageView", TabHomepageFragment.this);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mHomepageRefresh.isRefreshing() || isHomepageLikeRefresh || isHomepageViewRefresh) {
                    Toast.makeText(getActivity(), "连接失败，请检查网络是否正常", Toast.LENGTH_SHORT).show();
                    mHomepageRefresh.setRefreshing(false);
                    mHandler.postDelayed(mBannerRunnable, 6000);
                    mHandler.postDelayed(mHeadlineRunnable, 5000);
                }
            }
        }, 5000);

    }


    /**
     * 初始化ListView的HeaderView
     */
    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.homepage_headerview, null);
        ButterKnife.bind(this, mHeaderView);
    }

    /**
     * 初始化广告条（有疑问）
     */
    private void initBannerDataAndView(final List<String> bannerImageUrl, final List<String> bannerUrl) {
        //初始化数据
        List<View> bannerViewList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_homepage_banner, null);
            SimpleDraweeView draweeView = (SimpleDraweeView) view.findViewById(R.id.item_homepage_banner_draweeview);

            if (HomepageStaticInfo.isSucceed) {
                draweeView.setImageURI(bannerImageUrl.get(i));
                final int finalI = i;
                draweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击广告
                        jumpToWebViewActivity(bannerUrl.get(finalI));
                    }
                });
            }
            bannerViewList.add(view);
        }

        //初始化视图
        mHomepageBannerViewpager.setAdapter(new MyViewPagerAdapter(bannerViewList));

        if (HomepageStaticInfo.isSucceed) {
            mHomepageBannerViewpager.addOnPageChangeListener(new ViewPagerChangeListener(mHomepageBannerIndicator));

            //初始化广告条滚动  问题：mHomepageBannerViewpager.getChildCount()值不稳定
            mBannerRunnable = new Runnable() {
                @Override
                public void run() {
                    int currentItem = mHomepageBannerViewpager.getCurrentItem();
                    mHomepageBannerViewpager.setCurrentItem(++currentItem % 4, true);
                    mHandler.postDelayed(this, 6000);
                }
            };
            mHandler.postDelayed(mBannerRunnable, 6000);
        }
    }

    /**
     * 初始化商品分类
     */
    private void initClassifyDataAndView(List<String> classifyImageUrl, List<String> classifyString, final List<String> classifyUrl) {

        //初始化数据
        List<ClassifyInfo> classifyListPageOne = new ArrayList<>();
        List<ClassifyInfo> classifyListPageTwo = new ArrayList<>();

        //初始化视图
        View viewOne = LayoutInflater.from(getActivity()).inflate(R.layout.item_homepage_viewpager_classify, null);
        GridView gridViewPageOne = (GridView) viewOne.findViewById(R.id.classify_gridview);

        View viewTwo = LayoutInflater.from(getActivity()).inflate(R.layout.item_homepage_viewpager_classify, null);
        GridView gridViewPageTwo = (GridView) viewTwo.findViewById(R.id.classify_gridview);

        for (int i = 0; i < 16; i++) {
            if (i < 16 / 2) {
                if (HomepageStaticInfo.isSucceed) {
                    classifyListPageOne.add(new ClassifyInfo(classifyString.get(i), classifyImageUrl.get(i)));
                } else {
                    classifyListPageOne.add(new ClassifyInfo("", ""));
                }
            } else {
                if (HomepageStaticInfo.isSucceed) {
                    classifyListPageTwo.add(new ClassifyInfo(classifyString.get(i), classifyImageUrl.get(i)));
                } else {
                    classifyListPageTwo.add(new ClassifyInfo("", ""));
                }
            }
        }

        if (HomepageStaticInfo.isSucceed) {

            gridViewPageOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //点击分类
                    jumpToWebViewActivity(classifyUrl.get(position));
                }
            });

            gridViewPageTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //点击分类
                    jumpToWebViewActivity(classifyUrl.get(position + 8));
                }
            });

            mHomepageClassesViewpager.addOnPageChangeListener(new ViewPagerChangeListener(mHomepageClassesIndicator));
        }

        mViewList.clear();

        gridViewPageOne.setAdapter(new HomepageClassifyAdapter(getActivity(), classifyListPageOne));
        mViewList.add(viewOne);

        gridViewPageTwo.setAdapter(new HomepageClassifyAdapter(getActivity(), classifyListPageTwo));
        mViewList.add(viewTwo);

        mHomepageClassesViewpager.setAdapter(new MyViewPagerAdapter(mViewList));
    }

    /**
     * 初始化头条
     */
    private void initHeadlineDataAndView(List<String> headlineString, final List<String> headlineUrl) {

        if (!HomepageStaticInfo.isSucceed) return;

        mHomepageHeadline.removeAllViews();

        final String[] headlineFirst;
        final String[] headlineSecond;

        headlineFirst = headlineString.get(0).split("-");
        headlineSecond = headlineString.get(1).split("-");

        for (int i = 0; i < 2; i++) {
            View headline = LayoutInflater.from(getActivity()).inflate(R.layout.item_homepage_headline, null);
            TextView first = (TextView) headline.findViewById(R.id.item_homepage_headline_first);
            TextView second = (TextView) headline.findViewById(R.id.item_homepage_headline_second);
            first.setText(headlineFirst[i]);
            second.setText(headlineSecond[i]);
            final int finalI1 = i;
            headline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //头条点击事件
                    jumpToWebViewActivity(headlineUrl.get(finalI1));
                }
            });
            mHomepageHeadline.addView(headline);
        }


        mHeadlineRunnable = new Runnable() {
            @Override
            public void run() {
                mHomepageHeadline.showNext();
                mHandler.postDelayed(this, 5000);
            }
        };

        mHandler.postDelayed(mHeadlineRunnable, 5000);

    }

    /**
     * 初始化广告
     */
    private void initADDataAndView(List<String> adString, List<String> adImageUrl) {

        if (!HomepageStaticInfo.isSucceed) return;

        String[] topLeft = adString.get(0).split("-");
        mHomepageAdTopLeftText1.setText(topLeft[0]);
        mHomepageAdTopLeftText2.setText(topLeft[1]);
        mHomepageAdTopLeftDraweeview.setImageURI(adImageUrl.get(0));

        String[] topRight = adString.get(1).split("-");
        mHomepageAdTopRightText1.setText(topRight[0]);
        mHomepageAdTopRightText2.setText(topRight[1]);
        mHomepageAdTopRightDraweeview.setImageURI(adImageUrl.get(1));

        String[] middleLeft = adString.get(2).split("-");
        mHomepageAdMiddleLeftText1.setText(middleLeft[0]);
        mHomepageAdMiddleLeftText2.setText(middleLeft[1]);
        mHomepageAdMiddleLeftDraweeview.setImageURI(adImageUrl.get(2));

        String[] middleRight = adString.get(3).split("-");
        mHomepageAdMiddleRightText1.setText(middleRight[0]);
        mHomepageAdMiddleRightText2.setText(middleRight[1]);
        mHomepageAdMiddleRightDraweeview.setImageURI(adImageUrl.get(3));


        String[] bottomLeft = adString.get(4).split("-");
        mHomepageAdBottomLeftText1.setText(bottomLeft[0]);
        mHomepageAdBottomLeftText2.setText(bottomLeft[1]);
        mHomepageAdBottomLeftDraweeview.setImageURI(adImageUrl.get(4));

        String[] bottomMiddle = adString.get(5).split("-");
        mHomepageAdBottomMiddleText1.setText(bottomMiddle[0]);
        mHomepageAdBottomMiddleText2.setText(bottomMiddle[1]);
        mHomepageAdBottomMiddleDraweeview.setImageURI(adImageUrl.get(5));

        String[] bottomRight = adString.get(6).split("-");
        mHomepageAdBottomRightText1.setText(bottomRight[0]);
        mHomepageAdBottomRightText2.setText(bottomRight[1]);
        mHomepageAdBottomRightDraweeview.setImageURI(adImageUrl.get(6));

    }

    /**
     * 跳转到网页Activity
     *
     * @param url url链接
     */
    private void jumpToWebViewActivity(String url) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(ConstantPool.JUMPTOWEBVIEW, url);
        startActivity(intent);
    }

    /**
     * 初始化点击事件
     */
    private void initADClickListener(List<String> adUrl, int location) {
        if (!HomepageStaticInfo.isSucceed) return;
        switch (location) {
            case 0:
                jumpToWebViewActivity(adUrl.get(0));
                break;
            case 1:
                jumpToWebViewActivity(adUrl.get(1));
                break;
            case 2:
                jumpToWebViewActivity(adUrl.get(2));
                break;
            case 3:
                jumpToWebViewActivity(adUrl.get(3));
                break;
            case 4:
                jumpToWebViewActivity(adUrl.get(4));
                break;
            case 5:
                jumpToWebViewActivity(adUrl.get(5));
                break;
            case 6:
                jumpToWebViewActivity(adUrl.get(6));
                break;
        }

    }


    @OnClick({R.id.homepage_ad_top_left, R.id.homepage_ad_top_right, R.id.homepage_ad_middle_left, R.id.homepage_ad_middle_right, R.id.homepage_ad_bottom_left, R.id.homepage_ad_bottom_middle, R.id.homepage_ad_bottom_right})
    public void onADClicked(View view) {
        switch (view.getId()) {
            case R.id.homepage_ad_top_left:
                initADClickListener(HomepageStaticInfo.adUrl, 0);
                break;
            case R.id.homepage_ad_top_right:
                initADClickListener(HomepageStaticInfo.adUrl, 1);
                break;
            case R.id.homepage_ad_middle_left:
                initADClickListener(HomepageStaticInfo.adUrl, 2);
                break;
            case R.id.homepage_ad_middle_right:
                initADClickListener(HomepageStaticInfo.adUrl, 3);
                break;
            case R.id.homepage_ad_bottom_left:
                initADClickListener(HomepageStaticInfo.adUrl, 4);
                break;
            case R.id.homepage_ad_bottom_middle:
                initADClickListener(HomepageStaticInfo.adUrl, 5);
                break;
            case R.id.homepage_ad_bottom_right:
                initADClickListener(HomepageStaticInfo.adUrl, 6);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage_titlebar_location:
                Intent citylistIntent = new Intent(getActivity(), CityListActivity.class);
                citylistIntent.putExtra(ConstantPool.CITYLIST_CITY, mHomepageTitlebarLocationCity.getText());
                startActivityForResult(citylistIntent, ConstantPool.CITYLIST_REQUEST_CODE);
                break;
            case R.id.homepage_titlebar_search:
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.homepage_titlebar_qr:
                break;
            case R.id.homepage_titlebar_message:
                break;

        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.GUESSWHATYOULIKE_WHAT:
                Gson likeGson = new Gson();
                GoodsListInfo goodsListInfo = likeGson.fromJson(response.get(), GoodsListInfo.class);
                List<GoodsListInfo.ResultsBean> goodsListInfoResults = goodsListInfo.getResults();
                mHomepageGuesswhatyoulikeListview.setAdapter(new GuessWhatYouLikeAdapter(getActivity(), goodsListInfoResults));
                mHomepageGuesswhatyoulikeListview.setOnItemClickListener(new GuessWhatYouLikeListener(getActivity(), goodsListInfoResults));

                if (mHomepageGuesswhatyoulikeListview.getFooterViewsCount() == 0 && goodsListInfo.getResults().size() != 0) {
                    View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.item_load_all, null);
                    mHomepageGuesswhatyoulikeListview.addFooterView(footerView, null, false);
                }

                isHomepageLikeRefresh = false;
                if (mHomepageRefresh.isRefreshing() && !isHomepageViewRefresh) {
                    mHomepageRefresh.setRefreshing(false);
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                }

                break;

            case ConstantPool.WELCOME_WHAT:
                Gson welcomeGson = new Gson();
                HomepageInfo homepageInfo = welcomeGson.fromJson(response.get(), HomepageInfo.class);
                if (homepageInfo.getResults().size() == 1) {
                    HomepageStaticInfo.getInstance().setStaticInfo(homepageInfo.getResults().get(0));
                    initDataAndView();
                    HttpRequestUtils.getInstance().requestOfLike("https://api.bmob.cn/1/classes/GoodsListInfo", this);
                } else {
                    HomepageStaticInfo.getInstance().setStaticInfo(null);
                }

                isHomepageViewRefresh = false;
                if (mHomepageRefresh.isRefreshing() && !isHomepageLikeRefresh) {
                    mHomepageRefresh.setRefreshing(false);
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.GUESSWHATYOULIKE_WHAT:
                mHomepageGuesswhatyoulikeListview.setAdapter(new GuessWhatYouLikeAdapter(getActivity()));
                break;

            case ConstantPool.WELCOME_WHAT:
                HomepageStaticInfo.getInstance().setStaticInfo(null);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantPool.CITYLIST_REQUEST_CODE && resultCode == ConstantPool.CITYLIST_RESULT_CODE) {
            mHomepageTitlebarLocationCity.setText(data.getStringExtra(ConstantPool.CITYLIST_CITY));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}


//    /**
//     * 初始化滚动监听
//     */
//    private void initScrollListener() {
//
//        mHomepageGuesswhatyoulikeListview.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                //firstVisibleItem--处于顶部的Item标记
//                //visibleItemCount--当前可见item数
//                //totalItemCount----总item数
//                mCurrentfirstVisibleItem = firstVisibleItem;
//                View firstView = view.getChildAt(0);
//                if (firstView != null) {
//                    ItemRecode itemRecord = (ItemRecode) recordSp.get(firstVisibleItem);
//                    if (null == itemRecord) {
//                        itemRecord = new ItemRecode();
//                    }
//                    itemRecord.height = firstView.getHeight();//获取最顶部Item的高度
//                    itemRecord.top = firstView.getTop();//获取距离顶部的距离
//                    recordSp.append(firstVisibleItem, itemRecord);//设置值
//                }
//
//                int ScrollY = getScrollY();
//
//                if (ScrollY >= 0 && ScrollY <= mHomepageBannerViewpager.getMeasuredHeight()) {
//                    mHomepageTitlebar.setBackgroundColor(Color.parseColor("#0AFFFFFF"));
//                } else if (ScrollY > mHomepageBannerViewpager.getMeasuredHeight()) {
//                    mHomepageTitlebar.setBackgroundColor(Color.parseColor("#FF621D"));
//                }
//            }
//        });
//
//    }

//    /**
//     * 获得ListView滚动的距离
//     *
//     * @return 滚动距离
//     */
//    private int getScrollY() {
//        int height = 0;
//        for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
//            ItemRecode itemRecode = (ItemRecode) recordSp.get(i);
//            //快速滑动会为空，判断一下，发现的bug
//            if (itemRecode != null) {
//                height += itemRecode.height;
//            }
//        }
//        ItemRecode itemRecode = (ItemRecode) recordSp.get(mCurrentfirstVisibleItem);
//        if (null == itemRecode) {
//            itemRecode = new ItemRecode();
//        }
//        return height - itemRecode.top;
//    }

//    /**
//     * 记录height和top
//     */
//    class ItemRecode {
//        int height = 0;
//        int top = 0;
//    }

