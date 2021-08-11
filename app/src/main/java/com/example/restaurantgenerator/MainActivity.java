package com.example.restaurantgenerator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button findPlaceToEatButton = (Button)findViewById(R.id.find_a_place);
        findPlaceToEatButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.find_a_place) {
            openDialog();
        }
    }

    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog(this, "1");
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

}
