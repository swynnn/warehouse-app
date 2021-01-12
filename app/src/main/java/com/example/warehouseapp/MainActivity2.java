package com.example.warehouseapp;

import android.os.Bundle;
import android.widget.Toast;

import com.example.warehouseapp.provider.ItemViewModel;
import com.example.warehouseapp.provider.WarehouseItem;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutMan;
    MyRecyclerViewAdapter adapter;

    //ArrayList<WarehouseItem> itemData;
    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle);

        recyclerView = findViewById(R.id.rv);
        layoutMan = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutMan);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        //itemData = (ArrayList<WarehouseItem>) getIntent().getSerializableExtra("KEY_LIST");
        adapter = new MyRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        mItemViewModel=new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {   //ask the model class to set the data to newData, list with all the data and pass to adapter
            adapter.setItemData(newData); //newData a list of item
            adapter.notifyDataSetChanged();
        });
    }

}
