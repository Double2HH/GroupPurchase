package www.huangheng.site.grouppurchase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.adapter.MyViewPagerAdapter;

/**
 * 引导界面
 */

public class GuideActivity extends AppCompatActivity {

    private int[] mImageResources =
            new int[]{
                    R.drawable.guide_guide1,
                    R.drawable.guide_guide2,
                    R.drawable.guide_guide3,
                    R.drawable.guide_guide4};

    private Button guide_start_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initialize();

    }

    private void initialize() {

        List<View> mViewList = new ArrayList<>();
        ViewPager guide_guide_viewpager = (ViewPager) findViewById(R.id.guide_guide_viewpager);


        for (int mImageResource : mImageResources) {

            View view = getLayoutInflater().inflate(R.layout.item_guide_viewpager, null);
            ImageView imageview = (ImageView) view.findViewById(R.id.item_guide_viewpager_imageview);

            imageview.setBackgroundResource(mImageResource);
            mViewList.add(view);

        }

        guide_guide_viewpager.setAdapter(new MyViewPagerAdapter(mViewList));
        guide_guide_viewpager.addOnPageChangeListener(new PageChangeListener());

        guide_start_button = (Button) findViewById(R.id.guide_start_button);
        guide_start_button.setOnClickListener(new ClickListener());

    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 3 && !(guide_start_button.getVisibility() == View.VISIBLE)) {

                guide_start_button.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(GuideActivity.this, R.anim.guide_start_button);
                guide_start_button.startAnimation(animation);

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(GuideActivity.this, MainActivity.class));
            finish();
        }
    }

}






