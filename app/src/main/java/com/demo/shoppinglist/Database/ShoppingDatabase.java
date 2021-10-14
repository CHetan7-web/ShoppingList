package com.demo.shoppinglist.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.demo.shoppinglist.Interfaces.ItemDao;
import com.demo.shoppinglist.Entity.Item;

@Database(entities = {Item.class},version = 5)
public abstract class ShoppingDatabase extends RoomDatabase {

    private ItemDao itemDao;
    private static ShoppingDatabase instance;

    public abstract ItemDao getItemDao();

    public static synchronized ShoppingDatabase getInstance(Context context){
        if (instance==null)
            instance= Room.databaseBuilder(context.getApplicationContext(),
                         ShoppingDatabase.class, "shopping_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();

        return instance;
    }

}
