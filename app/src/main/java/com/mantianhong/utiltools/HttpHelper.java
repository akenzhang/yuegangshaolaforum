package com.mantianhong.utiltools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by AKENZHANG on 2016/6/11.
 */
public class HttpHelper
{
    /*
    根据给定的图片网址，获得对应的Bitmap,但需要传入图片网址
     */
    public static Bitmap getBitmapFromUrl(String strPhotoUrl)
    {
        Bitmap bitmap = null;
        try {
            //创建一个URL对象，URL要表达的是：给定网址，使用URL将网址表达出来
            //URL url = new URL("http://www.1316818.com/upload/temp/mydog.png");
            URL url = new URL(strPhotoUrl);
            //有了网址URL,自然要想到通过该URL，获得一个远程连接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(6*1000);

            //拿到了远程连接，目的就是为了获得其中的文件流
            //InputStream inputStream = conn.getInputStream();
            InputStream inputStream = new BufferedInputStream(conn.getInputStream()); //这个方法效率会更高点

            //定义一个字节数组，用来存放输入流中的字节
            byte[] bytes = new byte[1024];

            //定义一个输出流，将输入流中的字节数组循环的写到该输出流中
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            //循环的读取输入流中的字节，并存入指定的数组中，inputStream.read(bytes)返回的是每次读入的长度，如果读入长度为-a1，表示读完了，只有大于零，才是正常读取操作
            int len=-1;
            if (conn.getResponseCode() == 200)
            {
                while ( (len=inputStream.read(bytes)) != - 1 )
                {
                    outputStream.write(bytes, 0 , len); //一定要注意，写入到输出流的时候，需要明确指定后面两个参数
                }
            }

            //将输出流中的字节一次性的写入到一个大的字节数组中
            byte[] imageBytes = outputStream.toByteArray();

            //有了图片的字节数组，可以借助Bitmap来直接将图片字节数组转换成更直观的图片
            bitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

            //关闭连接
            if(outputStream!=null) {outputStream.close();}
            if(inputStream!=null) {inputStream.close();}
            if(conn!=null) {conn.disconnect();}

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /*
    根据给定的网址，获得对应的字节数组
    */
    public static String getStringFromUrl(String strUrl)
    {
        String strResult="";

        try {
            //创建一个URL对象，URL要表达的是：给定网址，使用URL将网址表达出来
            URL url = new URL(strUrl);
            //有了网址URL,自然要想到通过该URL，获得一个远程连接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(6*1000);

            //拿到了远程连接，目的就是为了获得其中的文件流
            //InputStream inputStream = conn.getInputStream();
            InputStream inputStream = new BufferedInputStream(conn.getInputStream()); //这个方法效率会更高点

            //定义一个字节数组，用来存放输入流中的字节
            byte[] bytes = new byte[1024];

            //定义一个输出流，将输入流中的字节数组循环的写到该输出流中
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            //循环的读取输入流中的字节，并存入指定的数组中，inputStream.read(bytes)返回的是每次读入的长度，如果读入长度为-a1，表示读完了，只有大于零，才是正常读取操作
            int len=-1;
            if (conn.getResponseCode() == 200)
            {
                while ( (len=inputStream.read(bytes)) != - 1 )
                {
                    outputStream.write(bytes, 0 , len); //一定要注意，写入到输出流的时候，需要明确指定后面两个参数
                }
            }

            //将输出流中的字节一次性的写入到一个大的字节数组中
            strResult= outputStream.toString();

            //关闭连接
            if(outputStream!=null) {outputStream.close();}
            if(inputStream!=null) {inputStream.close();}
            if(conn!=null) {conn.disconnect();}

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return strResult;
    }

}
