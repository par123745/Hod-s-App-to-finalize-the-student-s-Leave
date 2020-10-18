package com.isha.leave_hod;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Details extends AppCompatActivity {

    String StuId;
    TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        StuId = getIntent().getStringExtra("id");
        Toast.makeText(this, "" + getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();

        loadHeroList();
        t1 = findViewById(R.id.textView2);
        t2 = findViewById(R.id.textView3);
        t3 = findViewById(R.id.textView4);
        t4 = findViewById(R.id.textView5);
        t5 = findViewById(R.id.textView6);
        t6 = findViewById(R.id.textView7);
        t7 = findViewById(R.id.textView8);
        t8 = findViewById(R.id.textView9);
        t9 = findViewById(R.id.textView10);
        t10 = findViewById(R.id.textView11);


    }

    private void loadHeroList() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://malnirisha.xyz/leave/hod_details.php?id=" + StuId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("leave");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                String name = heroObject.getString("name");
                                String id = heroObject.getString("id");
                                String email = heroObject.getString("email");
                                String warden = heroObject.getString("warden");
                                String usn = heroObject.getString("usn");
                                String date = heroObject.getString("date");
                                String reason = heroObject.getString("reason");
                                String address = heroObject.getString("address");
                                String mobile = heroObject.getString("mob");
                                String parents = heroObject.getString("parents");
                                String date_issue = heroObject.getString("date_issue");
                                t1.setText("Name -" + name);
                                t2.setText("Email-" + email);
                                t3.setText("warden Mobile Number-" + warden);
                                t4.setText("USN-" + usn);
                                t5.setText("DATE-" + date);
                                t6.setText("Reason for Leave-" + reason);
                                t7.setText("Address-" + address);
                                t8.setText("Mobile no.-" + mobile);
                                t9.setText("Parent's mobile no-" + parents);
                                t10.setText("Date_ISSUE-" + date_issue);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


    public void GO(View view) {
        RequestQueue rq = Volley.newRequestQueue(Details.this);
        String url = "http://malnirisha.xyz/leave/hod_approve.php?id=" + StuId;
        url = url.replace(" ", "%20");
        Log.d("url", url);
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("1")) {
                    Toast.makeText(Details.this, "Approved", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                    String log = sp.getString("log", "");
                    Intent i = new Intent(Details.this, view.class);
                    i.putExtra("mob", log);
                    startActivity(i);
                    finish();
                } else if (response.trim().equals("0")) {
                    Toast.makeText(Details.this, "Failed", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Details.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);
    }

    public void CLICK(View view) {
        RequestQueue rq = Volley.newRequestQueue(Details.this);
        String url = "http://malnirisha.xyz/leave/hod_reject.php?id=" + StuId;
        url = url.replace(" ", "%20");
        Log.d("url", url);
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("1")) {
                    Toast.makeText(Details.this, "Rejected", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                    String log = sp.getString("log", "");
                    Intent i = new Intent(Details.this, view.class);
                    i.putExtra("email", log);
                    startActivity(i);
                    finish();
                } else if (response.trim().equals("0")) {
                    Toast.makeText(Details.this, "Failed", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Details.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setShouldCache(false);
        sr.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(sr);
    }
}