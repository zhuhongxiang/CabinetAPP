package com.example.cabinetapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cabinetapp.R;
import com.example.cabinetapp.adapter.BinAdapter;
import com.example.cabinetapp.entity.Bin;
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


public class SendSelectBinActivity extends Activity {
    private TextView tv_cabinetNumber;
    private ListView lv_bin;
    private List<Bin> list;
    private BinAdapter adapter;
    private ArrayList<ArrayList<String>> storeData;


    private static final String URL_OPEN = URL_HEAD+"/send/bininfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_selectbin);
        initView();
        initEvent();
    }

    private void initView() {
        tv_cabinetNumber = findViewById(R.id.tv_cabinetNumber);
        lv_bin = findViewById(R.id.lv_bin);
        list = new ArrayList<>();
        storeData = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> list = bundle.getStringArrayList("all");
        tv_cabinetNumber.setText("柜体编号:"+list.get(0));
        final String id = list.get(0);
        getAllData(id);

    }

    private void initEvent() {
        lv_bin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(SendSelectBinActivity.this, SendInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("all", storeData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getAllData(final String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_OPEN,
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
                                JSONArray lists = body.getJSONArray("bin_list");
                                for (int i = 0;i < lists.length();i ++){
                                    ArrayList arrayList = new ArrayList();
                                    Bin bin = new Bin();
                                    JSONObject jsonObject = (JSONObject) lists.get(i);
                                    String bin_id = jsonObject.getString("bin_id");
                                    bin.setBin_id("格口编号： "+bin_id);
                                    arrayList.add(id);
                                    arrayList.add(bin_id);
                                    String type = jsonObject.getString("type");
                                    switch (type){
                                        case "10901":
                                            bin.setType("格口类型： 大箱");
                                            bin.setPrice("价格：48元");
                                            arrayList.add("大箱");
                                            arrayList.add("价格：48元");
                                            break;
                                        case "10902":
                                            bin.setType("格口类型： 中箱");
                                            bin.setPrice("价格：30元");
                                            arrayList.add("中箱");
                                            arrayList.add("价格：30元");
                                            break;
                                        case "10903":
                                            bin.setType("格口类型： 小箱");
                                            bin.setPrice("价格：17元");
                                            arrayList.add("小箱");
                                            arrayList.add("价格：17元");
                                            break;
                                        case "10904":
                                            bin.setType("格口类型： 超小箱");
                                            bin.setPrice("价格：10元");
                                            arrayList.add("超小箱");
                                            arrayList.add("价格：10元");
                                        default:
                                            break;
                                    }

                                    list.add(bin);
                                    storeData.add(arrayList);
                                }
                                Toast.makeText(SendSelectBinActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: size "+list.size() );
                                for (Iterator iterator = list.iterator(); iterator.hasNext();){
                                    Bin bin = (Bin) iterator.next();
                                    Log.e(TAG, "onResponse: "+bin.toString() );
                                }
                                adapter = new BinAdapter(getApplicationContext(),list);
                                lv_bin.setAdapter(adapter);
                            }else {
                                Toast.makeText(SendSelectBinActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SendSelectBinActivity.this,"连接服务器失败，请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", GlobalData.getUid());
                map.put("cab_id",id);
                map.put("status","0");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}

