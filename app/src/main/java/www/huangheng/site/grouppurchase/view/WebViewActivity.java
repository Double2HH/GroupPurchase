package www.huangheng.site.grouppurchase.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ConstantPool;

/**
 * 网页
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView mWebview_webview;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initialize();
    }

    /**
     * 初始化
     */
    private void initialize() {
        url = getIntent().getStringExtra(ConstantPool.JUMPTOWEBVIEW);
        mWebview_webview = (WebView) findViewById(R.id.webview_webview);
        mWebview_webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });
        mWebview_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebview_webview.getSettings().setJavaScriptEnabled(true);
        mWebview_webview.getSettings().setUseWideViewPort(true); //适配屏幕

        //优先使用缓存
        mWebview_webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mWebview_webview.loadUrl(url);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebview_webview.canGoBack()) {
            mWebview_webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
