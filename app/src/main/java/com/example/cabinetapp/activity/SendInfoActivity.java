package com.example.cabinetapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
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

import static com.example.cabinetapp.utils.GlobalData.URL_HEAD;

public class SendInfoActivity extends Activity implements View.OnClickListener {
    private TextView tv_bin;
    private TextView tv_binprice;
    public TextView tv_send;
    public TextView tv_receive;
    public TextView tv_company;
    private Button bt_xiadan;
    private RelativeLayout rl_send;
    private RelativeLayout rl_receive;
    private RelativeLayout rl_company;
    private CheckBox cb_yuedu;
    private String str_send;
    private String str_receive;
    private String str_company;
    private String str_cab;
    private String str_bin;
    private String str_senderid;
    private String str_receiverid;
    private String str_companyid;

    private static final String URL_FINISH = URL_HEAD+"/send/sendorder/new";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_info);
        initView();
        initEvent();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        tv_bin = findViewById(R.id.tv_bin);
        tv_binprice = findViewById(R.id.tv_binprice);
        tv_send = findViewById(R.id.tv_send);
        tv_receive = findViewById(R.id.tv_receive);
        tv_company = findViewById(R.id.tv_company);

        bt_xiadan = findViewById(R.id.bt_xiadan);
        rl_send = findViewById(R.id.rl_send);
        rl_receive = findViewById(R.id.rl_receive);
        rl_company = findViewById(R.id.rl_company);
        cb_yuedu = findViewById(R.id.cb_yuedu);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> list = bundle.getStringArrayList("all");
        tv_bin.setText("您选择了"+list.get(0)+"号柜"+list.get(1)+"号"+list.get(2));
        tv_binprice.setText( list.get(3));
        str_cab = list.get(0);
        str_bin = list.get(1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if(resultCode==RESULT_OK){
                Bundle bundle = data.getExtras();
                ArrayList<String> list1 = bundle.getStringArrayList("sender");
                tv_send.setText(list1.get(0)+'\n'+list1.get(1)+'\n'+list1.get(2));
                str_senderid = list1.get(3);
            }
        }else if (requestCode==2){
            if(resultCode==RESULT_OK){
                Bundle bundle = data.getExtras();
                ArrayList<String> list2 = bundle.getStringArrayList("receiver");
                tv_receive.setText(list2.get(0)+'\n'+list2.get(1)+'\n'+list2.get(2));
                str_receiverid = list2.get(3);
            }
        }else if (requestCode==3){
            if(resultCode==RESULT_OK){
                Bundle bundle = data.getExtras();
                ArrayList<String> list3 = bundle.getStringArrayList("company");
                tv_company.setText(list3.get(1));
                tv_company.setTextColor(Color.parseColor("#333333"));
                str_companyid = list3.get(0);
            }
        }
    }

    private void initEvent() {
        rl_send.setOnClickListener(this);
        rl_receive.setOnClickListener(this);
        rl_company.setOnClickListener(this);
        bt_xiadan.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_send:
                Intent intent = new Intent(SendInfoActivity.this, SenderListActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.rl_receive:
                Intent intent1 = new Intent(SendInfoActivity.this, ReceiverListActivity.class);
                startActivityForResult(intent1,2);
                break;
            case R.id.rl_company:
                str_send = tv_send.getText().toString().trim();
                str_receive = tv_receive.getText().toString().trim();
                if (str_send.equals("从哪里寄")){
                    Toast.makeText(SendInfoActivity.this,"请填写寄件信息",
                            Toast.LENGTH_SHORT).show();
                }else if (str_receive.equals("寄到哪里")){
                    Toast.makeText(SendInfoActivity.this,"请填写收件信息",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent2 = new Intent(SendInfoActivity.this, CompanyListActivity.class);
                    startActivityForResult(intent2,3);
                }
                break;
            case R.id.bt_xiadan:
                str_company = tv_company.getText().toString().trim();
                if (str_send.equals("从哪里寄")){
                    Toast.makeText(SendInfoActivity.this,"请填写寄件信息",
                            Toast.LENGTH_SHORT).show();
                }else if (str_receive.equals("寄到哪里")){
                    Toast.makeText(SendInfoActivity.this,"请填写收件信息",
                            Toast.LENGTH_SHORT).show();
                }else if (str_company.equals("请选择")){
                    Toast.makeText(SendInfoActivity.this,"请选择快递公司",
                            Toast.LENGTH_SHORT).show();
                }else if (!cb_yuedu.isChecked()){
                    Toast.makeText(SendInfoActivity.this,"请勾选已阅读并同意《服务协议》",
                            Toast.LENGTH_SHORT).show();
                }else{
                    postToServe();
                }

                break;
            default:
                break;
        }
    }

    /*
    向服务器发送下单信息
     */
    private void postToServe() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_FINISH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String code = json.getString("code");
                            String msg = json.getString("msg");
                            if (code.equals("0")){
                                Toast.makeText(SendInfoActivity.this,"下单成功",Toast.LENGTH_SHORT).show();
                                new Handler(new Handler.Callback() {
                                    @Override
                                    public boolean handleMessage(@NonNull Message msg) {
                                        finish();
                                        return false;
                                    }
                                }).sendEmptyMessageDelayed(0x123,2000);//延时2秒关闭
                            }else {
                                Toast.makeText(SendInfoActivity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SendInfoActivity.this,"连接服务器失败，请检查网络连接",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("uid", GlobalData.getUid());
                map.put("cab_id",str_cab);
                map.put("bin_id",str_bin);
                map.put("sender_id",str_senderid);
                map.put("receiver_id",str_receiverid);
                map.put("company_id",str_companyid);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
