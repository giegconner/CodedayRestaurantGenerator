package com.example.restaurantgenerator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface choseListDao {

    @Query("select count(*) from chosenRestaurants")
    int count();

    @Query("select * from chosenRestaurants where address = :restaurantaddress")
    boolean findRestaurantByAddress(String restaurantaddress);

    @Query("select * from chosenRestaurants")
    List<chosenList> getAllRestaurants();

    @Insert
    long[] insertRestaurant(chosenList... chosenLists);

    @Insert
    void addRestaurant(chosenList chosenlist);
}
