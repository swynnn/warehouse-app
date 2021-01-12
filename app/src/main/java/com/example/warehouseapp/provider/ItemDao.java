package com.example.warehouseapp.provider;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao //put in the query we wish to access the database
public interface ItemDao {

    @Query("select * from items")
    LiveData<List<WarehouseItem>> getAllItem();

    @Query("select * from items where itemName = :name")   //param that going to pass in
    List<WarehouseItem> getItem(String name);
    //
    @Insert
    void addItem(WarehouseItem item);

//    @Query("delete from customers where customerName= :name")
//    void deleteCustomer(String name);

//    @Query("delete FROM items WHERE itemName = :name")
//    void deleteItem(String name);

    @Query("delete FROM items")
    void deleteAllItems();
}
