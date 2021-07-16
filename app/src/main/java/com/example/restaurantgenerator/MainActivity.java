package com.example.restaurantgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mainLinearLayout;
    LinearLayout restaurantLinearLayout;
    TextView restaurantTextView;
    Button addBtn;
    Button readyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // click listener for go back button in listed restaurants
        View listedGoBack = findViewById(R.id.listed_gobackBtn);
        listedGoBack.setOnClickListener(this); // unsure

        mainLinearLayout = (LinearLayout)findViewById(R.id.mainLinearLayout);
        readyBtn = (Button)findViewById(R.id.readyBtn);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=&location=36.66717694044335,-121.65614460655894&radius=16000&type=restaurant";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray restaurants = (JSONArray)jsonObject.get("results");
                    for (int i = 0; i < restaurants.length(); i++) {
                        JSONObject obj = restaurants.getJSONObject(i);
                        String restaurantName = obj.getString("name");
                        restaurantLinearLayout = new LinearLayout(MainActivity.this);
                        restaurantLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        restaurantTextView = new TextView(MainActivity.this);
                        restaurantTextView.setText(restaurantName);
                        addBtn = new Button(MainActivity.this);
                        addBtn.setText("Add");
                        addBtn.setWidth(3);

                        restaurantLinearLayout.addView(restaurantTextView);
                        restaurantLinearLayout.addView(addBtn);

                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getApplicationContext(), restaurantName + " clicked", Toast.LENGTH_SHORT).show();
                                // add the restaurant info to the user's list
                            }
                        });
                        mainLinearLayout.addView(restaurantLinearLayout);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "API request failed", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);

        readyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Ready clicked", Toast.LENGTH_SHORT).show();
                Intent userPicks = new Intent(MainActivity.this, UserPicks.class);
                startActivity(userPicks);
            }
        });
    }

    public void onClick(View v) {
        if(v.getId() == R.id.listed_gobackBtn) {
            // button will direct user to cover page(temporarily until categories is set up)
            Intent i = new Intent(this, CoverPage.class);
            startActivity(i);
        }
    }

}
