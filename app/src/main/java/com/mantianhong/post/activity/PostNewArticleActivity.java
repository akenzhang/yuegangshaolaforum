package com.mantianhong.post.activity;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mantianhong.R;
import com.mantianhong.utiltools.BaseActivity;

/**
 * Created by new pc on 8/8/2016.
 */
public class PostNewArticleActivity extends BaseActivity {

    private EditText post_newarticle_content_edittext;
    private EditText post_newarticle_title_edittext;
    private TextView post_newarticle_release_textview;
    private LinearLayout post_newarticle_layout;
    private ImageView post_newarticle_addpic_imageview;

    private int intTitleFlag = -1;
    private int intContentFlag = -1;

    @Override
    protected int getLayout() {
        return R.layout.post_newarticle_activity;
    }

    @Override
    protected void initView() {

        post_newarticle_content_edittext = (EditText) this.findViewById(R.id.id_post_newarticle_content_edittext);
        post_newarticle_title_edittext = (EditText) this.findViewById(R.id.id_post_newarticle_title_edittext);
        post_newarticle_release_textview = (TextView) this.findViewById(R.id.id_post_newarticle_release_textview);
        post_newarticle_layout = (LinearLayout) this.findViewById(R.id.id_post_newarticle_layout);
        post_newarticle_addpic_imageview = (ImageView) this.findViewById(R.id.id_post_newarticle_addpic_imageview);

    }

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

        post_newarticle_content_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    intContentFlag =1;
                    if(intTitleFlag==1){
                        post_newarticle_release_textview.setEnabled(true);
                        post_newarticle_release_textview.setBackgroundColor(Color.parseColor("#828285"));
                    }
                }else {
                    intContentFlag =-1;
                    post_newarticle_release_textview.setEnabled(false);
                    post_newarticle_release_textview.setBackgroundColor(Color.parseColor("#d1d1d5"));
                }
            }
        });

        post_newarticle_title_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length()>0){
                    intTitleFlag =1;
                    if(intContentFlag==1){
                        post_newarticle_release_textview.setEnabled(true);
                        post_newarticle_release_textview.setBackgroundColor(Color.parseColor("#828285"));
                    }
                }else {
                    intTitleFlag =-1;
                    post_newarticle_release_textview.setEnabled(false);
                    post_newarticle_release_textview.setBackgroundColor(Color.parseColor("#d1d1d5"));
                }

            }
        });

        post_newarticle_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostNewArticleActivity.this.finish();
            }
        });

        post_newarticle_addpic_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PostNewArticleActivity.this,"添加图片功能正在开发中...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void bindData() {

    }
}
