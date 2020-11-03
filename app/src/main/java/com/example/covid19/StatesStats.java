package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatesStats extends AppCompatActivity  {

    private String stateID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states_stats);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
                JSONObject stateKey = null;
                try {
                    stateKey = new JSONObject("{'Andaman and Nicobar Islands':'AN','Andhra Pradesh':'AP','Arunachal Pradesh':'AR','Assam':'AS','Bihar':'BR','Chandigarh':'CH','Chhattisgarh':'CT','Delhi':'DL','Dadra and Nagar Haveli and Daman and Diu':'DN','Goa':'GA','Gujarat':'GJ','Himachal Pradesh':'HP','Haryana':'HR','Jharkhand':'JH','Jammu and Kashmir':'JK','Karnataka':'KA','Kerala':'KL','Ladakh':'LA','Maharashtra':'MH','Meghalaya':'ML','Manipur':'MN','Madhya Pradesh':'MP','Mizoram':'MZ','Nagaland':'NL','Odisha':'OR','Punjab':'PB','Puducherry':'PY','Rajasthan':'RJ','Sikkim':'SK','Telangana':'TG','Tamil Nadu':'TN','Tripura':'TR','Total':'TT','Uttar Pradesh':'UP','Uttarakhand':'UT','West Bengal':'WB'}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String state = parent.getItemAtPosition(pos).toString();
                try {
                    stateID = stateKey.getString(state);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button getbtn = (Button)findViewById(R.id.button);

        getbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView message = (TextView) findViewById(R.id.message);

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(StatesStats.this);
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
                                    confirmed.setText(getStatus(response, "confirmed", stateID));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    recovered.setText(getStatus(response, "recovered", stateID));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    deceased.setText(getStatus(response, "deceased", stateID));
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
        cal.add(Calendar.DATE, -2);
        String url =  "https://api.covid19india.org/v4/data-"+dateFormat.format(cal.getTime())+".json";
        return url;
    }

    private String getStatus(String res, String status, String state) throws Exception{
        JSONObject object = new JSONObject(res);
        int total = object.getJSONObject(state).getJSONObject("total").getInt(status);
        int delta = object.getJSONObject(state).getJSONObject("delta").getInt(status);

        if (delta>0)
            return "" + total + " [+" + delta + "]";
        return "" + total + " [" + delta + "]";
    }
}