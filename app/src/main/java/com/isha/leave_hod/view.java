package com.isha.leave_hod;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class view extends AppCompatActivity {


    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Model> leave;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        email=getIntent().getStringExtra("email");
        //initializing listview and hero list
        listView = (ListView) findViewById(R.id.list);
        leave = new ArrayList<>();

        //this method will fetch and parse the data
        loadHeroList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView Id =  view.findViewById(R.id.textView1);
                String stuId = Id.getText().toString();
                Intent i =new Intent(view.this,Details.class);
                i.putExtra("id",stuId);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.op1){
            SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor spe = sp.edit();
            spe.clear();
            spe.commit();
            Intent i=new Intent(view.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadHeroList() {
        //getting the progressbar
        /*final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);*/

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://malnirisha.xyz/leave/hod_get.php?e="+email,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       /* //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);*/


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("leave");

                            if(heroArray.length()==0){
                                Toast.makeText(view.this, "No Requests", Toast.LENGTH_SHORT).show();
                            }

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = heroArray.getJSONObject(i);

                                String name = heroObject.getString("name");
                                String id = heroObject.getString("id");


                                //creating a hero object and giving them the values from json object
                                Model model = new Model(name,id);

                                //adding the hero to herolist
                                leave.add(model);
                            }

                            //creating custom adapter object
                            List_adapter adapter = new List_adapter(leave, getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

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
}
