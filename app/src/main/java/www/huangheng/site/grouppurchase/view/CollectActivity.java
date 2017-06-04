package www.huangheng.site.grouppurchase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.utils.NetWorkUtils;

/**
 * 收藏
 */

public class CollectActivity extends AppCompatActivity {

    //网络出现问题
    @BindView(R.id.collect_load_failure)
    RelativeLayout mCollectLoadFailure;

    @BindView(R.id.collect_listview)
    ListView mCollectListview;

    @BindView(R.id.collect_nocollection)
    LinearLayout mCollectNocollection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        if (NetWorkUtils.getInstance().isNetworkConnected(this)) {
            mCollectListview.setVisibility(View.VISIBLE);
            mCollectLoadFailure.setVisibility(View.GONE);
        } else {
            mCollectListview.setVisibility(View.GONE);
            mCollectLoadFailure.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.collect_titlebar_back, R.id.collect_load_failure,R.id.collect_stroll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collect_titlebar_back:
                finish();
                break;
            case R.id.collect_load_failure:
                if (NetWorkUtils.getInstance().isNetworkConnected(this)) {
                    mCollectListview.setVisibility(View.VISIBLE);
                    mCollectLoadFailure.setVisibility(View.GONE);

                    //网络请求


                } else {
                    mCollectListview.setVisibility(View.GONE);
                    mCollectLoadFailure.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.collect_stroll:


                break;
        }
    }


}
