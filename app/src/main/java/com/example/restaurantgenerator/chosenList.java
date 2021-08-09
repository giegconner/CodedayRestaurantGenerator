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

    @ColumnInfo(name = "latitude")
    private Double restaurantLatitude;

    @ColumnInfo(name = "longitude")
    private Double restaurantLongitude;

    public chosenList(String restaurantName, String restaurantAddress, Double restaurantLatitude,
                      Double restaurantLongitude){
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantLatitude = restaurantLatitude;
        this.restaurantLongitude = restaurantLongitude;
    }

    public int getRestaurantID(){return restaurantID;}
    public void setRestaurantID(int id){this.restaurantID = id;}

    public String getRestaurantName(){return restaurantName;}
    public void setRestaurantName(String name){this.restaurantName = name;}

    public String getRestaurantAddress(){return restaurantAddress;}
    public void setRestaurantAddress(String address){this.restaurantAddress = address;}

    public Double getRestaurantLatitude(){return restaurantLatitude;}
    public void setRestaurantLatitude(Double latitude){this.restaurantLatitude = latitude;}

    public Double getRestaurantLongitude(){return restaurantLongitude;}
    public void setRestaurantLongitude(Double longitude){this.restaurantLongitude = longitude;}

    @Override
    public String toString() {
        String restaurant_name = getRestaurantName() + "\n" + getRestaurantAddress() + "\n" +
                getRestaurantLatitude() + "\n" + getRestaurantLongitude();

        return restaurant_name;
    }

}
