package com.yuegangshaola.common;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 * Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    protected View root;

    /**
     * 创建Fragment对应的布局文件
     * @param inflater 布局加载器
     * @param container  父容器
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(root==null) {
            root = inflater.inflate(getLayout(), container, false);
        }
            return root;
    }

    /**
     * Fragment对应的宿主Activity创建完成后的回调方法
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
