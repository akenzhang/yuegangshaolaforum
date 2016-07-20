package com.yuegangshaola.common;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okio.BufferedSink;

/**
 * Created by zuliang on 2016/5/20.
 * OkHttpUtils 工具类封装
 */
public class OkHttpUtils {

    private static String FILE_PREFIX = "ZERO";
    private OkHttpClient client;//OkHttp对象实例
    private static OkHttpUtils okHttpUtils;
    private Handler mHandler;

    /**
     * 单利获取OkHttpUtils实例
     * @return
     */
    private static OkHttpUtils getInstance(){
        if (okHttpUtils == null){
            okHttpUtils =new OkHttpUtils();
        }
        return okHttpUtils;
    }

    /**
     * 私有的构造函数
     */
    private OkHttpUtils() {
        client = new OkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }


    /*****************内部逻辑处理的方法*********************/
    /**
     * 同步GET请求方法
     * @param url
     * @return
     * @throws IOException
     */
    private Response p_getSync(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response;
    }

    /**
     * 同步GET请求方法，并返回String类型数据
     * @param url
     * @return
     * @throws IOException
     */
    private String p_getSyncAsString(String url) throws IOException{
        return p_getSync(url).body().toString();
    }

    /**
     * GET异步请求
     * @param url
     * @throws IOException
     */
    private void p_getAsync(String url,final DataCallBack callback){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,callback);
            }

            /**
             * 异步返回数据
             * @param response
             */
            @Override
            public void onResponse(Response response) {
                try {
                    deliverDataSuccess(response.body().string(),callback);
                } catch (IOException e) {
                    deliverDataFailure(request,e,callback);
                }

            }
        });
    }

    /**
     * POST请求表单
     * @param url
     * @param parms
     * @param callback
     */
    private void P_postAsync(String url,Map<String,String>parms,final DataCallBack callback){
        //step a1:创建请求体
        RequestBody requestBody = null;

        if (parms ==null){
            parms = new HashMap<String,String>();
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        StringBuilder urlBuilder = new StringBuilder();
        int intFlag = 0;
        for (Map.Entry<String,String> entry:
             parms.entrySet()) {
            String key = entry.getKey().toString();
            String value = null;
            if (key == null || key.equals("")){
                value = "";
            } else {
                value = entry.getValue().toString();

                if(intFlag==0){
                    urlBuilder.append("?"+key+"="+value);
                }else{
                    urlBuilder.append("&"+key+"="+value);
                }
                intFlag++;

            }
            builder.add(key,value);
        }

        requestBody = builder.build();

        String finalUrl = url+urlBuilder.toString();
        //step 2:创建请求
        final Request request = new Request.Builder().url(finalUrl).post(requestBody).build();  //临时将所有的键值都放入url内，正确的方法是将键值封装在requestBody内
        //final Request request = new Request.Builder().url(url).post(requestBody).build();
        //step 3
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,callback);
            }

            @Override
            public void onResponse(Response response){
                try {
                    String result = response.body().string();
                    deliverDataSuccess(result,callback);
                } catch (IOException e) {
                    deliverDataFailure(request,e,callback);
                }
            }
        });
    }

    /**
     * 异步下载文件
     * @param url
     * @param path
     * @param callback
     */
    private void p_downloadAsync(final String url, final String path, final DataCallBack callback){
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                deliverDataFailure(request,e,callback);
            }

            @Override
            public void onResponse(Response response) throws IOException{
                //开始写入文件
                InputStream input = null;
                FileOutputStream fileoutput = null;
                try {
                    input = response.body().byteStream();
                    byte []buffer = new byte[2048];
                    int len = 0;
                    File file = new File(path,getFileName(url));
                    fileoutput = new FileOutputStream(file);
                    while((len=input.read(buffer)) == -1){
                        fileoutput.write(buffer,0,len);
                    }
                    fileoutput.flush();
                    deliverDataSuccess(file.getAbsolutePath(),callback);
                } catch (IOException e) {
                    deliverDataFailure(request,e,callback);
                }finally {
                    if (fileoutput != null){
                        fileoutput.close();
                    }
                    if (input != null){
                        input.close();
                    }
                }
            }
        });
    }

    /****************数据分发的方法***********************/
    private void deliverDataFailure(final Request request, final IOException e, final DataCallBack callback){
        //发送到主线程当中处理
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null){
                    callback.requestFailure(request,e);
                }
            }
        });
    }

    private void deliverDataSuccess(final String result, final DataCallBack callback){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null){
                    callback.requestSuccess(result);
                }
            }
        });
    }

    /*****************对外公布的方法*********************/
    public static Response getSync(String url) throws IOException{
        return getInstance().p_getSync(url);
    }

    public static String getSyncAsString(String url) throws IOException{
        return getInstance().p_getSyncAsString(url);
    }

    public static void getAsync(String url,DataCallBack callback){
        getInstance().p_getAsync(url,callback);
    }

    public static void postAsync(String url, Map<String,String>parms,DataCallBack callback){
        getInstance().P_postAsync(url,parms,callback);
    }

    public static void downloadAsync(String url,String path,DataCallBack callback){
        getInstance().p_downloadAsync(url,path,callback);
    }

    /****************数据回调接口***********************/
    public interface DataCallBack{
        void requestFailure(Request request, IOException e);
        void requestSuccess(String result);
    }

    /**
     * 根据文件URL地址来获取文件的路径文件名
     * @param url
     * @return
     */
    private String getFileName(String url){
        int index = url.lastIndexOf("/");
        String path = (index < 0)?url:url.substring(index + 1,url.length());
        return FILE_PREFIX+path;
    }


}
