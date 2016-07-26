package com.mantianhong.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

/**
 * created dpb on 16/6/3.
 * <p/>
 * e_mail  dengpbs@163.com.
 *
 * 通用适配器
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    // 数据源
    private List<T> list;
    // item对应的布局文件的id
    private int resId;

    private LayoutInflater inflater;

    private Context context;

    public CommonAdapter(List<T> list, int resId, Context context) {
        this.list = list;
        this.resId = resId;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 获取对应的item的布局文件
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder vh = ViewHolder.getViewHolder(convertView,inflater,resId);

        // 就是设置控件对应的数据
        setContent(vh,list.get(position));

        return vh.getConvertView() ;
    }

    public abstract void setContent(ViewHolder vh,T item);


}