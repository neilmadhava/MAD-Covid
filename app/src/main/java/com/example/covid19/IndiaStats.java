package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IndiaStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india_stats);

        Button getbtn = (Button)findViewById(R.id.get);

        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView message = (TextView) findViewById(R.id.message);

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(IndiaStats.this);
                String url = getUrl();

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                TextView confirmed = (TextView)findViewById(R.id.confirmed);
                                TextView recovered = (TextView)findViewById(R.id.recovered);
                                TextView deceased = (TextView)findViewById(R.id.deceased);

                                try {
                                    confirmed.setText(getConfirmed(response, "confirmed"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    recovered.setText(getConfirmed(response, "recovered"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    deceased.setText(getConfirmed(response, "deceased"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.DATE, -1);

                                message.setText("Success!\n Last Updated: " + dateFormat.format(cal.getTime()));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("response", "onErrorResponse: " + error);
                        message.setText("That didn't work! Check internet connection.");
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
    }

    private String getUrl() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String url =  "https://api.covid19india.org/v4/data-"+dateFormat.format(cal.getTime())+".json";
        return url;
    }

    private String getConfirmed(String res, String status) throws Exception{
        JSONObject object = new JSONObject(res);
        int total = object.getJSONObject("TT").getJSONObject("total").getInt(status);
        int delta = object.getJSONObject("TT").getJSONObject("delta").getInt(status);

        if (delta>0)
                return "" + total + " [+" + delta + "]";
        return "" + total + " [" + delta + "]";
    }


}