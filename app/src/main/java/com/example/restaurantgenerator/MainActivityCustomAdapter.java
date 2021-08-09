package com.example.restaurantgenerator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivityCustomAdapter extends BaseAdapter implements ListAdapter{

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    private RestaurantDb choicesdb;
    private List<chosenList> choicesList;

    public MainActivityCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
        choicesdb = RestaurantDb.getInstance(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0; // ?
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_nearby_restaurants_custom_adapter, null);
        }

        // handle textview and display string list
        TextView listItemText = (TextView)view.findViewById(R.id.restaurant_list);
        listItemText.setText(list.get(position));

        // handle buttons and add onClickListeners
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String[] restaurantParts = list.get(position).split("\n");
                if(choicesdb.chosenlist().findRestaurantByAddress(restaurantParts[1])){
                    Toast.makeText(context.getApplicationContext(), "The restaurant " + restaurantParts[0] +
                            " at " + restaurantParts[1] + " is already on your list.", Toast.LENGTH_SHORT).show();
                }else{
                    chosenList newRestaurant = new chosenList(restaurantParts[0], restaurantParts[1],
                            Double.valueOf(restaurantParts[2]), Double.valueOf(restaurantParts[3]));
                    choicesdb.chosenlist().addRestaurant(newRestaurant);
                    Toast.makeText(context.getApplicationContext(), "The restaurant " + restaurantParts[0]
                            + " has been added to your list.", Toast.LENGTH_SHORT).show();
                }
                list.remove(position);
                notifyDataSetChanged();
            }
        });


        return view;
    }
}
