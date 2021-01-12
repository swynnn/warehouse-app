package com.example.warehouseapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.warehouseapp.provider.WarehouseItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<WarehouseItem> itemData = new ArrayList<>();

    public MyRecyclerViewAdapter() { //instantiate the class
    }

    public void setItemData(List<WarehouseItem> itemData) {
        this.itemData = itemData;
    } // The data is passed to the adaptor

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {       //inflates the card’s layout that we have implemented earlier each time a new card is required
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false); //CardView inflated as RecyclerView list item
        //inflate viewholder to hold the data to put inside cardview
        ViewHolder viewHolder = new ViewHolder(v); //have to let app know inside got 3 details, will bind all the neccessay view component into the layoutn
        //It the passes v, which is a reference to the card layout to the contractor of ViewHolder local class.


        return viewHolder; //once return, it will be used as an input for the next callback
        //returns the view holder object that will be an input to the next method onBindViewHolder
    }

    @Override //use oncreate return viewholder
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) { // retrieve the data at that position and place it in that view holder.
        holder.itemID.setText(String.valueOf(itemData.get(position).getItemID())); //get current position
        holder.itemName.setText(itemData.get(position).getItemName());
        holder.itemQty.setText(itemData.get(position).getItemCost());
        holder.itemCost.setText(itemData.get(position).getItemQty());
        holder.itemDesc.setText(itemData.get(position).getItemDes());
        holder.itemFrozen.setText(itemData.get(position).getItemFroz());
        Log.d("week6App","onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    } // returns the size of the array list which is the number of items to be displayed in the list

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView itemID;
        public TextView itemName;
        public TextView itemQty;
        public TextView itemCost;
        public TextView itemDesc;
        public TextView itemFrozen;


        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            itemID = itemView.findViewById(R.id.cardID);
            itemName = itemView.findViewById(R.id.cardname);
            itemQty = itemView.findViewById(R.id.cardqty);
            itemCost = itemView.findViewById(R.id.cardcost);
            itemDesc = itemView.findViewById(R.id.carddesc);
            itemFrozen = itemView.findViewById(R.id.cardfroz);
        }
    }

}

