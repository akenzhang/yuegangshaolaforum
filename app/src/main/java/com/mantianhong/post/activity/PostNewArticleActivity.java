package com.mantianhong.post.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    }

    @Override
    protected void bindData() {

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*用来标识请求照相功能的activity*/
    private static final int CAMERA_WITH_DATA = 3023;

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

    /**
     * 拍照获取图片
     *
     */
    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "R.string.photoPickerNotFoundText",
                    Toast.LENGTH_LONG).show();
        }
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     *
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }

    // 请求Gallery程序
    protected void doPickPhotoFromGallery() {
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "R.string.photoPickerNotFoundText1",
                    Toast.LENGTH_LONG).show();
        }
    }

    // 封装请求Gallery的intent
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        return intent;
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
                System.out.println(photo);
                //缓存用户选择的图片
                //img = getBitmapByte(photo);
                //mEditor.setPhotoBitmap(photo);

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

                System.out.println("set new photo");
                break;
            }
            case CAMERA_WITH_DATA: {    // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                doCropPhoto(mCurrentPhotoFile);
                break;
            }
        }
    }

    protected void doCropPhoto(File f) {
        try {
            // 启动gallery去剪辑这个照片
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (Exception e) {
            Toast.makeText(this, "R.string.photoPickerNotFoundText",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Constructs an intent for image cropping. 调用图片剪辑程序
     */
    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        return intent;
    }

    private void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
