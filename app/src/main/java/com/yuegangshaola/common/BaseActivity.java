package com.yuegangshaola.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initVariable();
        initListener();
        bindData();
    }

    /**
     * 获取子类布局文件
     * @return
     */
    protected abstract int getLayout();
    /**
     * 初始化控件
     * @return
     */
    protected abstract void initView();
    /**
     * 初始化变量
     * @return
     */
    protected abstract void initVariable();
    /**
     * 初始化事件
     * @return
     */
    protected abstract void initListener();
    /**
     * 绑定数据
     * @return
     */
    protected abstract void bindData();
}
