package com.example.restaurantgenerator;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface choseListDao {

    @Query("select count(*) from chosenRestaurants")
    int count();


    @Query("select * from chosenRestaurants where address = :restaurantaddress")
    boolean findRestaurantByAddress(String restaurantaddress);

    // Will return all restaurants the user picks
    @Query("select * from chosenRestaurants")
    List<chosenList> getAllRestaurants();

    @Insert
    long[] insertRestaurant(chosenList... chosenLists);

    // Will delete any restaurant with the inputted name (JUST FOR TESTING PURPOSES)
    @Query("DELETE from chosenRestaurants where restaurant_name = :restaurantName")
    void deleteByName(String restaurantName);

    @Insert
    void addRestaurant(chosenList chosenlist);
}
