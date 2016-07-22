package com.yuegangshaola.bean;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by AKENZHANG on 2016/7/22.
 */
public class EmailUtils {

    private static String host = "smtp.qq.com";
    private static String to = "372891240@qq.com";
    private static String from = "2217005280@qq.com";
    private static String password = "zjgerkfyiytudjib";// 密码


    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void sendMail(final String strTitle,final String strContent) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建HtmlEmail类
                    HtmlEmail email = new HtmlEmail();
                    //填写邮件的主机明，我这里使用的是163
                    email.setHostName(host);
                    email.setTLS(true);
                    email.setSSL(true);
                    //设置字符编码格式，防止中文乱码
                    email.setCharset("gbk");
                    //设置收件人的邮箱
                    email.addTo(to);
                    //设置发件人的邮箱
                    email.setFrom(from);
                    //填写发件人的用户名和密码
                    email.setAuthentication(from, password);
                    //填写邮件主题
                    email.setSubject(strTitle);
                    //填写邮件内容
                    email.setMsg(strContent);
                    //发送邮件
                    email.send();

                } catch (EmailException e) {}
            }
        }).start();

    }


}
