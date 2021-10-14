package com.demo.shoppinglist.Interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.demo.shoppinglist.Entity.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void addItem(Item item);

    @Delete
    void deleteItem(Item item);

    @Update
    void updateItem(Item item);

    @Query("Select * from shopping_item")
    List<Item> getItems();

    @Query("Select * from shopping_item order by time DESC limit 1")
    LiveData<List<Item>> getItemsSortedByTime();

    @Query("Select * from shopping_item order by title COLLATE NOCASE")
    LiveData<List<Item>> getItemsSortedByTitle();

    @Query("Select * from shopping_item where isComplete=0")
    LiveData<List<Item>> getIncompleteItems();

    @Query("Delete from shopping_item where isComplete=1")
    void deleteImportantItems();

}
