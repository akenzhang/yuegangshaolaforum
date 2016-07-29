package com.mantianhong.video.activity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.mantianhong.R;
import com.mantianhong.common.BaseActivity;
import com.mantianhong.common.LogUtil;

/**
 * Created by AKENZHANG on 2016/7/27.
 */
public class VideoHomeFragmentVideoPlayerActivity extends BaseActivity {

    private WebView video_fragment_videoplayer;
    private TextView video_fragment_close;

    @Override
    protected int getLayout() {
        return R.layout.video_fragment_home_videoplayer;
    }

    @Override
    protected void initView() {

        video_fragment_close = (TextView) this.findViewById(R.id.id_video_fragment_close);
        video_fragment_videoplayer = (WebView) this.findViewById(R.id.id_video_fragment_videoplayer);

        video_fragment_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                暂时没找到好的办法关闭，并且停掉内存中播放的视频
                 */
                //VideoHomeFragmentVideoPlayerActivity.this.onDestroy();
                VideoHomeFragmentVideoPlayerActivity.this.finish();
            }
        });

        //初始化Webview
        WebSettings webSettings = video_fragment_videoplayer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        video_fragment_videoplayer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        String strUrl = this.getIntent().getExtras().getString("url");
        video_fragment_videoplayer.loadUrl(strUrl);
    }

    @Override
    protected void initVariable() {
    }

    @Override
    protected void initListener() {

        //更多微视：  http://www.weishi.com/u/25690423
    }

    @Override
    protected void bindData() {
    }
}
