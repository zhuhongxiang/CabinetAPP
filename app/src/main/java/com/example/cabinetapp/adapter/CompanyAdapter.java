package com.example.cabinetapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.cabinetapp.R;
import com.example.cabinetapp.entity.Company;

import java.util.List;


public class CompanyAdapter extends BaseAdapter {
    private Context context;
    private List<Company> list;

    public CompanyAdapter(Context context, List<Company> list) {
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
        Company company = list.get(position);
        ViewHolder viewHolder = null;
        if (company == null){
            return null;
        }
        if (view != null){
            viewHolder = (ViewHolder) view.getTag();
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_company,null);
            viewHolder = new ViewHolder();

            viewHolder.tv_companyname = view.findViewById(R.id.tv_companyname);
            viewHolder.tv_exp_price = view.findViewById(R.id.tv_exp_price);
            viewHolder.im_logo = view.findViewById(R.id.im_logo);
            view.setTag(viewHolder);
        }
        viewHolder.tv_companyname.setText(company.getCompany_name());
        viewHolder.tv_exp_price.setText(company.getExp_price());
        if (company.getCompany_name().equals("京东快递")){
            Drawable drawable = ResourcesCompat.getDrawable(view.getResources(), R.drawable.lg_jd, null);
            viewHolder.im_logo.setBackground(drawable);
        }else if (company.getCompany_name().equals("中通快递")){
            Drawable drawable = ResourcesCompat.getDrawable(view.getResources(), R.drawable.lg_zt, null);
            viewHolder.im_logo.setBackground(drawable);
        }
        return view;
    }

    private static class ViewHolder{
        public TextView tv_companyname;
        public TextView tv_exp_price;
        public ImageView im_logo;
    }
}
