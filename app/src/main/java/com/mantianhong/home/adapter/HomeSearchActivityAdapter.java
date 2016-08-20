package com.mantianhong.home.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.BaseAdapter;

import com.mantianhong.bean.Mysearch;
import com.mantianhong.utiltools.CommonAdapter;
import com.mantianhong.utiltools.ViewHolder;

import java.util.List;

/**
 * Created by new pc on 2016/7/4.
 */
public class HomeSearchActivityAdapter extends CommonAdapter<Mysearch> {

    public HomeSearchActivityAdapter(List<Mysearch> list, int resId, Context context) {
        super(list, resId, context);
    }

    @Override
    public void setContent(ViewHolder vh, Mysearch item) {

    }
}
