package com.yuegangshaola.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by new pc on 2016/7/10.
 */
public class SingletonImageCollection {

    private static Map picCollection;

    private static class LazyHolder {
        private static final SingletonImageCollection INSTANCE = new SingletonImageCollection();
    }

    private SingletonImageCollection (){
        picCollection = new HashMap<Object,String>();
    }

    public static final SingletonImageCollection getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void addImage(String strImgeUrl){
        if(!picCollection.containsValue(strImgeUrl)) {
            int intSize = picCollection.size();
            picCollection.put(intSize+1, strImgeUrl);
        }
    }

    public int getImageCount(){
        return picCollection.size();
    }

    public String getImage(int intKey){
        if(picCollection.containsKey(intKey)) {
            return picCollection.get(intKey).toString();
        }else{
            return "";
        }
    }

    public void removeImage(int intKey){
        if(picCollection.containsKey(intKey)) {
            picCollection.remove(intKey);
        }
    }

    public boolean containImage(int intKey){
        return picCollection.containsKey(intKey);
    }

    //内存中最多保存50张图片
    public void recycleCollection(Context mContext){

        int intSize = picCollection.size();
        if(intSize>80) {
            Map newpicCollection = new HashMap<Object,String>();

            int j = 1;
            for(int i=intSize;i>=1;i--){
                newpicCollection.put(j,picCollection.get(i));
                j++;
            }

            picCollection.clear();
            for(int k=1;k<=50;k++){
                picCollection.put(k,newpicCollection.get(k));
            }

            for(int k=51;k<=intSize;k++){
                Picasso.with(mContext).invalidate(newpicCollection.get(k).toString());
            }

            newpicCollection.clear();
        }
    }

    public void initialCollection(Context mContext){

        int intSize = picCollection.size();
        for(int k=1;k<=intSize;k++){
            Picasso.with(mContext).invalidate(picCollection.get(k).toString());
        }
        picCollection.clear();
    }

    public Boolean containImage(String strValue){
        if(picCollection.containsValue(strValue)) {
            return true;
        }else {
            return false;
        }
    }

    /*
    无缓存加载图片
     */
    public static void loadImage(Context mContext, String mUrl,ImageView mImageView){

        int intStart = mUrl.indexOf("[");
        int intEnd = mUrl.indexOf("]")+1;
        String strWidthHeight = mUrl.substring(intStart,intEnd);

        int intDouhao = strWidthHeight.indexOf(",");
        String strWidth = strWidthHeight.substring(0,intDouhao).replace("[","");
        String strHeight = strWidthHeight.replace("["+ strWidth +",", "").replace("]", "");

        int intWidth = Integer.parseInt(strWidth);
        int intHeight = Integer.parseInt(strHeight);

        String strFinalImageUrl = mUrl.replace(strWidthHeight,"");

        Picasso.with(mContext).setIndicatorsEnabled(true);
        Picasso.with(mContext)
                .load(strFinalImageUrl)
                //.memoryPolicy(MemoryPolicy.NO_CACHE )
                //.networkPolicy(NetworkPolicy.NO_CACHE)
                //.resize(450,500*intHeight/intWidth).centerCrop()
                .noFade()
                .config(Bitmap.Config.RGB_565)
                .into(mImageView);
    }

    public static String parseImageUrl(String url){

        int intStart = url.indexOf("[");
        int intEnd = url.indexOf("]")+1;
        String strWidthHeight = url.substring(intStart,intEnd);

        return url.replace(strWidthHeight,"");
    }


}
