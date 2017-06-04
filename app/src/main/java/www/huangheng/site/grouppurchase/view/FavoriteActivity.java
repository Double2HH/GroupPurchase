package www.huangheng.site.grouppurchase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.FavoriteAdapter;
import www.huangheng.site.grouppurchase.entity.FavoriteInfo;
import www.huangheng.site.grouppurchase.utils.BmobUtils;

/**
 * 收藏
 */
public class FavoriteActivity extends AppCompatActivity {
    @BindView(R.id.login_titlebar_back)
    ImageView mIvBack;
    @BindView(R.id.favorite_listview)
    ListView mFavoriteListview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        BmobUtils.getInstance().queryAllData(new FindListener<FavoriteInfo>() {
            @Override
            public void done(List<FavoriteInfo> list, BmobException e) {
                mFavoriteListview.setAdapter(new FavoriteAdapter(FavoriteActivity.this,list));
            }
        });


    }

    @OnClick(R.id.login_titlebar_back)
    public void onClick() {
        finish();
    }
}
