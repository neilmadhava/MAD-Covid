package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WorldStates extends AppCompatActivity {

    private Spinner spinner;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_states);

        RequestQueue queue = Volley.newRequestQueue(this);

        spinner = (Spinner)findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                url = "https://covid19.mathdro.id/api/countries/" +  parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Button getbtn = (Button)findViewById(R.id.button2);
        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView msg = (TextView)findViewById(R.id.msg);
                msg.setText("Loading...");
                RequestQueue queue = Volley.newRequestQueue(WorldStates.this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject object = null;
                                try {
                                    object = new JSONObject(response);
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    TextView confirm = (TextView)findViewById(R.id.confirm);
                                    confirm.setText(object.getJSONObject("confirmed").get("value").toString());
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                try {
                                    TextView confirm = (TextView)findViewById(R.id.recover);
                                    confirm.setText(object.getJSONObject("recovered").get("value").toString());
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                try {
                                    TextView confirm = (TextView)findViewById(R.id.death);
                                    confirm.setText(object.getJSONObject("deaths").get("value").toString());
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                                TextView msg = (TextView) findViewById(R.id.msg);
                                msg.setText("Success!");


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", "An error occurred" + error);
                        TextView msg = (TextView) findViewById(R.id.msg);
                        msg.setText("Error");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

    }
}