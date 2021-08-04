package com.example.restaurantgenerator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.widget.Toast;

public class UserPicks extends AppCompatActivity implements View.OnClickListener {
    private RestaurantDb choicesdb; // library database
    private List<chosenList> choiceslist;
    private ListView choiceListView;
    Button userGoBack;
    Button randomizeBtn;

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

        // create an arrayList to store the restaurant's information, and display the user's chosen restaurants
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0; i < choicesdb.chosenlist().count(); i++){
            String restaurant = choiceslist.get(i).getRestaurantName() + "\n" + choiceslist.get(i).getRestaurantAddress();
            list.add(restaurant);
        }

        // instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        // handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.choice_list);
        lView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.picks_gobackBtn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.picks_randomizeBtn) {
            Toast.makeText(getApplicationContext(), "Randomize clicked", Toast.LENGTH_SHORT).show();
            // display a popup
            // the popup will show the random restaurant's name and two buttons
            // one button will be titled 'Go Back'
            // the popup will show the random restaurant's name and address along with two buttons
            // one button will be titled 'Cancel'
            // the other button will be titled 'Go'
            Random rand = new Random();
            int upperLimit = choicesdb.chosenlist().count();
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