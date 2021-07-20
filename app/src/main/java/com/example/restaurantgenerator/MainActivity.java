
package com.example.restaurantgenerator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RestaurantDb db;    // create a db, so users can add their restaurants into their list

    LinearLayout mainLinearLayout;
    LinearLayout restaurantLinearLayout;
    TextView restaurantTextView;
    Button addBtn;
    Button listedGoBack;
    Button readyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creates an instance of the database when the app launches
        db = RestaurantDb.getInstance(this);

        listedGoBack = (Button)findViewById(R.id.listed_gobackBtn);
        listedGoBack.setOnClickListener(this);

        mainLinearLayout = (LinearLayout)findViewById(R.id.mainLinearLayout);
        readyBtn = (Button)findViewById(R.id.readyBtn);
        readyBtn.setOnClickListener(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=" + API.key +"&location=36.66717694044335,-121.65614460655894&radius=16000&type=restaurant";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mainLinearLayout = (LinearLayout)findViewById(R.id.mainLinearLayout);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray restaurants = (JSONArray)jsonObject.get("results");
                    for (int i = 0; i < restaurants.length(); i++) {
                        JSONObject obj = restaurants.getJSONObject(i);
                        String restaurantName = obj.getString("name");
                        String restaurantVicinity =obj.getString("vicinity");
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

                                // adds the chosen restaurant into the database
                                if(db.chosenlist().findRestaurantByAddress(restaurantVicinity)){
                                    Toast.makeText(getApplicationContext(), "The restaurant " + restaurantName + " at " + restaurantVicinity +
                                            " is already on your list.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), restaurantName + " is added to your list", Toast.LENGTH_SHORT).show();
                                    db.chosenlist().addRestaurant(new chosenList(restaurantName, restaurantVicinity));
                                }

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
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.listed_gobackBtn) {
            // button will direct user to cover page(temporarily until categories is set up)
            Intent i = new Intent(this, CoverPage.class);
            startActivity(i);
        }else if(v.getId() == R.id.readyBtn){
            Toast.makeText(getApplicationContext(), "Ready clicked", Toast.LENGTH_SHORT).show();
            Intent userPicks = new Intent(MainActivity.this, UserPicks.class);
            startActivity(userPicks);
        }
    }
}
