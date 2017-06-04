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
 * 浏览记录
 */

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.history_load_failure)
    RelativeLayout mHistoryLoadFailure;

    @BindView(R.id.history_listview)
    ListView mHistoryListview;

    @BindView(R.id.history_nohistory)
    LinearLayout mHistoryNohistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        if (NetWorkUtils.getInstance().isNetworkConnected(this)) {
            mHistoryLoadFailure.setVisibility(View.GONE);
        } else {
            mHistoryLoadFailure.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.history_titlebar_back, R.id.history_load_failure, R.id.history_stroll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.history_titlebar_back:
                finish();
                break;
            case R.id.history_load_failure:
                if (NetWorkUtils.getInstance().isNetworkConnected(this)) {
                    mHistoryLoadFailure.setVisibility(View.GONE);
                    //网络请求

                } else {
                    mHistoryLoadFailure.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.history_stroll:
                break;
        }
    }
}
