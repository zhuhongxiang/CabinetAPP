package com.example.cabinetapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cabinetapp.R;
import com.example.cabinetapp.utils.GlobalData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;
import static com.example.cabinetapp.utils.GlobalData.URL_HEAD;

public class PickupDetailActivity extends Activity {
    private TextView tv_waybillNumberPickup;
    private TextView tv_phoneRecord;
    private TextView tv_address;
    private TextView tv_deliveryTimePickup;
    private TextView tv_passwordPickup;
    private Button btn_pickup;

    private static final String URL_APPLICATION = URL_HEAD+"/pickup/apply";
    private static final String URL_CHECK = URL_HEAD+"/pickup/check";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickupdetail);
        initView();
    }

    private void initView() {
        tv_waybillNumberPickup = findViewById(R.id.tv_waybillNumberPickup);
        tv_address = findViewById(R.id.tv_address);
        tv_deliveryTimePickup = findViewById(R.id.tv_deliveryTimePickup);
        tv_passwordPickup = findViewById(R.id.tv_passwordPickup);
        btn_pickup = findViewById(R.id.btn_pickup);
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> list = bundle.getStringArrayList("all");
        tv_waybillNumberPickup.setText(list.get(0));
        tv_address.setText(list.get(1));
        tv_deliveryTimePickup.setText(list.get(2));
        tv_passwordPickup.setText(list.get(3));
        final String id = list.get(4);
        Log.e(TAG, "initView: "+id );
        btn_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToServer(id);
            }
        });
    }

    //向服务器发送取件请求
    private void sendToServer(final String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_APPLICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "sendToServer: "+response );
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            String code = jsonObject.getString("code");
                            if (code.equals("0")){
                                check(id);
                            }else if (code.equals("20001")){
                                Toast.makeText(PickupDetailActivity.this,"用户不存在",Toast.LENGTH_SHORT).show();
                            }else if (code.equals("10001")){
                                Toast.makeText(PickupDetailActivity.this,"柜体无效",Toast.LENGTH_SHORT).show();
                            }else if (code.equals("10002")){
                                Toast.makeText(PickupDetailActivity.this,"格口无效",Toast.LENGTH_SHORT).show();
                            }else if (code.equals("15101")){
                                Toast.makeText(PickupDetailActivity.this,"订单不存在",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PickupDetailActivity.this, "连接服务器失败，请检查网络", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("uid", GlobalData.getUid());
                map.put("order_id",id);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    //检查取件申请
    private void check(final String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "sendToServer: "+response );
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.getString("msg");
                            String code = jsonObject.getString("code");
                            if (code.equals("0")){
                                JSONObject body = jsonObject.getJSONObject("body");
                                boolean is_pick = body.getBoolean("is_pick");
                                if (is_pick){
                                    Toast.makeText(PickupDetailActivity.this,"取件成功",Toast.LENGTH_SHORT).show();
                                    new Handler(new Handler.Callback() {
                                        @Override
                                        public boolean handleMessage(@NonNull Message msg) {
                                            finish();
                                            return false;
                                        }
                                    }).sendEmptyMessageDelayed(0x123,2000);//延时2秒关闭
                                }else {
                                    Toast.makeText(PickupDetailActivity.this,msg,Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(PickupDetailActivity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PickupDetailActivity.this, "连接服务器失败，请检查网络", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("uid",GlobalData.getUid());
                map.put("order_id",id);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}