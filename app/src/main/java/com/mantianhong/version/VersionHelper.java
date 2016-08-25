package com.mantianhong.version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Created by AKENZHANG on 2016/8/23.
 */
public class VersionHelper {

    /*
    * 获取当前程序的版本号
    */
    public static String getVersionName(Context mContext){

        try {
            //获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionName;
        }catch (Exception ex){
            return "0";
        }
    }


    /*
	 * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
	 */
    public static UpdataInfo getUpdataInfo(InputStream is){

        try {

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(is, "utf-8");//设置解析的数据源
            int type = parser.getEventType();
            UpdataInfo info = new UpdataInfo();//实体
            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("version".equals(parser.getName())) {
                            info.setVersion(parser.nextText());    //获取版本号
                        } else if ("url".equals(parser.getName())) {
                            info.setUrl(parser.nextText());    //获取要升级的APK文件
                        } else if ("description".equals(parser.getName())) {
                            info.setDescription(parser.nextText());    //获取该文件的信息
                        }
                        break;
                }
                type = parser.next();
            }
            return info;

        }catch (Exception e){
            return null;
        }
    }


}
