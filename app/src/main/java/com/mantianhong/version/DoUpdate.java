package com.mantianhong.version;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import com.mantianhong.R;
import com.mantianhong.utiltools.MyConstants;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/*
如何更改版本号：
打开 build.gradle ===> versionName "1.2",修改这里的versionName
打开www.1316818.com/androidversion.xml, 修改 <version>1.2</version>
如果两者不一致，就会检查提示新版本更新
 */

/**
 * Created by AKENZHANG on 2016/8/23.
 */
public class DoUpdate {

    private static UpdataInfo info;

    public static void update(final Context mContext,final android.os.Handler handler){

        //检查现在的版本号: build.gradle内取的版本号
        final String versionname = VersionHelper.getVersionName(mContext);
        //Toast.makeText(mContext,"目前的版本号是："+versionname,Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //从资源文件获取服务器 地址
                    String path = mContext.getResources().getString(R.string.serverurl);
                    //包装成url的对象
                    URL url = new URL(path);
                    HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    InputStream is =conn.getInputStream();
                    info =  VersionHelper.getUpdataInfo(is);

                    String strVersion = info.getVersion();
                    //Toast.makeText(mContext,"最新的版本号是："+info.getVersion(),Toast.LENGTH_SHORT).show();

                    if(!info.getVersion().equals(versionname)){
                        //Log.i(TAG,"版本号不同 ,提示用户升级 ");
                        Message msg = new Message();
                        msg.what = MyConstants.UPDATA_CLIENT;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    // 待处理
                    Message msg = new Message();
                    msg.what = MyConstants.GET_UNDATAINFO_ERROR;
                    handler.sendMessage(msg);
                    //e.printStackTrace();
                }
            }
        }).start();


    }

    /*
	 *
	 * 弹出对话框通知用户更新程序
	 *
	 * 弹出对话框的步骤：
	 * 	1.创建alertDialog的builder.
	 *	2.要给builder设置属性, 对话框的内容,样式,按钮
	 *	3.通过builder 创建一个对话框
	 *	4.对话框show()出来
	 */
    public static void showUpdataDialog(final Context mContext,final android.os.Handler handler) {
        AlertDialog.Builder builer = new AlertDialog.Builder(mContext) ;
        builer.setTitle("版本升级");
        builer.setMessage(info.getDescription());
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk(mContext,handler);
            }
        });
        //当点取消按钮时进行登录
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //LoginMain();
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    /*
     * 从服务器中下载APK
     */
    public static void downLoadApk(final Context context,final android.os.Handler handler) {
        final ProgressDialog pd;	//进度条对话框
        pd = new  ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(info.getUrl(), pd,context);
                    String strDownurl = info.getUrl();
                    sleep(3000);
                    installApk(file,context);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = MyConstants.DOWN_ERROR;
                    handler.sendMessage(msg);
                    //SingletonImageCollection.msg = String.valueOf(e.getMessage());
                }
            }}.start();
    }


    public static File getFileFromServer(String path, ProgressDialog pd,Context mContext) throws Exception{

        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            if(conn.getResponseCode() == 200) {
                //获取到文件的大小
                pd.setMax(conn.getContentLength());
                InputStream is = conn.getInputStream();
                //File file = new File(Environment.getExternalStorageDirectory(), File.separator + "updata.apk");
                File file = new File(mContext.getExternalCacheDir(), File.separator + "updata.apk"); //不适用物理的SDCard位置，而是使用cache dir

                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buffer = new byte[128];
                int len;
                int total = 0;
                while ((len = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;
                    //获取当前下载量
                    pd.setProgress(total);
                }
                fos.flush();
                bis.close();
                fos.close();
                is.close();
                conn.disconnect();
                return file;
            }

        return null;
    }

    //安装apk
    public static void installApk(File file,Context mContext) {

        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        mContext.startActivity(intent);

    }


}
