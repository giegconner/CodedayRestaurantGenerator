package com.example.restaurantgenerator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;


public class NearbyRestaurants  extends AppCompatActivity implements View.OnClickListener {

    private RestaurantDb db;    // create a db, so users can add their restaurants into their list
    private ArrayList<String> restaurant_List = new ArrayList<>();
    private ArrayList<String> restaurantCoords = new ArrayList<>();
    private Context context = this;
    private String location = "36.66717694044335,-121.65614460655894";
    private String radiusDistance;

    Button listedGoBack;
    Button readyBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_restaurants);

        this.setTitle("Choose Nearby Restaurants");

        // creates an instance of the database when the app launches
        db = RestaurantDb.getInstance(this);

        listedGoBack = (Button) findViewById(R.id.listed_gobackBtn);
        listedGoBack.setOnClickListener(this);

        readyBtn = (Button) findViewById(R.id.readyBtn);
        readyBtn.setOnClickListener(this);

        RequestQueue queue = Volley.newRequestQueue(this);
        // runtime permissions if needed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        Intent intent = getIntent();
        radiusDistance = intent.getStringExtra("radius");

        setCurrentLocation();
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=" + API.key +
                "&location=" + location + "&radius=" + radiusDistance + "&type=restaurant";
        Log.d("NearbyRestaurants", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray restaurants = (JSONArray) jsonObject.get("results");
                    for (int i = 0; i < restaurants.length(); i++) {
                        JSONObject obj = restaurants.getJSONObject(i);
                        if (db.chosenlist().findRestaurantByAddress(obj.getString("vicinity"))) {
                            continue;
                        }
                        String restaurantName = obj.getString("name");
                        String restaurantVicinity = obj.getString("vicinity");
                        Double restaurantLatitude = obj.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        Double restaurantLongitude = obj.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                        String restaurant = restaurantName + "\n" + restaurantVicinity;
                        String restaurant_coords = restaurantLatitude + "\n" + restaurantLongitude;
                        restaurant_List.add(restaurant);
                        restaurantCoords.add(restaurant_coords);
                    }
                    // create our adapter, fill the adapter, and list the adapter's content
                    NearbyRestaurantCustomAdapter adapter = new NearbyRestaurantCustomAdapter(restaurantCoords, restaurant_List, context);

                    ListView lView = (ListView) findViewById(R.id.restaurantList);
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

    @SuppressLint("MissingPermission")
    public void setCurrentLocation() {
        try {
            LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String currLatitude = String.valueOf(loc.getLatitude());
            String currLongitude = String.valueOf(loc.getLongitude());
            location = currLatitude + "," + currLongitude;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
            userPicks.putExtra("radius", radiusDistance);
            startActivity(userPicks);
        }
    }
}
