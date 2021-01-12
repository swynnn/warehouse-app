package com.example.warehouseapp.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<WarehouseItem>> mAllItems;

    //CONSTRUCTOR
    ItemRepository(Application application) {
        ItemDatabase db = ItemDatabase.getDatabase(application); //INSTANTIATE DB USING GETDB, IF ALD EXIST JUST ASGN TO DB
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItem();
    }

    LiveData<List<WarehouseItem>> getAllItems() {
        return mAllItems;
    } //ALLOW UI TO ACCESS THIS METHOD

    void insert(WarehouseItem item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> mItemDao.addItem(item)); //databaseWriteExecutor that has declared in itemdatabase
    }

    void deleteAll(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteAllItems();
        });
    }

//    void deleteItem(String name){
//        ItemDatabase.databaseWriteExecutor.execute(() -> mItemDao.deleteItem(name));
//        }
}
