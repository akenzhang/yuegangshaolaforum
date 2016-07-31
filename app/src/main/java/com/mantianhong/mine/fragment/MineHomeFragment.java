package com.mantianhong.mine.fragment;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mantianhong.R;
import com.mantianhong.mine.adapter.MineDataAdapter;
import com.mantianhong.utiltools.LazyLoadBaseFragment;
import com.mantianhong.utiltools.LogUtil;

import java.util.ArrayList;

/**
 * Created by new pc on 2016/7/3.
 */
public class MineHomeFragment extends LazyLoadBaseFragment {

    private ListView mine_fragment_listview;
    private MineDataAdapter adapter;
    private ArrayList<String> datalist;

    @Override
    protected int getLayout() {
        return R.layout.mine_fragment_home;
    }

    @Override
    protected void initView() {
        mine_fragment_listview = (ListView) root.findViewById(R.id.id_mine_fragment_listview);
    }

    @Override
    protected void initVariable() {
        datalist = new ArrayList<>();
        datalist.add("我的关注");
        datalist.add("我的文章");
        datalist.add("我的评论");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void lazyLoad() {
        LogUtil.e("ContactHomeFragment==>lazyLoad()");

        adapter = new MineDataAdapter(datalist,R.layout.mine_fragment_home_listviewdetail,root.getContext());
        mine_fragment_listview.setAdapter(adapter);

    }

}
