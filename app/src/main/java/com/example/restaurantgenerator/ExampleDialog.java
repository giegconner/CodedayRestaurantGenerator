package com.example.restaurantgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.content.Context;
import android.content.ContextWrapper;

public class ExampleDialog extends AppCompatDialogFragment {
    private chosenList restaurant;

    public ExampleDialog(chosenList restaurant) {
        this.restaurant = restaurant;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
    }
}
