package com.example.cabinetapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cabinetapp.R;
import com.example.cabinetapp.entity.Sender;

import java.util.List;


public class SenderAdapter extends BaseAdapter {
    private List<Sender> mContentList;
    private LayoutInflater mInflater;
    private MyClickListener mListener;

    public SenderAdapter(Context context, List<Sender> contentList, MyClickListener listener) {
       mContentList = contentList;
       mInflater = LayoutInflater.from(context);
       mListener = listener;
   }

    @Override
    public int getCount() {
        return mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.e("TAG", "getView: "+position);
        Sender sender = mContentList.get(position);
        ViewHolder viewHolder = null;
        if (sender == null){
            return null;
        }
        if (view != null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = mInflater.inflate(R.layout.list_item_sender,null);
            viewHolder = new ViewHolder();

            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_phone = view.findViewById(R.id.tv_phone);
            viewHolder.tv_dizhi = view.findViewById(R.id.tv_dizhi);
            viewHolder.ibt_editsender = view.findViewById(R.id.ibt_editsender);
            view.setTag(viewHolder);
        }
        viewHolder.tv_name.setText(sender.getSend_name());
        viewHolder.tv_phone.setText(sender.getSend_phone());
        viewHolder.tv_dizhi.setText(sender.getSend_addr()+sender.getSend_detail());
        viewHolder.ibt_editsender.setOnClickListener(mListener);
        viewHolder.ibt_editsender.setTag(position);
        return view;
    }

    private static class ViewHolder{
        public TextView tv_name;
        public TextView tv_phone;
        public TextView tv_dizhi;
        public ImageButton ibt_editsender;
    }

    /**
    */
    public static abstract class MyClickListener implements View.OnClickListener {
        /**
         * 基类的onClick方法
          */
        @Override
    public void onClick(View v) {
                   myOnClick((Integer) v.getTag(), v);
                   }
        public abstract void myOnClick(int position, View v);
    }

}
