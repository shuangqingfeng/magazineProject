package com.shuang.meiZhi.webView;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.shuang.meiZhi.R;
import com.shuang.meiZhi.base.BaseActivity;
import com.shuang.meiZhi.utils.UIUtils;

import butterknife.BindView;

/**
 * @author feng
 * @Description: 展示 详情页面
 * @date 2017/3/30
 */
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.ll_revealDetails)
    LinearLayout revealDetails;
    private String mUrl;
    private WebView mDetails;
    private LinearLayout.LayoutParams mWebViewParams;
    private LinearLayout.LayoutParams mProgressParams;
    private ProgressBar mProgressBar;
    private WebSettings mSettings;

    @Override
    protected void initToolbar(Toolbar toolbar) {

    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDetails = new WebView(this);
        mWebViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mDetails.setLayoutParams(mWebViewParams);
        mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        mProgressParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 8, new DisplayMetrics()));
        mProgressBar.setLayoutParams(mProgressParams);
        mProgressBar.setMax(100);
        mProgressBar.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.progress_background));
        revealDetails.addView(mProgressBar);
        revealDetails.addView(mDetails);
        mSettings = mDetails.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        mSettings.setJavaScriptEnabled(true);
//设置自适应屏幕，两者合用
        mSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        mSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//缩放操作
        mSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        mSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        mSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//其他细节操作
        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        mSettings.setAllowFileAccess(true); //设置可以访问文件
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        mSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (null != bundle) {
            mUrl = bundle.getString("detailsUrl");
            mDetails.loadUrl(mUrl);
        } else {
            finish();
        }
        mDetails.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


        });
        mDetails.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (View.INVISIBLE == mProgressBar.getVisibility()) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });

    }

    @Override
    protected int getContainerId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onDestroy() {
        if (mDetails != null) {
            mDetails.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mDetails.clearHistory();
            ((LinearLayout) mDetails.getParent()).removeView(mDetails);
            mDetails.destroy();
            mDetails = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (null != mDetails && mDetails.canGoBack()) {
            mDetails.goBack();
        } else {
            finish();
        }
    }
}
