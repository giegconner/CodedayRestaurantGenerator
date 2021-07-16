package com.example.restaurantgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CoverPage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover_page);

        View findPlaceToEatButton = findViewById(R.id.init_find_place);
        findPlaceToEatButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.init_find_place) {
            Intent find_restaurant = new Intent(this, MainActivity.class);
            startActivity(find_restaurant);
        }
    }
}
