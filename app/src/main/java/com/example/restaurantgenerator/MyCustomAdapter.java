package com.example.restaurantgenerator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    private RestaurantDb choicesdb;
    private List<chosenList> choicesList;


    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
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
            view = inflater.inflate(R.layout.activity_test_choice, null);
        }


        // handle textview and display string list
//        TextView listItemText = (TextView)view.findViewById(R.id.t_choice);
//        listItemText.setText(list.get(position));

        // handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position);

                String restaurantaddress = choicesList.get(position).getRestaurantAddress();
                if(choicesdb.chosenlist().findRestaurantByAddress(restaurantaddress)) {
                    choicesdb.chosenlist().deleteByAddress(restaurantaddress);
                }
                choicesdb.chosenlist().deleteByAddress(restaurantaddress);
//                Intent reload = new Intent();
                notifyDataSetChanged();
            }
        });

        return view;
    }



}
