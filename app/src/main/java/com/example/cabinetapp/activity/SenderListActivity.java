package com.example.cabinetapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cabinetapp.R;
import com.example.cabinetapp.adapter.SenderAdapter;
import com.example.cabinetapp.entity.Sender;
import com.example.cabinetapp.utils.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.cabinetapp.utils.GlobalData.URL_HEAD;

public class SenderListActivity extends Activity {
    private ListView lv_sender;
    private Button btn_newsender;
    private List<Sender> list;
    private SenderAdapter adapter;
    private ArrayList<ArrayList<String>> storeData;
    private static final String URL_SENDER = URL_HEAD+"/send/sender/select";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_list);
        initView();
        initEvent();
    }
    private void initView() {
        lv_sender = findViewById(R.id.lv_sender);
        btn_newsender = findViewById(R.id.btn_newsender);
        list = new ArrayList<>();
        storeData = new ArrayList<>();
        btn_newsender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SenderListActivity.this, SenderNewActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getAllData();
    }

    private void initEvent() {
        lv_sender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(SenderListActivity.this, SendInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("sender", storeData.get(position));
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
    private void getAllData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SENDER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String code = obj.getString("code");
                            if (code.equals("0")){
                                JSONObject body = obj.getJSONObject("body");
                                int count = body.getInt("count");
                                Log.e(TAG, "onResponse: "+count );
                                JSONArray lists = body.getJSONArray("sender_list");
                                for (int i = 0;i < lists.length();i ++){
                                    ArrayList arrayList = new ArrayList();
                                    JSONObject jsonObject = (JSONObject) lists.get(i);
                                    String send_name = jsonObject.getString("send_name");
                                    String send_phone = jsonObject.getString("send_phone");
                                    String send_addr = jsonObject.getString("send_addr");
                                    String send_detail = jsonObject.getString("send_detail");
                                    String sender_id = jsonObject.getString("sender_id");
                                    Sender sender = new Sender();
                                    sender.setSend_name(send_name);
                                    sender.setSend_phone(send_phone);
                                    sender.setSend_addr(send_addr);
                                    sender.setSend_detail(send_detail);
                                    sender.setSender_id(sender_id);
                                    arrayList.add(send_name);
                                    arrayList.add(send_phone);
                                    arrayList.add(send_addr+send_detail);
                                    arrayList.add(sender_id);
                                    arrayList.add(send_addr);
                                    arrayList.add(send_detail);
                                    list.add(sender);
                                    storeData.add(arrayList);
                                }
                                Toast.makeText(SenderListActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: size "+list.size() );
                                for (Iterator iterator = list.iterator(); iterator.hasNext();){
                                    Sender sender = (Sender) iterator.next();
                                    Log.e(TAG, "onResponse: "+sender.toString() );
                                }
                                adapter = new SenderAdapter(getApplicationContext(),list,mListener);
                                lv_sender.setAdapter(adapter);
                            }else {
                                Toast.makeText(SenderListActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SenderListActivity.this,"连接服务器失败，请检查网络连接",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map = new HashMap<>();
                map.put("uid", GlobalData.getUid());
                map.put("orderby","asc");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    /**
     * 实现类，响应按钮点击事件
     */
    private SenderAdapter.MyClickListener mListener = new SenderAdapter.MyClickListener() {
        @Override
        public void myOnClick(int position, View v) {
            Intent intent = new Intent(SenderListActivity.this, SenderEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("sender", storeData.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    };
}
