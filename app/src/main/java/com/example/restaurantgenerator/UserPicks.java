package com.example.restaurantgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserPicks extends AppCompatActivity implements View.OnClickListener {
    Button randomizeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_picks);

        randomizeBtn = (Button)findViewById(R.id.randomizeBtn);
        randomizeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "Randomize clicked", Toast.LENGTH_SHORT).show();
        // display a popup
        // the popup will show the random restaurant's name and two buttons
        // one button will be titled 'Go Back'
        // the other button will be titled 'Go'
    }
}