package com.example.cabinetapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cabinetapp.R;
import com.example.cabinetapp.entity.Record;

import java.util.List;


public class RecordAdapter extends BaseAdapter {
    private Context context;
    private List<Record> list;

    public RecordAdapter(Context context, List<Record> list) {
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
        Record record = list.get(position);
        ViewHolder viewHolder = null;
        if (record == null){
            return null;
        }
        if (view != null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_record,null);
            viewHolder = new ViewHolder();

            viewHolder.tv_waybillNumber = view.findViewById(R.id.tv_waybillNumber);
            viewHolder.tv_deliveryTime = view.findViewById(R.id.tv_deliveryTime);
            viewHolder.tv_deliveryStatus = view.findViewById(R.id.tv_deliveryStatus);

            view.setTag(viewHolder);
        }
        viewHolder.tv_waybillNumber.setText(record.getDeliveryNumber());
        viewHolder.tv_deliveryTime.setText(record.getDeliveryTime());
        if (record.getStatus().equals("未揽件")||record.getStatus().equals("未取件")){
            viewHolder.tv_deliveryStatus.setTextColor(Color.RED);
        }else {
            viewHolder.tv_deliveryStatus.setTextColor(Color.BLUE);
        }
        viewHolder.tv_deliveryStatus.setText(record.getStatus());
        return view;
    }

    private static class ViewHolder{
        public TextView tv_waybillNumber;
        public TextView tv_deliveryTime;
        public TextView tv_deliveryStatus;
    }
}
