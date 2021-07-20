package com.example.restaurantgenerator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class UserPicks extends AppCompatActivity implements View.OnClickListener{

    private RestaurantDb choicesdb; // library database
    private List<chosenList> choiceslist;
    private ListView choiceListView;
    private ArrayAdapter<chosenList> choice_adapter;

    Button userGoBack;

    LinearLayout userLinearLayout;
    LinearLayout choicesLinearLayout;
    TextView choicesTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_picks); // displays user picks page

        userGoBack = (Button)findViewById(R.id.picks_gobackBtn); // go back button
        userGoBack.setOnClickListener(this);

        choiceListView = findViewById(R.id.choice_list);
        choicesdb = RestaurantDb.getInstance(this); // instance of database

        choiceslist = choicesdb.chosenlist().getAllRestaurants(); // will get all restaurants from database

        choice_adapter = new ArrayAdapter<>(this, R.layout.activity_test_choice, R.id.t_choice, choiceslist);
        choiceListView.setAdapter(choice_adapter);


//        restaurant_list = new ArrayList<String>();
//        restaurant_list.add("");



////        userLinearLayout = (LinearLayout)findViewById(R.id.userLinearLayout);
//
//        for(int i = 0; i < choiceslist.size(); i++) {
//
//            choicesLinearLayout = new LinearLayout(UserPicks.this);
//            choicesLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//            choicesTextView = new TextView(UserPicks.this);
//
//            choicesTextView.setText(choiceslist.get(i).getRestaurantName());
//        }
////        choicesTextView.setText(choiceslist.get());
//
//        choicesLinearLayout.addView(choicesTextView);



    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.picks_gobackBtn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}