package com.mantianhong.utiltools;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
}
