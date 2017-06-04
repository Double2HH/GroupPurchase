package www.huangheng.site.grouppurchase.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;
import www.huangheng.site.grouppurchase.utils.SharedPreferencesUtils;


/**
 * 个人中心
 */

public class PersonageActivity extends AppCompatActivity {


    //头像
    @BindView(R.id.personage_avator_draweeview)
    SimpleDraweeView mPersonageAvatorDraweeview;

    //用户名
    @BindView(R.id.personage_username_username)
    TextView mPersonageUsernameUsername;

    //手机号码
    @BindView(R.id.personage_phone_phone)
    TextView mPersonagePhonePhone;

    //用户名
    String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personage);
        ButterKnife.bind(this);
        initialize();
    }

    /**
     * 初始化
     */
    private void initialize() {
        Intent intent = getIntent();
        username = intent.getStringExtra(ConstantPool.USERNAME);
        mPersonageUsernameUsername.setText(username);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri chosenImageUri = data.getData();

            Bitmap avator = null;
            try {
                avator = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(this.getResources(),
                    avator);
            drawable.setCircular(true);
            ((ImageView) mPersonageAvatorDraweeview).setImageDrawable(drawable);
        }
    }

    @OnClick({R.id.personage_titlebar_back, R.id.personage_avator, R.id.personage_username, R.id.personage_phone, R.id.personage_quit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personage_titlebar_back:
                finish();
                break;
            case R.id.personage_avator:

                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);

                break;
            case R.id.personage_username:
                break;
            case R.id.personage_phone:
                break;
            case R.id.personage_quit_login:

                SharedPreferencesUtils.getInstance().clearUserInfo(PersonageActivity.this);
                StaticUserInfo.getInstance().setUserInfo(null,false);
                EventBus.getDefault().post(StaticUserInfo.getInstance());
                Toast.makeText(this, "已退出登录", Toast.LENGTH_SHORT).show();
                finish();


                break;
        }
    }
}
