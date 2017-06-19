package com.bwie.day0618_oldweekexam_ab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by
 * Chenxin
 * on 2017/6/18.
 */

public class IAdapter extends BaseAdapter {

    Context context;
    List<Bean.ResultBean.DataBean> list;
    public IAdapter(Context context, List<Bean.ResultBean.DataBean> list) {

        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder holder = null;

        if (convertView == null) {

            convertView = View.inflate(context,R.layout.lv_item,null);

            holder = new ViewHolder();

            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tv_updatetime = (TextView) convertView.findViewById(R.id.tv_updatetime);

            convertView.setTag(holder);

        }else{

            holder = (ViewHolder) convertView.getTag();

        }

        Bean.ResultBean.DataBean dataBean = list.get(position);

        holder.tv_content.setText(dataBean.getContent());

        holder.tv_updatetime.setText(dataBean.getUpdatetime());

        return convertView;
    }

    class ViewHolder{

        TextView tv_content,tv_updatetime;

    }
}
