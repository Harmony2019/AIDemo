package com.example.myapplication.robotdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

/**
 * listview绑定的数据适配器
 */
public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;

    private List<ChatMessage> mDatas = null;

    public ChatMessageAdapter(Context context, List<ChatMessage> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessage.Type.INCOMING) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatMessage chatMessage = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (view == null){
            if (getItemViewType(position) == 0){
                view = mInflater.inflate(R.layout.item_from_msg,viewGroup,false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) view.findViewById(R.id.from_time);
                viewHolder.mMsg = (TextView) view.findViewById(R.id.from_info);
            } else{
                view = mInflater.inflate(R.layout.item_to_msg,viewGroup,false);
                viewHolder = new ViewHolder();
                viewHolder.mDate = (TextView) view.findViewById(R.id.to_time);
                viewHolder.mMsg = (TextView) view.findViewById(R.id.to_info);
            }
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDate.setText(df.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());
        return view;
    }

    private final class ViewHolder {
        TextView mDate;
        TextView mMsg;
    }
}
