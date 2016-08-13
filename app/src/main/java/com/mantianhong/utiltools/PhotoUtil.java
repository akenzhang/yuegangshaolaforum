package com.mantianhong.utiltools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AKENZHANG on 2016/8/13.
 */
public class PhotoUtil {

    /*
    imgBytes -- 图片的字节数组
    uploadUrl -- 接收图片数据流的ASP.NET服务端
    newName -- 图片的文件名
     */
    public static void uploadPhoto(byte[] imgBytes,String uploadUrl,String newName){
        try {
            //String uploadUrl = "http://www.1316818.com/Jsonserver2.ashx";
            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "******";
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");

            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""+ newName +"\"" + end);
            dos.writeBytes(end);
            dos.write(imgBytes, 0, imgBytes.length);
            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();

            dos.close();
            is.close();
        }catch (Exception ex){
            LogUtil.e(ex.getMessage());
        }
    }
    
//////////////////////////////////////////////////////////////
///////////////////// ASP.NET 接收端代码//////////////////////
/////////////////////////////////////////////////////////////
//    public class Jsonserver2:IHttpHandler
//    {
//        public void ProcessRequest(HttpContext context)
//        {
//            context.Response.ContentType = "text/plain";
//            context.Response.Charset = "utf-8";
//            string uploadPath = HttpContext.Current.Server.MapPath("/") + "\\upload\\android_forum\\";
//            String strFilename = context.Request.Files[0].FileName;
//
//            //将上存的文件保存起来
//            for (int intcnt = 0; intcnt < context.Request.Files.Count; intcnt++)
//            {
//                context.Request.Files[intcnt].SaveAs(uploadPath + intcnt.ToString() + strFilename);
//            }
//        }
//
//        public bool IsReusable
//        {
//            get
//            {
//                return false;
//            }
//        }
//    }

}
