package com.yuegangshaola.home.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.yuegangshaola.R;
import com.yuegangshaola.bean.Article;
import com.yuegangshaola.bean.Articleset;
import com.yuegangshaola.bean.Root;
import com.yuegangshaola.common.BaseFragment;
import com.yuegangshaola.common.LogUtil;
import com.yuegangshaola.common.OkHttpUtils;
import com.yuegangshaola.home.adapter.HomeDefaultFragmentShaoyaRecyclerViewAdapter;

import java.io.IOException;
import java.util.List;

/**
 * Created by new pc on 2016/7/5.
 */
public class HomeDefaultFragmentShaoya extends YuegangshaolaBaseFragment {

    @Override
    protected int getRecyclerViewID() {
        return R.id.id_home_fragment_shaoya_recyclerview;
    }

    @Override
    protected int getFid() {
        return 7;
    }

    @Override
    protected int getLayout() {
        return R.layout.home_fragment_shaoya_layout;
    }
}
