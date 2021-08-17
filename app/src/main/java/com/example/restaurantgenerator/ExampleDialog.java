package com.example.restaurantgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.content.Context;

public class ExampleDialog extends AppCompatDialogFragment implements View.OnClickListener{
    private chosenList restaurant;
    private String restaurantString;
    private Context context;
    private String distance;
    private EditText editText;
    private boolean checker = true;

    // default constructor
    public ExampleDialog(Context context, String distance){
        this.context = context;
        this.distance = distance;
        this.restaurantString = "choosing";
    }

    public ExampleDialog(chosenList restaurant) {
        this.restaurant = restaurant;
        this.restaurantString = "none";
    }

    public ExampleDialog(String restaurantString, Context context, String distance){
        this.restaurantString = restaurantString;
        this.context = context;
        this.distance = distance;
        this.checker = false;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(restaurantString.equals("none")){
            String restaurantName = this.restaurant.getRestaurantName();
            String restaurantAddress = this.restaurant.getRestaurantAddress();
            String restaurantLatitude = String.valueOf(this.restaurant.getRestaurantLatitude());
            String restaurantLongitude = String.valueOf(this.restaurant.getRestaurantLongitude());
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Restaurant chosen!")
                    .setMessage("\n" + restaurantName + "\n" + restaurantAddress + "\n")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d("Redirect to:", "routing screen");
                            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + restaurantLatitude + "," + restaurantLongitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        }
                    });
            return builder.create();
        }else if( checker == false ){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("You Have An Empty List!")
                    .setMessage("\n" + restaurantString + "\n")
                    .setNegativeButton("Fill up List", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent p = new Intent(context, NearbyRestaurants.class);
                            p.putExtra("radius", distance);
                            startActivity(p);
                        }
                    });
            return builder.create();
        }else{
            return askUserForDistance();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, NearbyRestaurants.class);
        if(v.getId() == R.id.button1){
            distance = "8046";
            intent.putExtra("radius", distance);
            startActivity(intent);
        }else if(v.getId() == R.id.button2){
            distance = "16093";
            intent.putExtra("radius", distance);
            startActivity(intent);
        }else if(v.getId() == R.id.button3){
            distance = "24140";
            intent.putExtra("radius", distance);
            startActivity(intent);
        }else if(v.getId() == R.id.buttonCustom){
            String input = editText.getText().toString();
            String[] inputParts = input.split("[^0-9]");

            int miles_to_meters = Integer.parseInt(inputParts[0])*1609;
            System.out.println(miles_to_meters);

            distance = Integer.toString(miles_to_meters);

            intent.putExtra("radius", distance);
            startActivity(intent);
        }
    }

    public Dialog askUserForDistance(){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater inflater = requireActivity().getLayoutInflater();//inflate view for alertdialog since we are

        View view = inflater.inflate(R.layout.my_alert_dialog, null);

        editText = (EditText) view.findViewById(R.id.edittext_id);

        Button mile_5_btn = (Button) view.findViewById(R.id.button1);
        mile_5_btn.setOnClickListener(this);

        Button mile_10_btn = (Button) view.findViewById(R.id.button2);
        mile_10_btn.setOnClickListener(this);

        Button mile_15_btn = (Button) view.findViewById(R.id.button3);
        mile_15_btn.setOnClickListener(this);

        Button mile_custom_btn = (Button) view.findViewById(R.id.buttonCustom);
        mile_custom_btn.setOnClickListener(this);

        alert.setView(view);
        return alert.show();
    }
}
