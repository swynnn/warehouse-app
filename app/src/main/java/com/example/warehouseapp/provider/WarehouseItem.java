package com.example.warehouseapp.provider;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class WarehouseItem  {

    public WarehouseItem() {
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull //IF NULL, NOT ABLE TO ADD NEW DATA TO TABLE, PRIMARY KEY CANNOT BE NULL
    @ColumnInfo(name = "itemID")
    private int itemID;

    @ColumnInfo(name = "itemName")
    public String itemName;

    @ColumnInfo(name = "itemCost")
    public String itemCost;

    @ColumnInfo(name = "itemQty")
    public String itemQty;

    @ColumnInfo(name = "itemDes")
    public String itemDes;

    @ColumnInfo(name = "itemFroz")
    public String itemFroz;


    public WarehouseItem(String item_name, String item_cost, String item_qty, String item_des, String item_froz) {
        itemName = item_name;
        itemCost = item_cost;
        itemQty = item_qty;
        itemDes = item_des;
        itemFroz = item_froz;
    }

    public void setItemID(@NonNull  int itemID) {
        this.itemID = itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemCost(String itemCost) {
        this.itemCost = itemCost;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public void setItemFroz(String itemFroz) {
        this.itemFroz = itemFroz;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCost() {
        return itemCost;
    }

    public String getItemQty() {
        return itemQty;
    }

    public String getItemDes() {
        return itemDes;
    }

    public String getItemFroz() {
        return itemFroz;
    }
}
