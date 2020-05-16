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
import com.example.cabinetapp.utils.GlobalData;
import com.example.cabinetapp.R;
import com.example.cabinetapp.adapter.CompanyAdapter;
import com.example.cabinetapp.entity.Company;

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

public class CompanyListActivity extends Activity {
    private ListView lv_company;
    private List<Company> list;
    private CompanyAdapter adapter;
    private ArrayList<ArrayList<String>> storeData;
    private static final String URL_COMPANY = URL_HEAD+"/send/company";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        initView();
        initEvent();
    }
    private void initView() {
        lv_company = findViewById(R.id.lv_company);
        list = new ArrayList<>();
        storeData = new ArrayList<>();
        getAllData();
    }

    private void initEvent() {
        lv_company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(CompanyListActivity.this, SendInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("company", storeData.get(position));
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    private void getAllData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_COMPANY,
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
                                JSONArray lists = body.getJSONArray("company_list");
                                for (int i = 0;i < lists.length();i ++){
                                    ArrayList arrayList = new ArrayList();
                                    JSONObject jsonObject = (JSONObject) lists.get(i);
                                    String company_id = jsonObject.getString("company_id");
                                    String company_name = jsonObject.getString("company_name");
                                    String exp_price = jsonObject.getString("exp_price");
                                    Company company = new Company();
                                    company.setCompany_id(company_id);
                                    company.setCompany_name(company_name);
                                    company.setExp_price(exp_price);
                                    arrayList.add(company_id);
                                    arrayList.add(company_name);
                                    list.add(company);
                                    storeData.add(arrayList);
                                }
                                Toast.makeText(CompanyListActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onResponse: size "+list.size() );
                                for (Iterator iterator = list.iterator(); iterator.hasNext();){
                                    Company company = (Company) iterator.next();
                                    Log.e(TAG, "onResponse: "+company.toString() );
                                }
                                adapter = new CompanyAdapter(getApplicationContext(),list);
                                lv_company.setAdapter(adapter);
                            }else {
                                Toast.makeText(CompanyListActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CompanyListActivity.this,"连接服务器失败，请检查网络连接",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> map = new HashMap<>();
                map.put("uid", GlobalData.getUid());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
