package com.example.warehouseapp.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WarehouseItem.class}, version = 1)
//CREATING DB AND ALL ENTITY FROM WAREHOUSE ITEM, IF HAVE ANOTHER ONE , IT WILL REPLACE THE HIGHER VERSION TO LOWER VERSION
public abstract class ItemDatabase extends RoomDatabase {

    public static final String ITEM_DATABASE_NAME = "item_database"; //NAME FOR DATABASE
    public abstract ItemDao itemDao();           //DATA ACCESS OBJECT
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile ItemDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;  //HOW MANY THREADS ARE THERE TO EXECUTE
    static final ExecutorService databaseWriteExecutor =            //manipulate database
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ItemDatabase getDatabase(final Context context) {        //check whether this database is ald exist in your application
        if (INSTANCE == null) {                                    //if does not exist yet
            synchronized (ItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemDatabase.class, ITEM_DATABASE_NAME).fallbackToDestructiveMigration()   //create database
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
