package com.demo.shoppinglist.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.shoppinglist.MainActivity;
import com.demo.shoppinglist.R;
import com.demo.shoppinglist.data.MyDbHandler;
import com.demo.shoppinglist.model.shopping;

public class EditList extends AppCompatActivity {
    int id;
    MyDbHandler db;
    com.demo.shoppinglist.model.shopping shopping;
    EditText item;
    String newitem;
    EditText quantity;
    String newquantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

//        db = new MyDbHandler(this);
//        shopping = new shopping();
//        Intent intent = getIntent();
//        id = intent.getExtras().getInt("Rid");
//        String name = intent.getStringExtra("Rname");
//        String num = intent.getStringExtra("Rquantity");
//        item = findViewById(R.id.editName);
//        quantity = findViewById(R.id.editQuantity);
//        item.setText(name);
//        quantity.setText(num);

    }
    public void editList(View view){
        item = findViewById(R.id.editName);
        quantity = findViewById(R.id.editQuantity);
        newitem = item.getText().toString();
        newquantity = quantity.getText().toString();
        shopping.setId(id);
        shopping.setName(newitem);
        shopping.setQuantity(newquantity);
        db.updateList(shopping);
        Toast.makeText(this, "Changes Done", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}