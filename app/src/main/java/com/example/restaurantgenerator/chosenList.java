package com.example.restaurantgenerator;


import androidx.room.Entity;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

@Entity(tableName = "chosenRestaurants")
public class chosenList {

    @PrimaryKey(autoGenerate = true)
    private int restaurantID;

    @ColumnInfo(name = "restaurant_name")
    private String restaurantName;

    @ColumnInfo(name = "address")
    private String restaurantAddress;

    public chosenList(String restaurantName, String restaurantAddress){
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
    }

    public int getRestaurantID(){return restaurantID;}
    public void setRestaurantID(int id){this.restaurantID = id;}

    public String getRestaurantName(){return restaurantName;}
    public void setRestaurantName(String name){this.restaurantName = name;}

    public String getRestaurantAddress(){return restaurantAddress;}
    public void setRestaurantAddress(String address){this.restaurantAddress = address;}

    @Override
    public String toString() {
        String restaurant_name = getRestaurantName() + "\n" + getRestaurantAddress();

        return restaurant_name;
    }

}
