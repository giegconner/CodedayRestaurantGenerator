package com.example.restaurantgenerator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.widget.Button;
import android.widget.Toast;

public class UserPicks extends AppCompatActivity implements View.OnClickListener {

    private RestaurantDb choicesdb; // library database
    private List<chosenList> choiceslist;
    private ListView choiceListView;
    private ArrayAdapter<chosenList> choice_adapter;

    Button userGoBack;
    Button randomizeBtn;
    Button deleteBtn;

    LinearLayout mainLinearLayout;
    LinearLayout restaurantListLinearLayout;
    TextView choicesTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_picks); // displays user picks page

        userGoBack = (Button)findViewById(R.id.picks_gobackBtn); // go back button
        userGoBack.setOnClickListener(this);

        randomizeBtn = (Button)findViewById(R.id.picks_randomizeBtn); // randomize button
        randomizeBtn.setOnClickListener(this);

        choicesdb = RestaurantDb.getInstance(this); // instance of database

        choiceslist = choicesdb.chosenlist().getAllRestaurants(); // will get all restaurants from database

        mainLinearLayout = findViewById(R.id.mainLinearLayout);
        for(int i=0; i < choicesdb.chosenlist().count(); i++){
            String displayRestaurantName = choiceslist.get(i).getRestaurantName();
            String displayRestaurantAddress = choiceslist.get(i).getRestaurantAddress();
            restaurantListLinearLayout = new LinearLayout(UserPicks.this);
            restaurantListLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            choicesTextView = new TextView(UserPicks.this);
            choicesTextView.setText(displayRestaurantName);
            deleteBtn = new Button(UserPicks.this);
            deleteBtn.setText("Delete");
            deleteBtn.setWidth(10);

            restaurantListLinearLayout.addView(choicesTextView);
            restaurantListLinearLayout.addView(deleteBtn);

            deleteBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(choicesdb.chosenlist().findRestaurantByAddress(displayRestaurantAddress)){
                        choicesdb.chosenlist().deleteByAddress(displayRestaurantAddress);
                        Intent reload = new Intent(UserPicks.this, UserPicks.class);
                        startActivity(reload);
                    }
                }
            });
            mainLinearLayout.addView(restaurantListLinearLayout);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.picks_gobackBtn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.picks_randomizeBtn) {
            Toast.makeText(getApplicationContext(), "Randomize clicked", Toast.LENGTH_SHORT).show();
            // display a popup
            // the popup will show the random restaurant's name and address along with two buttons
            // one button will be titled 'Cancel'
            // the other button will be titled 'Go'
            Random rand = new Random();
            int upperLimit = choiceslist.size();
            if (upperLimit >= 1) {
                int i = rand.nextInt(upperLimit); // random index from 0 to upperLimit-1
                chosenList restaurant = choiceslist.get(i);
                chosenList randomRestaurant = choiceslist.get(i);
                String randomRestaurantName = randomRestaurant.getRestaurantName();
                String randomRestaurantAddress = randomRestaurant.getRestaurantAddress();
                Log.d("restaurant name:", randomRestaurantName);
                Log.d("restaurant address:", randomRestaurantAddress);
                openDialog(randomRestaurant);
            }
        }
    }

    public void openDialog(chosenList restaurant) {
        ExampleDialog exampleDialog = new ExampleDialog(restaurant);
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
}