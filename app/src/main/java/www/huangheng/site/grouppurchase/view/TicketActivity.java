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
 * 拉手券
 */

public class TicketActivity extends AppCompatActivity {

    @BindView(R.id.ticket_load_failure)
    RelativeLayout mTicketLoadFailure;

    @BindView(R.id.ticket_listview)
    ListView mTicketListview;

    @BindView(R.id.ticket_noticket)
    LinearLayout mTicketNoticket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        if (NetWorkUtils.getInstance().isNetworkConnected(this)) {
            mTicketLoadFailure.setVisibility(View.GONE);
        } else {
            mTicketLoadFailure.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.ticket_titlebar_back, R.id.ticket_load_failure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ticket_titlebar_back:
                finish();
                break;
            case R.id.ticket_load_failure:
                if (NetWorkUtils.getInstance().isNetworkConnected(this)) {
                    mTicketLoadFailure.setVisibility(View.GONE);

                    //网络请求


                } else {
                    mTicketLoadFailure.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
