package com.mantianhong.utiltools;

/**
 * Created by new pc on 2016/7/14.
 */
public class TextUtil {

    public static String parseJason(String strResult){
        return strResult.replace("111111","<").replace("222222","/>").replace("333333",">").replace("444444","\"").replace("555555","\''");
    }

    public static String getString(String strOrigin,String strStart,String strEnd){

        if(strOrigin.contains("(") && strOrigin.contains(")")) {
            int intStart = strOrigin.indexOf(strStart);
            return strOrigin.substring(0, intStart);
        }

        return strOrigin;
    }
}
