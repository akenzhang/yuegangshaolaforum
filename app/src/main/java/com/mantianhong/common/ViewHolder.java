package com.mantianhong.common;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * created dpb on 16/6/3.
 * <p/>
 * e_mail  dengpbs@163.com.
 */
public class ViewHolder {
    /**和Map和相似，只是他的key只能是Integer*/
    private SparseArray<View> list;

    /**复用的布局*/
    private View convertView;

    private ViewHolder(LayoutInflater inflater,int resId)
    {
        convertView = inflater.inflate(resId,null);
        list = new SparseArray<>();
        convertView.setTag(this);
    }

    public static ViewHolder getViewHolder(View convertView,LayoutInflater inflater,int resId)
    {
        if(convertView == null)
        {
            return new ViewHolder(inflater,resId);
        }
        return (ViewHolder)convertView.getTag();
    }

    /**
     * 提供给外界获取ViewHolder中的view的方法
     * @return
     */
    public View getViews(int viewId)
    {
        View view = list.get(viewId);
        if(view == null)
        {
            view = convertView.findViewById(viewId);
            // 加加载的布局文件添加到list中
            list.put(viewId,view);
        }
        return view;
    }

    public View getConvertView()
    {
        return convertView;
    }
}
