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
import com.example.cabinetapp.adapter.ReceiverAdapter;
import com.example.cabinetapp.entity.Receiver;
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

public class ReceiverListActivity extends Activity {
    private ListView lv_receiver;
    private Button btn_newreceiver;
    private List<Receiver> list;
    private ReceiverAdapter adapter;
    private ArrayList<ArrayList<String>> storeData;
    private static final String URL_RECEIVER = URL_HEAD+"/send/receiver/select";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_list);
        initView();
        initEvent();
    }
    private void initView() {
        lv_receiver = findViewById(R.id.lv_receiver);
        btn_newreceiver = findViewById(R.id.btn_newreceiver);
        list = new ArrayList<>();
        storeData = new ArrayList<>();
        btn_newreceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiverListActivity.this, ReceiverNewActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getAllData();
    }

    private void initEvent() {
        lv_receiver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ReceiverListActivity.this, SendInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("receiver", storeData.get(position));
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private void getAllData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RECEIVER,
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
                                JSONArray lists = body.getJSONArray("receiver_list");
                                for (int i = 0;i < lists.length();i ++){
                                    ArrayList arrayList = new ArrayList();
                                    JSONObject jsonObject = (JSONObject) lists.get(i);
                                    String rec_name = jsonObject.getString("rec_name");
                                    String rec_phone = jsonObject.getString("rec_phone");
                                    String rec_addr = jsonObject.getString("rec_addr");
                                    String rec_detail = jsonObject.getString("rec_detail");
                                    String receiver_id = jsonObject.getString("receiver_id");
                                    Receiver receiver = new Receiver();
                                    receiver.setRec_name(rec_name);
                                    receiver.setRec_phone(rec_phone);
                                    receiver.setRec_addr(rec_addr);
                                    receiver.setRec_detail(rec_detail);
                                    receiver.setReceiver_id(receiver_id);
                                    arrayList.add(rec_name);
                                    arrayList.add(rec_phone);
                                    arrayList.add(rec_addr+rec_detail);
                                    arrayList.add(receiver_id);
                                    arrayList.add(rec_addr);
                                    arrayList.add(rec_detail);
                                    list.add(receiver);
                                    storeData.add(arrayList);
                                }
                                Toast.makeText(ReceiverListActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: size "+list.size() );
                                for (Iterator iterator = list.iterator(); iterator.hasNext();){
                                    Receiver receiver = (Receiver) iterator.next();
                                    Log.e(TAG, "onResponse: "+receiver.toString() );
                                }
                                adapter = new ReceiverAdapter(getApplicationContext(),list,mListener);
                                lv_receiver.setAdapter(adapter);
                            }else {
                                Toast.makeText(ReceiverListActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReceiverListActivity.this,"连接服务器失败，请检查网络连接",Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(ReceiverListActivity.this, ReceiverEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("receiver", storeData.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    };
}
