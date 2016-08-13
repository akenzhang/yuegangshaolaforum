package com.mantianhong.post.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ExpandedMenuView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mantianhong.R;
import com.mantianhong.utiltools.BaseActivity;
import com.mantianhong.utiltools.DBUtils;
import com.mantianhong.utiltools.LogUtil;
import com.mantianhong.utiltools.OkHttpUtils;
import com.mantianhong.utiltools.PhotoUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by new pc on 8/8/2016.
 */
public class PostNewArticleActivity extends BaseActivity {

    private EditText post_newarticle_content_edittext;
    private EditText post_newarticle_title_edittext;
    private TextView post_newarticle_release_textview;
    private LinearLayout post_newarticle_layout;

    private ImageView post_newarticle_pic01_imageview;
    private ImageView post_newarticle_pic02_imageview;
    private ImageView post_newarticle_pic03_imageview;
    private ImageView post_newarticle_pic04_imageview;

    private int intTitleFlag = -1;
    private int intContentFlag = -1;

    private static int mFlag = -1;

    final List<byte[]> datalist = new ArrayList<>();

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

        post_newarticle_pic01_imageview = (ImageView) this.findViewById(R.id.id_post_newarticle_pic01_imageview);
        post_newarticle_pic02_imageview = (ImageView) this.findViewById(R.id.id_post_newarticle_pic02_imageview);
        post_newarticle_pic03_imageview = (ImageView) this.findViewById(R.id.id_post_newarticle_pic03_imageview);
        post_newarticle_pic04_imageview = (ImageView) this.findViewById(R.id.id_post_newarticle_pic04_imageview);

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
                        post_newarticle_release_textview.setBackgroundColor(Color.parseColor("#015e19"));
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
                        post_newarticle_release_textview.setBackgroundColor(Color.parseColor("#015e19"));
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


        post_newarticle_pic01_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPickPhotoAction(1);
            }
        });

        post_newarticle_pic02_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPickPhotoAction(2);
            }
        });

        post_newarticle_pic03_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPickPhotoAction(3);
            }
        });

        post_newarticle_pic04_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPickPhotoAction(4);
            }
        });

        //发表功能的实现
        post_newarticle_release_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这些写将数据保存到数据库和服务器的代码
                //////////////////////////////////////////
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String newName = String.valueOf(System.currentTimeMillis())+".jpg";
                        String strTemp = "";
                        for(int x=0;x<datalist.size();x++)
                        {
                            strTemp = String.valueOf(x);
                            PhotoUtil.uploadPhoto(datalist.get(x),
                                    "http://www.1316818.com/Jsonserver2.ashx",
                                    strTemp+newName);
                        }
                        datalist.clear();
                    }
                }).start();
                //////////////////////////////////////////
            }
        });

    }

    @Override
    protected void bindData() {}


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*用来标识请求gallery的activity*/
    private static final int PHOTO_PICKED_WITH_DATA = 3021;
    /*拍照的照片存储位置*/
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;//照相机拍照得到的图片

    private void doPickPhotoAction(int intFlag) {
        Context context = PostNewArticleActivity.this;
        this.mFlag = intFlag;
        doPickPhotoFromGallery();// 从相册中去获取
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }

    // 请求Gallery程序
    protected void doPickPhotoFromGallery() {
        try {
            final Intent intent = getPhotoPickIntent();
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {}
    }


    // 因为调用了Camera和Gally所以要判断他们各自的返回情况,他们启动时是这样的startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA: {
                // 调用Gallery返回的
                final Bitmap photo = data.getParcelableExtra("data");

                // 下面就是显示照片了
                switch (mFlag){
                    case 1:
                        post_newarticle_pic01_imageview.setImageBitmap(photo);
                    break;
                    case 2:
                        post_newarticle_pic02_imageview.setImageBitmap(photo);
                        break;
                    case 3:
                        post_newarticle_pic03_imageview.setImageBitmap(photo);
                        break;
                    case 4:
                        post_newarticle_pic04_imageview.setImageBitmap(photo);
                        break;
                }

                ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
                photo.compress(Bitmap.CompressFormat.PNG, 30, output);//把bitmap100%高质量压缩 到 output对象里
                //photo.recycle();// 自由选择是否进行回收
                final byte[] imgBytes = output.toByteArray();//转换成功了
                datalist.add(imgBytes);
                try {
                    output.close();
                } catch (Exception e) {e.printStackTrace();}

                break;
            }
        }
    }


    protected void doCropPhoto(File f) {
        try {
            // 启动gallery去剪辑这个照片
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (Exception e) {}
    }

    /**
     *  Constructs an intent for image cropping. 调用剪辑程序
     */
    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        return intent;
    }

    // 封装请求Gallery的intent
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        return intent;
    }

    private void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
