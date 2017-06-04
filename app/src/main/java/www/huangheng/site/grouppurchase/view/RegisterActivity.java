package www.huangheng.site.grouppurchase.view;

import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.entity.RegisterInfo;
import www.huangheng.site.grouppurchase.entity.StaticUserInfo;
import www.huangheng.site.grouppurchase.nohttp.NoHttpListener;
import www.huangheng.site.grouppurchase.nohttp.NoHttpServer;
import www.huangheng.site.grouppurchase.other.citylist.utils.KeyBoard;
import www.huangheng.site.grouppurchase.utils.DialogUtils;
import www.huangheng.site.grouppurchase.utils.SharedPreferencesUtils;

/**
 * 注册
 */

public class RegisterActivity extends AppCompatActivity implements NoHttpListener<String> {


    //用户名
    @BindView(R.id.register_username_edittext)
    EditText mRegisterUsernameEdittext;
    @BindView(R.id.register_deleteusername_imageview)
    ImageView mRegisterDeleteusernameImageview;

    //密码
    @BindView(R.id.register_password_edittext)
    EditText mRegisterPasswordEdittext;
    @BindView(R.id.register_showpassword_checkbox)
    CheckBox mRegisterShowpasswordCheckbox;
    @BindView(R.id.register_deletepassword_imageview)
    ImageView mRegisterDeletepasswordImageview;

    //确认密码
    @BindView(R.id.register_deletecomfirmpassword_imageview)
    ImageView mRegisterDeletecomfirmpasswordImageview;
    @BindView(R.id.register_password_confirm_edittext)
    EditText mRegisterPasswordConfirmEdittext;

    //注册
    @BindView(R.id.register_register_button)
    Button mRegisterRegisterButton;

    private String username;
    private String password;
    private String comfirmPassword;

    private boolean isUserNameEnable = false;
    private boolean isPasswordEnable = false;
    private boolean isPasswordComfirmEnable = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initUsernameEdittext();
        initPasswordEdittext();
        initPasswordConfirmEdittext();

    }

    /**
     * 初始化用户名编辑框
     */
    private void initUsernameEdittext() {

        mRegisterUsernameEdittext.addTextChangedListener(new TextWatcher() {
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
                if (s.length() > 0) {
                    mRegisterDeleteusernameImageview.setVisibility(View.VISIBLE);
                } else {
                    mRegisterDeleteusernameImageview.setVisibility(View.INVISIBLE);
                }

                if (isUserNameEnable && isPasswordEnable && isPasswordComfirmEnable) {
                    mRegisterRegisterButton.setEnabled(true);
                } else {
                    mRegisterRegisterButton.setEnabled(false);
                }
            }
        });


    }

    /**
     * 初始化密码编辑框
     */
    private void initPasswordEdittext() {
        mRegisterPasswordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = s.toString();
                isPasswordEnable = s.length() > 0;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mRegisterDeletepasswordImageview.setVisibility(View.VISIBLE);
                } else {
                    mRegisterDeletepasswordImageview.setVisibility(View.INVISIBLE);
                }

                if (isUserNameEnable && isPasswordEnable && isPasswordComfirmEnable) {
                    mRegisterRegisterButton.setEnabled(true);
                } else {
                    mRegisterRegisterButton.setEnabled(false);
                }

            }
        });
    }

    /**
     * 初始化密码确认编辑框
     */
    private void initPasswordConfirmEdittext() {
        mRegisterPasswordConfirmEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                comfirmPassword = s.toString();
                isPasswordComfirmEnable = s.length() > 0;

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mRegisterDeletecomfirmpasswordImageview.setVisibility(View.VISIBLE);
                } else {
                    mRegisterDeletecomfirmpasswordImageview.setVisibility(View.INVISIBLE);
                }

                if (isUserNameEnable && isPasswordEnable && isPasswordComfirmEnable) {
                    mRegisterRegisterButton.setEnabled(true);
                } else {
                    mRegisterRegisterButton.setEnabled(false);
                }

            }
        });
    }


    @OnClick({R.id.register_titlebar_back, R.id.register_deleteusername_imageview, R.id.register_showpassword_checkbox, R.id.register_deletepassword_imageview, R.id.register_deletecomfirmpassword_imageview, R.id.register_register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_titlebar_back:
                finish();
                break;
            case R.id.register_deleteusername_imageview:
                mRegisterUsernameEdittext.setText("");
                break;
            case R.id.register_showpassword_checkbox:

                if (mRegisterShowpasswordCheckbox.isChecked()) {
                    mRegisterPasswordEdittext.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mRegisterPasswordEdittext.setSelection(mRegisterPasswordEdittext.getText().toString().length());

                    mRegisterPasswordConfirmEdittext.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mRegisterPasswordConfirmEdittext.setSelection(mRegisterPasswordConfirmEdittext.getText().toString().length());

                } else {
                    //两种类型需要同时存在
                    mRegisterPasswordEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mRegisterPasswordEdittext.setSelection(mRegisterPasswordEdittext.getText().toString().length());

                    mRegisterPasswordConfirmEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mRegisterPasswordConfirmEdittext.setSelection(mRegisterPasswordConfirmEdittext.getText().toString().length());
                }

                break;
            case R.id.register_deletepassword_imageview:
                mRegisterPasswordEdittext.setText("");
                break;
            case R.id.register_deletecomfirmpassword_imageview:
                mRegisterPasswordConfirmEdittext.setText("");
                break;

            case R.id.register_register_button:
                register();
                break;
        }
    }


    /**
     * 注册
     */
    private void register() {

        if (username.length() < 6) {
            Toast.makeText(this, "用户名长度小于6位", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(this, "密码长度小于6位", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(comfirmPassword)) {
            Toast.makeText(this, "密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
        } else {
            //注册请求
            Request<String> stringRequest = NoHttp.createStringRequest("https://api.bmob.cn/1/users", RequestMethod.POST);
            stringRequest.addHeader("X-Bmob-Application-Id", "9fc41959205e1bbfaf50ad619eb45d0d");
            stringRequest.addHeader("X-Bmob-REST-API-Key", "30ea7937c419a9f7e3f779b3f705b636");
            stringRequest.addHeader("Content-Type", "application/json");
            stringRequest.setDefineRequestBodyForJson("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");
            NoHttpServer.getInstance().add(this, ConstantPool.REGISTER_WHAT, stringRequest, this, true);

            KeyBoard.closeSoftKeyboard(this);
        }

    }

    @Override
    public void onSucceed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.REGISTER_WHAT:

                final String res = response.get();

                if (res.contains("\"code\":202")) {
                    Toast.makeText(this, "用户名 " + username + " 已存在", Toast.LENGTH_SHORT).show();
                } else {
                    //注册成功
                    Toast.makeText(RegisterActivity.this, "恭喜您，注册成功", Toast.LENGTH_SHORT).show();
                    //提醒是否登录
                    DialogUtils.getInstance().messageDialog(this, "是否立即登录？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点击取消按钮

                            List<String> userPass = new ArrayList<>();
                            userPass.add(username);
                            userPass.add(password);
                            EventBus.getDefault().post(userPass);

                            finish();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点击确定按钮，跳转到登录成功的界面

                            Gson gson = new Gson();
                            RegisterInfo registerInfo = gson.fromJson(res, RegisterInfo.class);

                            SharedPreferencesUtils.getInstance().putUserInfoToSP(RegisterActivity.this, username, registerInfo.getObjectId(), registerInfo.getSessionToken());

                            StaticUserInfo.getInstance().setUserInfo(null,false);

                            EventBus.getDefault().post(StaticUserInfo.getInstance());

                            finish();
                            
                            EventBus.getDefault().post("finish");

                        }
                    });

                }
                break;
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        switch (what) {
            case ConstantPool.REGISTER_WHAT:
                Toast.makeText(this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
