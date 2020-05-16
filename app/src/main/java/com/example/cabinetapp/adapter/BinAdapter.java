package com.example.cabinetapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cabinetapp.R;
import com.example.cabinetapp.entity.Bin;

import java.util.List;


public class BinAdapter extends BaseAdapter {
    private Context context;
    private List<Bin> list;

    public BinAdapter(Context context, List<Bin> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.e("TAG", "getView: "+position);
        Bin bin = list.get(position);
        ViewHolder viewHolder = null;
        if (bin == null){
            return null;
        }
        if (view != null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_bin,null);
            viewHolder = new ViewHolder();

            viewHolder.tv_bin_id = view.findViewById(R.id.tv_bin_id);
            viewHolder.tv_type = view.findViewById(R.id.tv_type);
            viewHolder.tv_price = view.findViewById(R.id.tv_price);

            view.setTag(viewHolder);
        }
        viewHolder.tv_bin_id.setText(bin.getBin_id());
        viewHolder.tv_type.setText(bin.getType());
        viewHolder.tv_price.setText(bin.getPrice());
        return view;
    }

    private static class ViewHolder{
        public TextView tv_bin_id;
        public TextView tv_type;
        public TextView tv_price;
    }
}
