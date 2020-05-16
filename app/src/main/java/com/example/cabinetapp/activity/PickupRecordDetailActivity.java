package com.example.cabinetapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cabinetapp.R;

import java.util.ArrayList;

public class PickupRecordDetailActivity extends Activity {
    //声明控件
    private TextView tv_numberRecord;
    private TextView tv_phoneRecord;
    private TextView tv_addressRecord;
    private TextView tv_deliveryTimeRecord;
    private TextView tv_pickupTimeRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_record_detail);
        initView();
    }
    //通过ID获取布局文件中的控件
    private void initView() {
        tv_numberRecord = findViewById(R.id.tv_numberRecord);
        tv_phoneRecord = findViewById(R.id.tv_phoneRecord);
        tv_addressRecord = findViewById(R.id.tv_addressRecord);
        tv_deliveryTimeRecord = findViewById(R.id.tv_deliveryTimeRecord);
        tv_pickupTimeRecord = findViewById(R.id.tv_pickupTimeRecord);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> list = bundle.getStringArrayList("all");
        tv_numberRecord.setText(list.get(0));
        tv_phoneRecord.setText(list.get(1));
        tv_addressRecord.setText(list.get(2));
        tv_deliveryTimeRecord.setText(list.get(3));
        tv_pickupTimeRecord.setText(list.get(4));
    }
}
