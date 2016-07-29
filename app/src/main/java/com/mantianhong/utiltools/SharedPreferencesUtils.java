package com.mantianhong.utiltools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by AKENZHANG on 2016/7/25.
 */
public class SharedPreferencesUtils {

    public static void saveData(Context mContext,String mKey,String mValue){
        //将获取到的用户信息保存起来
        PackageManager pm = mContext.getPackageManager();
        String appName = mContext.getApplicationInfo().loadLabel(pm).toString();
        String mFilename = appName+"_akenzhang";
        SharedPreferences mySharedPreferences = mContext.getSharedPreferences(mFilename, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(mKey,mValue );
        editor.commit();
    }

    public static String getData(Context mContext,String mKey){
        PackageManager pm = mContext.getPackageManager();
        String appName = mContext.getApplicationInfo().loadLabel(pm).toString();
        String mFilename = appName+"_akenzhang";
        SharedPreferences mySharedPreferences = mContext.getSharedPreferences(mFilename, Activity.MODE_PRIVATE);
        String strValue = mySharedPreferences.getString(mKey,"");
        return strValue;
    }

    public static String getUserName(Context mContext){

        String strValue = getData(mContext,MyConstants.CELLPHONE_USER_NAME);
        if(!TextUtils.isEmpty(strValue) && strValue.length()==11 && strValue.startsWith("1",0)){
            return strValue;
        }

        strValue = getData(mContext,MyConstants.QQ_USER_NAME);
        if(!TextUtils.isEmpty(strValue) && strValue.length()==11 && strValue.startsWith("1",0)){
            return strValue;
        }

        strValue = getData(mContext,MyConstants.WEIXIN_USER_NAME);
        if(!TextUtils.isEmpty(strValue) && strValue.length()==11 && strValue.startsWith("1",0)){
            return strValue;
        }

        strValue = getData(mContext,MyConstants.TEMP_USER_NAME);
        if(!TextUtils.isEmpty(strValue) && strValue.length()==11 && strValue.startsWith("1",0)){
            return strValue;
        }

        return "未知访客";
    }

}
