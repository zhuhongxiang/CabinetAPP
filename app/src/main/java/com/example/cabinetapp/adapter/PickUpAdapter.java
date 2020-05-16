package com.example.cabinetapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cabinetapp.R;
import com.example.cabinetapp.entity.PickUp;

import java.util.List;

public class PickUpAdapter extends BaseAdapter {
    private Context context;
    private List<PickUp> list;
    private String orderID;

    //建立PickUpAdapter方法
    public PickUpAdapter(Context context, List<PickUp> list) {
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
    @Override
    //建立getView方法，实现对应控件的获取
    public View getView(int position, View view, ViewGroup viewGroup) {
        final PickUp pickUp = list.get(position);
        ViewHolder holder = null;
        if (list == null){
            return null;
        }
        if (view != null){
            holder = (ViewHolder) view.getTag();
        }else {
            //通过ID获取具体的控件
            view = LayoutInflater.from(context).inflate(R.layout.list_item_pickup,null);
            holder = new ViewHolder();
            holder.tv_waybillNumberPickup = view.findViewById(R.id.tv_waybillNumberPickup);
            holder.tv_address = view.findViewById(R.id.tv_address);
            holder.tv_deliveryTime = view.findViewById(R.id.tv_deliveryTime);
            view.setTag(holder);

        }
        //设置对应控件的内容
        holder.tv_waybillNumberPickup.setText("投递订单编号："+pickUp.getCode());
        holder.tv_address.setText("存储位置："+pickUp.getAddress());
        holder.tv_deliveryTime.setText("投递时间："+pickUp.getDeliveryTime());
        return view;
    }
    //建立ViewHolder类，实例化控件
    private static class ViewHolder{
        public TextView tv_waybillNumberPickup;
        public TextView tv_address;
        public TextView tv_deliveryTime;
    }

}
