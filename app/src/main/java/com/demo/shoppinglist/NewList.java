package com.demo.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.shoppinglist.data.MyDbHandler;
import com.demo.shoppinglist.model.shopping;

public class NewList extends AppCompatActivity {
    shopping shopping;
    EditText item;
    EditText quantity;
    String item1;
    String quantity1;
    MyDbHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        db = new MyDbHandler(NewList.this);
        Intent intent = getIntent();
        shopping = new shopping();
    }
    public void addList(View view){
        item =(EditText) findViewById(R.id.item);
        quantity =(EditText) findViewById(R.id.quantity);
        item1 = item.getText().toString();
        quantity1 = quantity.getText().toString();
        shopping.setName(item1);
        shopping.setQuantity(quantity1);
        db.addItem(shopping);
        Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}