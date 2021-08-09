package com.example.restaurantgenerator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
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
import java.util.ArrayList;


public class NearbyRestaurants extends AppCompatActivity implements View.OnClickListener {

    private RestaurantDb db;    // create a db, so users can add their restaurants into their list
    private ArrayList<String> restaurant_List = new ArrayList<String>();
    private Context context = this;

    Button listedGoBack;
    Button readyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_restaurants);

        // creates an instance of the database when the app launches
        db = RestaurantDb.getInstance(this);

        listedGoBack = (Button)findViewById(R.id.listed_gobackBtn);
        listedGoBack.setOnClickListener(this);

        readyBtn = (Button)findViewById(R.id.readyBtn);
        readyBtn.setOnClickListener(this);



        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=" + API.key +"&location=36.66717694044335,-121.65614460655894&radius=16000&type=restaurant";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray restaurants = (JSONArray)jsonObject.get("results");
                    for (int i = 0; i < restaurants.length(); i++) {
                        JSONObject obj = restaurants.getJSONObject(i);
                        /*if(db.chosenlist().findRestaurantByAddress(obj.getString("vicinity"))){
                            continue;
                        }*/
                        String restaurantName = obj.getString("name");
                        String restaurantVicinity = obj.getString("vicinity");
                        Double restaurantLatitude = obj.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        Double restaurantLongitude = obj.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                        String restaurant = restaurantName + "\n" + restaurantVicinity + "\n" + restaurantLatitude + "\n" + restaurantLongitude;
                        restaurant_List.add(restaurant);
                    }
                    // create our adapter, fill the adapter, and list the adapter's content
                    MainActivityCustomAdapter adapter = new MainActivityCustomAdapter(restaurant_List, context);

                    ListView lView = (ListView)findViewById(R.id.restaurantList);
                    lView.setAdapter(adapter);
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
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else if(v.getId() == R.id.readyBtn){
            Toast.makeText(getApplicationContext(), "Ready clicked", Toast.LENGTH_SHORT).show();
            Intent userPicks = new Intent(NearbyRestaurants.this, UserPicks.class);
            startActivity(userPicks);
        }
    }
}
