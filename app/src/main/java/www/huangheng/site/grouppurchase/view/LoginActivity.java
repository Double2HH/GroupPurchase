package www.huangheng.site.grouppurchase.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;
import www.huangheng.site.grouppurchase.entity.UserInfo;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.other.citylist.utils.KeyBoard;
import www.huangheng.site.grouppurchase.utils.HttpRequestUtils;
import www.huangheng.site.grouppurchase.utils.SharedPreferencesUtils;


/**
 * 登录界面
 */

public class LoginActivity extends AppCompatActivity implements NoHttpListener<String> {


    //用户名相关
    @BindView(R.id.login_username_edittext)
    EditText mLoginUsernameEdittext;

    //密码相关
    @BindView(R.id.login_password_edittext)
    EditText mLoginPasswordEdittext;
    @BindView(R.id.login_showpassword_checkbox)
    CheckBox mLoginShowpasswordCheckbox;

    //登录按钮
    @BindView(R.id.login_login_button)
    Button mLoginLoginButton;

    private String username;
    private String password;

    private boolean isUserNameEnable = false;
    private boolean isPasswordEnable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initUsernameEdittext();
        initPasswordEdittext();
    }

    /**
     * 初始化用户名编辑框
     */
    private void initUsernameEdittext() {
        mLoginUsernameEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isUserNameEnable = s.length() > 0;
                username = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUserNameEnable && isPasswordEnable) {
                    mLoginLoginButton.setEnabled(true);
                } else {
                    mLoginLoginButton.setEnabled(false);
                }

            }
        });
    }

    /**
     * 初始化密码编辑框
     */
    private void initPasswordEdittext() {
        mLoginPasswordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPasswordEnable = s.length() > 0;
                password = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUserNameEnable && isPasswordEnable) {
                    mLoginLoginButton.setEnabled(true);
                } else {
                    mLoginLoginButton.setEnabled(false);
                }

            }
        });
    }


    @OnClick({R.id.login_titlebar_back, R.id.login_titlebar_register})
    public void onTitlebarClicked(View view) {
        switch (view.getId()) {
            case R.id.login_titlebar_back:
                finish();
                break;
            case R.id.login_titlebar_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }


    @OnClick({R.id.login_showpassword_checkbox, R.id.login_login_button, R.id.login_forgetpassword_textview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_showpassword_checkbox:

                if (mLoginShowpasswordCheckbox.isChecked()) {
                    mLoginPasswordEdittext.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mLoginPasswordEdittext.setSelection(mLoginPasswordEdittext.getText().toString().length());
                } else {
                    //两种类型需要同时存在
                    mLoginPasswordEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mLoginPasswordEdittext.setSelection(mLoginPasswordEdittext.getText().toString().length());
                }


                break;
            case R.id.login_login_button:

                login();

                break;

            case R.id.login_forgetpassword_textview:

                break;
        }
    }


    /**
     * 登录
     */
    private void login() {
        //请求
        if (username.length() < 6 || password.length() < 6) {
            Toast.makeText(this, "用户名或密码长度小于6位，请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            HttpRequestUtils.getInstance().requestOfUser(this,username,password,this);
            KeyBoard.closeSoftKeyboard(this);
        }
    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.LOGIN_WHAT:
                String res = response.get();
                if (res.contains("\"code\":101")) {
                    Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                } else {

                    //跳转到登录成功的界面

                    Gson gson = new Gson();
                    UserInfo userInfo = gson.fromJson(res, UserInfo.class);

                    SharedPreferencesUtils.getInstance().putUserInfoToSP(LoginActivity.this, userInfo.getUsername(), userInfo.getObjectId(), userInfo.getSessionToken());

                    StaticUserInfo.getInstance().setUserInfo(userInfo,true);

                    EventBus.getDefault().post(StaticUserInfo.getInstance());

                    finish();
                }

                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.LOGIN_WHAT:
                Toast.makeText(this, "登录失败，请重试", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(List<String> userPass) {
        mLoginUsernameEdittext.setText(userPass.get(0));
        mLoginPasswordEdittext.setText(userPass.get(1));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String finish) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
