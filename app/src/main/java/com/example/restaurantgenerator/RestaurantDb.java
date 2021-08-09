package com.example.restaurantgenerator;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database( entities = {chosenList.class}, version = 2, exportSchema = false)
public abstract class RestaurantDb extends RoomDatabase{

    private static RestaurantDb sInstance;
    public abstract choseListDao chosenlist();

    public static synchronized RestaurantDb getInstance(Context context){
        if (sInstance == null){
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    RestaurantDb.class, "restaurant.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }
}
