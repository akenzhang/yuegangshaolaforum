package com.mantianhong.contact.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mantianhong.R;
import com.mantianhong.common.BaseFragment;

import java.lang.reflect.Field;

/**
 * Created by new pc on 2016/7/4.
 */
public class ContactHomeFragment extends BaseFragment {

    private WebView contact_fragment_webview;

    @Override
    protected int getLayout() {
        return R.layout.contact_fragment_home;
    }

    @Override
    protected void initView() {

        contact_fragment_webview = (WebView) this.getActivity().findViewById(R.id.id_contact_fragment_webview);

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

        contact_fragment_webview.loadUrl("http://www.xxsweet.com/pigshow/weixin/MyShop.html");

    }

    @Override
    protected void initListener() {}

    @Override
    protected void bindData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
