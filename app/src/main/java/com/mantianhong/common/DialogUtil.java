package com.mantianhong.common;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by new pc on 2016/7/10.
 */
public class DialogUtil {

    AlertDialog dialog=null;

    /*
    制定对话框的字符串内容
     */
    public DialogUtil(Context context,String strDialogContent){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(strDialogContent)
                .create();
        dialog = builder.show();
    }

    /*
    通过自定义布局制定对话框的内容
     */
    public DialogUtil(Context context,int intLayoutResId){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.create();
        dialog = builder.show();
        dialog.setContentView(intLayoutResId);
    }

    public void closeDialog(){
        dialog.dismiss();
    }

    /*
    静态方法，通过自定义的式样布局指定对话框的内容
     */
    public static void showDialog(Context context,int intLayoutResId,final int intSleepTime){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.create();
        final AlertDialog dialog = builder.show();
        dialog.setContentView(intLayoutResId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(intSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }).start();
    }

    /*
    静态方法，直接指定对话框的内容为strDialogContent
    */
    public static void showDialog(Context context,String strDialogContent,final int intSleepTime){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(strDialogContent)
                .create();
        final AlertDialog dialog = builder.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(intSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        }).start();
    }

}
