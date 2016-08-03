package com.mantianhong.utiltools;

import com.squareup.okhttp.Request;
import java.io.IOException;
import java.util.Map;

/**
 * Created by AKENZHANG on 2016/8/2.
 */
public abstract class DBUtils {

    /*
    这个方法简单的调用OkHttpUtils.postAsync(),没有任何返回值，也不做任何处理
    使用默认的服务器连接
     */
    public static void SaveToDB(Map<String, String> hasMapParams){

        try {
//            Map<String, String> hasMapParams = new HashMap<String, String>();
//            hasMapParams.put("android_username", nickname);
//            hasMapParams.put("android_password", openid);
//            hasMapParams.put("android_nickname", nickname);
//            hasMapParams.put("android_type", "qq");
//            hasMapParams.put("android_img", image);

            OkHttpUtils.postAsync("http://www.1316818.com/jsonserver.aspx", hasMapParams, new OkHttpUtils.DataCallBack() {
                @Override
                public void requestFailure(Request request, IOException e) {
                    LogUtil.e(e.getMessage());
                }
                @Override
                public void requestSuccess(String result) {
                    LogUtil.e(result);
                }
            });
        }catch (Exception exsave){
            LogUtil.e(exsave.getMessage());
        }
    }

    /*
    这个方法简单的调用OkHttpUtils.postAsync(),没有任何返回值，也不做任何处理
    */
    public static void SaveToDB(String url,Map<String, String> hasMapParams){

        try {
//            Map<String, String> hasMapParams = new HashMap<String, String>();
//            hasMapParams.put("android_username", nickname);
//            hasMapParams.put("android_password", openid);
//            hasMapParams.put("android_nickname", nickname);
//            hasMapParams.put("android_type", "qq");
//            hasMapParams.put("android_img", image);

            OkHttpUtils.postAsync(url, hasMapParams, new OkHttpUtils.DataCallBack() {
                @Override
                public void requestFailure(Request request, IOException e) {
                    LogUtil.e(e.getMessage());
                }
                @Override
                public void requestSuccess(String result) {
                    LogUtil.e(result);
                }
            });
        }catch (Exception exsave){
            LogUtil.e(exsave.getMessage());
        }
    }

    //需要重写实现requestSuccess方法，一个参数：Map<String, String> hasMapParams
    public void getAsync(Map<String, String> hasMapParams){
        OkHttpUtils.postAsync("http://www.1316818.com/jsonserver.aspx", hasMapParams, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                //Do nothing
                LogUtil.e(e.getMessage());
            }

            @Override
            public void requestSuccess(String result){
                successRequest(result);
            }
        });
    }

    //需要重写实现requestSuccess方法，一个参数：可以灵活的构建url
    public void getAsync(String url){
        OkHttpUtils.getAsync(url, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                //Do nothing
                LogUtil.e(e.getMessage());
            }

            @Override
            public void requestSuccess(String result) {
                successRequest(result);
            }
        });
    }

    protected abstract void successRequest(String result);
}
