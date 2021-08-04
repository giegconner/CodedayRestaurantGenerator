package com.example.restaurantgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.content.Context;
import android.content.ContextWrapper;

public class ExampleDialog extends AppCompatDialogFragment {
    private chosenList restaurant;
    private String restaurantString;
    private Context context;

    public ExampleDialog(chosenList restaurant) {
        this.restaurant = restaurant;
        this.restaurantString = "none";
    }

    public ExampleDialog(String restaurantString, Context context){
        this.restaurantString = restaurantString;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(restaurantString.equals("none")){
            String restaurantName = this.restaurant.getRestaurantName();
            String restaurantAddress = this.restaurant.getRestaurantAddress();
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
                        }
                    });
            return builder.create();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("You Have An Empty List!")
                    .setMessage("\n" + restaurantString + "\n")
                    .setNegativeButton("Fill up List", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent l = new Intent(context, MainActivity.class);
                            startActivity(l);
                        }
                    });
            return builder.create();
        }
    }
}
