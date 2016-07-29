package com.mantianhong.contact.fragment;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mantianhong.R;
import com.mantianhong.utiltools.LazyLoadBaseFragment;
import com.mantianhong.utiltools.LogUtil;

/**
 * Created by new pc on 2016/7/4.
 */
public class ContactHomeFragment extends LazyLoadBaseFragment {

    private WebView contact_fragment_webview;

    @Override
    protected int getLayout() {
        return R.layout.contact_fragment_home;
    }

    @Override
    protected void initView() {
        contact_fragment_webview = (WebView) root.findViewById(R.id.id_contact_fragment_webview);
    }

    @Override
    protected void initVariable() {
        //初始化Webview
        WebSettings webSettings = contact_fragment_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        contact_fragment_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

    }

    @Override
    protected void initListener() {}

    @Override
    protected void lazyLoad() {
        LogUtil.e("ContactHomeFragment==>lazyLoad()");

        contact_fragment_webview.loadUrl("http://www.xxsweet.com/pigshow/weixin/MyShop2.html");
    }
}
