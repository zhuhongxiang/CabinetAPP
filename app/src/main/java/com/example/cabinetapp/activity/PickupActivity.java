package com.example.cabinetapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cabinetapp.adapter.PickUpAdapter;
import com.example.cabinetapp.R;
import com.example.cabinetapp.entity.PickUp;
import com.example.cabinetapp.utils.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.cabinetapp.utils.GlobalData.URL_HEAD;


public class PickupActivity extends Activity {
    //声明控件
    private ListView lv_pickup;
    private PickUpAdapter adapter;
    private List<PickUp> lists;
    private ArrayList<ArrayList<String>> storeData;
    private static final String URL_PICKUP = URL_HEAD+"/pickup/list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);
        init();
        initEvent();
    }
    private void init() {
        //通过ID获取ListView控件
        lv_pickup = findViewById(R.id.lv_pickup);
        lists = new ArrayList<>();
        storeData = new ArrayList<>();
        postToServer();
    }
    //为ListView绑定单击监听器
    private void initEvent() {
        lv_pickup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(PickupActivity.this, PickupDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("all", storeData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
    //向服务器查询待取件信息
    private void postToServer() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PICKUP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("TAG", "onResponse: content"+response );
                            String code = jsonObject.getString("code");
                            if (code.equals("0")){
                                Toast.makeText(PickupActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                                JSONObject body = jsonObject.getJSONObject("body");
                                JSONArray order_list = body.getJSONArray("order_list");
                                for (int i = 0;i<order_list.length();i++){
                                    ArrayList arrayList = new ArrayList();
                                    JSONObject list = (JSONObject) order_list.get(i);
                                    String in_time = list.getString("in_time");
                                    String open_code = list.getString("open_code");
                                    JSONObject addr_info = list.getJSONObject("addr_info");
                                    String number = list.getString("exp_code");
                                    String address = addr_info.getString("name")+addr_info.getString("desc");
                                    PickUp pickUp = new PickUp();
                                    pickUp.setCode(number);
                                    pickUp.setAddress(address);
                                    pickUp.setOpenCode(open_code);
                                    pickUp.setDeliveryTime(in_time);
                                    arrayList.add("投递订单号： "+number);
                                    arrayList.add("存储位置："+address);
                                    arrayList.add("投递时间： "+in_time);
                                    arrayList.add("取件密码： "+open_code);
                                    arrayList.add(list.getString("id"));
                                    lists.add(pickUp);
                                    storeData.add(arrayList);
                                    Log.e("TAG", "onResponse: "+pickUp.toString() );
                                }
                                adapter = new PickUpAdapter(getApplicationContext(),lists);
                                lv_pickup.setAdapter(adapter);
                            }else {
                                Toast.makeText(PickupActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PickupActivity.this, "连接服务器失败，请检查网络", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> map = new HashMap<>();
                map.put("uid", GlobalData.getUid());
                map.put("status","1");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
