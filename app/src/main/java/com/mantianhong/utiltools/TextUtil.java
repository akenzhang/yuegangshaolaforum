package com.mantianhong.utiltools;

import android.content.Context;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by new pc on 2016/7/14.
 */
public class TextUtil {

    public static String parseJason(String strResult){
        return strResult.replace("$@111111@$","<").replace("$@222222@$","/>").replace("$@333333@$",">").replace("$@444444@$","\"").replace("$@555555@$","\''");
    }

    public static String getString(String strOrigin,String strStart,String strEnd){

        if(strOrigin.contains("(") && strOrigin.contains(")")) {
            int intStart = strOrigin.indexOf(strStart);
            return strOrigin.substring(0, intStart);
        }

        return strOrigin;
    }

    public static String generateCode(){
        String[] beforeShuffle = new String[] {"0","1", "2", "3", "4", "5", "6", "7", "8", "9"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list); //洗牌，混乱
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }

    /*
    去掉"<"和">"内的内容
    并将图片的连接保存到ArrayList内
     */
    public static String RevoveGreaterLessTag(String strOrigin){

        String strResult = strOrigin;
        String strStart = "<";
        String strEnd = ">";
        String strRemove="";
        int intStart=-1;
        int intEnd=-1;

        if(!strResult.contains(strStart)){
            return strResult;
        }

        do {
            if(strResult.contains(strStart) && strResult.contains(strEnd)) {
                intStart= strResult.indexOf(strStart);
                intEnd= strResult.indexOf(strEnd);
                strRemove = strResult.substring(intStart,intEnd+1);
                strResult = strResult.replace(strRemove,"");
            }else{
                break;
            }
        }while(strResult.contains(strStart) && strResult.contains(strEnd));

        return strResult;
    }

    public static void ShowToast(Context mContext, String str){
        Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show();
    }


}
