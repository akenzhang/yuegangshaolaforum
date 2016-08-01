package com.mantianhong.utiltools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.mantianhong.login.activity.LoginMainActivity;

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

    public static String getUserNameConsiderlessVisitor(Context mContext){

        String strValue = getData(mContext,MyConstants.CELLPHONE_USER_NAME);
        if(!TextUtils.isEmpty(strValue) && strValue.length()==11 && strValue.startsWith("1",0)){
            return strValue;
        }

        strValue = getData(mContext,MyConstants.QQ_USER_NAME);
        if(!TextUtils.isEmpty(strValue)){
            return strValue;
        }

        strValue = getData(mContext,MyConstants.WEIXIN_USER_NAME);
        if(!TextUtils.isEmpty(strValue)){
            return strValue;
        }

        //strValue = getData(mContext,MyConstants.VISITOR_USER_NAME);
        //if(!TextUtils.isEmpty(strValue)){
        //    return strValue;
        //}

        return "";
    }

    public static String getUserNameIncludingVisitor(Context mContext){

        String strValue = getData(mContext,MyConstants.CELLPHONE_USER_NAME);
        if(!TextUtils.isEmpty(strValue) && strValue.length()==11 && strValue.startsWith("1",0)){
            return strValue;
        }

        strValue = getData(mContext,MyConstants.QQ_USER_NAME);
        if(!TextUtils.isEmpty(strValue)){
            return strValue;
        }

        strValue = getData(mContext,MyConstants.WEIXIN_USER_NAME);
        if(!TextUtils.isEmpty(strValue)){
            return strValue;
        }

        strValue = getData(mContext,MyConstants.VISITOR_USER_NAME);
        if(!TextUtils.isEmpty(strValue)){
            return strValue;
        }

        return "";
    }

    public static Boolean isLoginIncludingVisitor(Context mContext){
        boolean isThisLogin=true;
        //如能找到曾经登录的痕迹，就默认登录，不再需要提示登录界面
        String strUserName = SharedPreferencesUtils.getUserNameIncludingVisitor(mContext);
        if(TextUtils.isEmpty(strUserName)){
            isThisLogin=false;
            Intent intent = new Intent(mContext,LoginMainActivity.class);
            mContext.startActivity(intent);
            return isThisLogin;
        }

        return isThisLogin;
    }

    public static Boolean isLoginConsiderlessVisitor(Context mContext){
        boolean isThisLogin=true;
        //如能找到曾经登录的痕迹，就默认登录，不再需要提示登录界面
        String strUserName = SharedPreferencesUtils.getUserNameIncludingVisitor(mContext);
        if(strUserName.contains("过客") || strUserName.contains("游客") ) strUserName="";
        if(TextUtils.isEmpty(strUserName)){
            isThisLogin=false;
            Intent intent = new Intent(mContext,LoginMainActivity.class);
            mContext.startActivity(intent);
            return isThisLogin;
        }

        return isThisLogin;
    }

    public static Boolean isLoginConsiderlessVisitor(Context mContext,String mFlag){
        boolean isThisLogin=true;
        //如能找到曾经登录的痕迹，就默认登录，不再需要提示登录界面
        String strUserName = SharedPreferencesUtils.getUserNameIncludingVisitor(mContext);
        if(strUserName.contains("过客") || strUserName.contains("游客") ) strUserName="";
        if(TextUtils.isEmpty(strUserName)){
            isThisLogin=false;
            Bundle bundle = new Bundle();
            bundle.putString("flag",mFlag);
            Intent intent = new Intent(mContext,LoginMainActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
            return isThisLogin;
        }

        return isThisLogin;
    }

    public static Boolean isLoginConsiderlessVisitor(Context mContext,String mFlag,String mHandlerFlag){
        boolean isThisLogin=true;
        //如能找到曾经登录的痕迹，就默认登录，不再需要提示登录界面
        String strUserName = SharedPreferencesUtils.getUserNameIncludingVisitor(mContext);
        if(strUserName.contains("过客") || strUserName.contains("游客") ) strUserName="";
        if(TextUtils.isEmpty(strUserName)){
            isThisLogin=false;
            Bundle bundle = new Bundle();
            bundle.putString("flag",mFlag);
            bundle.putString("handlerflag",mHandlerFlag);
            Intent intent = new Intent(mContext,LoginMainActivity.class);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
            return isThisLogin;
        }

        return isThisLogin;
    }

    public static Boolean getLoginStateConsiderlessVisitor(Context mContext){
        boolean isThisLogin=true;
        String strUserName = SharedPreferencesUtils.getUserNameIncludingVisitor(mContext);
        if(strUserName.contains("过客") || strUserName.contains("游客") ) strUserName="";
        if(TextUtils.isEmpty(strUserName)){
            isThisLogin=false;
            return isThisLogin;
        }

        return isThisLogin;
    }

    public static String getLoginModeIncludingVisitor(Context mContext){

        String strValue = getData(mContext,MyConstants.CELLPHONE_USER_NAME);
        if(!TextUtils.isEmpty(strValue) && strValue.length()==11 && strValue.startsWith("1",0)){
            return "CELLPHONE";
        }

        strValue = getData(mContext,MyConstants.QQ_USER_NAME);
        if(!TextUtils.isEmpty(strValue)){
            return "QQ";
        }

        strValue = getData(mContext,MyConstants.WEIXIN_USER_NAME);
        if(!TextUtils.isEmpty(strValue)){
            return "WEIXIN";
        }

        strValue = getData(mContext,MyConstants.VISITOR_USER_NAME);
        if(!TextUtils.isEmpty(strValue)){
            return "VISITOR";
        }

        return "";
    }

    public static void logoutConsiderlessVisitor(Context mContext){
        saveData(mContext, MyConstants.CELLPHONE_USER_NAME,"");
        saveData(mContext, MyConstants.QQ_USER_NAME,"");
        saveData(mContext, MyConstants.WEIXIN_USER_NAME,"");
    }

}
