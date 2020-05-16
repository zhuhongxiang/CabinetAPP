package com.example.cabinetapp.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cabinetapp.utils.AddressPickTask;
import com.example.cabinetapp.R;
import com.example.cabinetapp.utils.GlobalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pickers.entity.City;
import pickers.entity.County;
import pickers.entity.Province;

import static com.example.cabinetapp.utils.GlobalData.URL_HEAD;

public class SenderEditActivity extends Activity {

    private EditText et_name;
    private EditText et_phone;
    private EditText et_addr;
    private EditText et_addrdetail;
    private LinearLayout ll_addr;
    private Button btn_savesender;
    private String str_name;
    private String str_phone;
    private String str_addr;
    private String str_addrdetail;
    private  String id;
    private static final String URL_NEWSENDER = URL_HEAD+"/send/sender/edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_edit);
        initView();
        initEvent();
    }
    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_addr = findViewById(R.id.et_addr);
        et_addrdetail = findViewById(R.id.et_addrdetail);
        ll_addr = findViewById(R.id.ll_addr);
        btn_savesender = findViewById(R.id.btn_savesender);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> list = bundle.getStringArrayList("sender");
        et_name.setText(list.get(0));
        et_phone.setText(list.get(1));
        et_addr.setText(list.get(4));
        et_addrdetail.setText(list.get(5));
        id = list.get(3);

    }

    private void initEvent() {
        btn_savesender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_name = et_name.getText().toString().trim();
                str_phone = et_phone.getText().toString().trim();
                str_addr = et_addr.getText().toString().trim();
                str_addrdetail = et_addrdetail.getText().toString().trim();
                final String id1 = id;
                postToServer(id1);
            }
        });

    }


    public void onAddressPicker(View view) {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                Toast.makeText(SenderEditActivity.this,"数据初始化失败",Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    et_addr.setText(province.getAreaName() + city.getAreaName());
                } else {
                    et_addr.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }
            }
        });
        task.execute("北京市", "北京市", "东城区");
    }
    private void postToServer(final String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NEWSENDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            String code = jsonObject.getString("code");
                            if (code.equals("0")){
                                Toast.makeText(SenderEditActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                                new Handler(new Handler.Callback() {
                                    @Override
                                    public boolean handleMessage(@NonNull Message msg) {
                                        Intent intent =new Intent(SenderEditActivity.this, SenderListActivity.class);
                                        startActivity(intent);
                                        return false;
                                    }
                                }).sendEmptyMessageDelayed(0x123,2000);//延时2秒关闭
                            }else{
                                Toast.makeText(SenderEditActivity.this,"保存失败，"+msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SenderEditActivity.this,"连接服务器失败，请检查网络连接",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map = new HashMap<>();
                map.put("uid", GlobalData.getUid());
                map.put("sender_id",id);
                map.put("send_name",str_name);
                map.put("send_phone",str_phone);
                map.put("send_addr",str_addr);
                map.put("send_detail",str_addrdetail);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
