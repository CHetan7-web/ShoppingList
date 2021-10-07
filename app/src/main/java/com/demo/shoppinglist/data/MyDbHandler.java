package com.demo.shoppinglist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.demo.shoppinglist.model.shopping;
import com.demo.shoppinglist.params.params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, params.DB_NAME, null, params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create ="CREATE TABLE "+ params.TABLE_NAME + "("+params.KEY_ID+" INTEGER PRIMARY KEY,"+
                params.KEY_NAME+" TEXT,"+params.KEY_QUANTITY+" TEXT,"+params.KEY_COLOR+" INTEGER)";
        Log.d("nishi",create);
//      Toast.makeText(MainActivity.this,"Database is created",Toast.LENGTH_SHORT).show();
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addItem(shopping shopping){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(params.KEY_NAME, shopping.getName());
        values.put(params.KEY_QUANTITY,shopping.getQuantity());
        values.put(params.KEY_COLOR,0);
        db.insert(params.TABLE_NAME, null, values);
        Log.d("nishi", "Item is added "+ shopping.getName());
        db.close();

    }

    public List<shopping> getAllItems() {
        List<shopping> shoppingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if (cursor.moveToFirst()){
            do {
                shopping shop = new shopping();
                shop.setId(cursor.getInt(0));
                shop.setName(cursor.getString(1));
                shop.setQuantity(cursor.getString(2));
                shoppingList.add(shop);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return shoppingList;
    }
    public int getcolor(int id){

        Log.d("nishi", "getId: ");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("nishi", "db write: ");
        String select = "SELECT "+params.KEY_COLOR+" FROM "+params.TABLE_NAME+" WHERE "+params.KEY_ID+
                "="+id;
        Log.d("nishi", "query: "+select);
        Cursor cursor = db.rawQuery(select,null);
        Log.d("nishi", "after query ");
        cursor.moveToFirst();
        id = cursor.getInt(0);
//        if (cursor.moveToFirst()){
//            id= cursor.getInt(0);
//        }
        cursor.close();
        return id;
    }
    public void setcolor(int id){

        Log.d("nishi", "getId: ");
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("nishi", "db write: ");
        ContentValues values = new ContentValues();
        values.put(params.KEY_COLOR,1);
        db.update(params.TABLE_NAME, values, params.KEY_ID+"=?",
                new String[]{String.valueOf(id)});
    }
    public void resetcolor(int id){

        Log.d("nishi", "getId: ");
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("nishi", "db write: ");
        ContentValues values = new ContentValues();
        values.put(params.KEY_COLOR,0);
        db.update(params.TABLE_NAME, values, params.KEY_ID+"=?",
                new String[]{String.valueOf(id)});
    }
    public void updateList(shopping shopping){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(params.KEY_NAME,shopping.getName());
        values.put(params.KEY_QUANTITY, shopping.getQuantity());
        Log.d("nishi", "updateContact: ");
        db.update(params.TABLE_NAME, values, params.KEY_ID + "=?",
                new String[]{String.valueOf(shopping.getId())});
    }
    public void delTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(params.TABLE_NAME,"1",null);
        Log.d("nishi", "delTable: before ");
        String autoIncr = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+params.TABLE_NAME+"'";
//        db.execSQL(autoIncr);
        Log.d("nishi", "delTable: ");
        db.close();
    }

}
