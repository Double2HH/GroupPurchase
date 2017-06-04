package www.huangheng.site.grouppurchase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.MyViewPagerAdapter;
import www.huangheng.site.grouppurchase.entity.DetailInfo;

/**
 * 图片画廊
 */

public class ImageGalleryActivity extends AppCompatActivity {

    @BindView(R.id.imagegallery_viewpager)
    ViewPager mImagegalleryViewpager;

    private List<View> mViewList;
    private DetailInfo.ResultBean mResultBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagegallery);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {

        mResultBean = (DetailInfo.ResultBean) getIntent().getSerializableExtra("Detail_Images");
        List<String> detail_imags = mResultBean.getDetail_imags();
        Toast.makeText(this, "detail_images.size() = "+detail_imags.size(), Toast.LENGTH_SHORT).show();
        mViewList = new ArrayList<>();

        for (int i = 0, length = detail_imags.size(); i < length; i++) {
            View view = getLayoutInflater().inflate(R.layout.item_imagegallery_viewpager, null);
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.item_imagegallery_draweeview);
            simpleDraweeView.setImageURI("https://www.baidu.com/img/bd_logo1.png");
            mViewList.add(view);

        }

        mImagegalleryViewpager.setAdapter(new MyViewPagerAdapter(mViewList));
    }


}
