package com.example.warehouseapp.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


//later will be used in mainactivity to call the database
public class ItemViewModel extends AndroidViewModel {
    private ItemRepository mRepository;
    private LiveData<List<WarehouseItem>> mAllItems;


    public ItemViewModel(@NonNull Application application) { //CONSTRUCTOR
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    //FOR UI / MAIN ACTIVITY TO DEAL WITH DB
    public LiveData<List<WarehouseItem>> getAllItems() {
        return mAllItems;
    }

    public void insert(WarehouseItem item) {
        mRepository.insert(item);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }


//    public void deleteItem(String name){
//        mRepository.deleteI   tem(name);
//    }
}
