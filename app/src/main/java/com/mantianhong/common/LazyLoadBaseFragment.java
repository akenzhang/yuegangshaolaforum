package com.mantianhong.common;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;

/**
 * Fragment的基类
 */
public abstract class LazyLoadBaseFragment extends Fragment {

    protected View root;

    /** Fragment当前状态是否可见 */
    protected boolean isVisible;
    protected boolean isPrepared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(root==null) {
            root = inflater.inflate(getLayout(), container, false);
            isPrepared = true;
            beforeLazyLoad();
        }
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        beforeLazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {}

    /**
     * 懒加载前应当做到得处理
     * @return
     */
    protected void beforeLazyLoad(){

        if(!isPrepared || !isVisible) {
            return;
        }

        initView();
        initVariable();
        initListener();

        lazyLoad();
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

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

    /*
    49，解决Fragment由于嵌套引起问题
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
