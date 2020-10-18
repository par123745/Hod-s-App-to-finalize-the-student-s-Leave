package com.isha.leave_hod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class registration extends AppCompatActivity {
EditText e1,e2,e3;
String name,email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        e1=findViewById(R.id.editText4);
        e2=findViewById(R.id.editText5);
        e3=findViewById(R.id.editText6);
    }

    public void submit(View view) {
        name = e1.getText().toString();
        email=e2.getText().toString();
        pass=e3.getText().toString();


        RequestQueue rq= Volley.newRequestQueue(registration.this);
        String url= "http://malnirisha.xyz/leave/hod_reg.php?n="+name+"&e="+email+"&p="+pass;
        url = url.replace(" ", "%20");
        Log.d("url",url);
        StringRequest sr=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("1")){
                    Toast.makeText(registration.this, "success", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(registration.this,MainActivity.class);
                    startActivity(i);
                }
                else if(response.trim().equals("0")){
                    Toast.makeText(registration.this, "mobile number exists", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(registration.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);
    }
}
