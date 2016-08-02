package com.mantianhong.home.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.mantianhong.R;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.MyConstants;

import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by AKENZHANG on 2016/7/22.
 */
public class HomeArticleShareActivity extends BaseActivity {

    private TextView home_article_share_cancel;
    private TextView home_article_share_restspace;
    private ImageView home_article_share_qqspace;
    private ImageView home_article_share_qq;
    private ImageView home_article_share_lianjie;

    /*
    QQ,QQ空间
     */
    private Tencent mTencent;
    private String strSHARE_TO_QQ_TITLE = "";
    private String strSHARE_TO_QQ_SUMMARY = "";
    private String strSHARE_TO_QQ_TARGET_URL = "";
    private String strSHARE_TO_QQ_IMAGE_URL = "";
    private ArrayList<String> mImageList = new ArrayList<>();


    @Override
    protected int getLayout() {
        return R.layout.home_article_share_activity;
    }

    @Override
    protected void initView() {
        home_article_share_cancel = (TextView) this.findViewById(R.id.id_home_article_share_cancel);
        home_article_share_restspace = (TextView) this.findViewById(R.id.id_home_article_share_restspace);
        home_article_share_qqspace = (ImageView) this.findViewById(R.id.id_home_article_share_qqspace);
        home_article_share_qq = (ImageView) this.findViewById(R.id.id_home_article_share_qq);
        home_article_share_lianjie = (ImageView) this.findViewById(R.id.id_home_article_share_lianjie);

        //QQ对象
        mTencent = Tencent.createInstance(MyConstants.APP_ID, this.getApplicationContext());

    }

    @Override
    protected void initVariable() {

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            strSHARE_TO_QQ_TITLE = bundle.getString("SHARE_TO_QQ_TITLE");
            strSHARE_TO_QQ_SUMMARY =bundle.getString("SHARE_TO_QQ_SUMMARY");
            strSHARE_TO_QQ_TARGET_URL =bundle.getString("SHARE_TO_QQ_TARGET_URL");
            strSHARE_TO_QQ_IMAGE_URL = bundle.getString("SHARE_TO_QQ_IMAGE_URL");

            String[] images = strSHARE_TO_QQ_IMAGE_URL.split("\\|");
            for(String url:images){
                if(!TextUtils.isEmpty(url)) {
                    mImageList.add("http://www.1316818.com/upload/"+url);
                }
            }
        }


    }

    @Override
    protected void initListener() {

        /*
        取消分享，隐藏分享的界面
         */
        home_article_share_restspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeArticleShareActivity.this.finish();
            }
        });
        home_article_share_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeArticleShareActivity.this.finish();
            }
        });


        /*
        QQ空间
         */
        home_article_share_qqspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
                params.putString(QzoneShare.SHARE_TO_QQ_TITLE, strSHARE_TO_QQ_TITLE);
                //params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,strSHARE_TO_QQ_SUMMARY);
                params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, strSHARE_TO_QQ_TARGET_URL);
                //ArrayList imageUrls = new ArrayList();
                //imageUrls.add("http://media-cdn.tripadvisor.com/media/photo-s/01/3e/05/40/the-sandbar-that-links.jpg");
                params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, mImageList);
                params.putInt(QzoneShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                mTencent.shareToQzone(HomeArticleShareActivity.this, params, new BaseUiListener());
            }
        });


        /*
        QQ
         */
        home_article_share_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, strSHARE_TO_QQ_TITLE);
                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, strSHARE_TO_QQ_SUMMARY);
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, strSHARE_TO_QQ_TARGET_URL);
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mImageList.get(0));
                //params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "测试应用222222");
                //params.putString(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
                mTencent.shareToQQ(HomeArticleShareActivity.this, params, new BaseUiListener());
            }
        });

        /*
        复制链接
         */
        home_article_share_lianjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                //clip.getText(); // 粘贴
                clip.setText(strSHARE_TO_QQ_TARGET_URL); // 复制
                Toast.makeText(HomeArticleShareActivity.this,"分享链接已经复制到剪贴板.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void bindData() {}

    /*
    QQ回调的需要
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(mTencent!=null) {
            mTencent.onActivityResult(requestCode, resultCode, data);
        }
    }


    /*
    QQ回调接口
     */
    class BaseUiListener implements IUiListener {

        protected void doComplete(JSONObject values) {
            //这里实现业务逻辑
            Toast.makeText(HomeArticleShareActivity.this,"成功完成分享...",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object response) {
            doComplete((JSONObject)response);
        }

        @Override
        public void onError(UiError e) {}

        @Override
        public void onCancel() {}
    }

}



